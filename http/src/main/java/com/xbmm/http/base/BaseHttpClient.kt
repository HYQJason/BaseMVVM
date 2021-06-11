package com.xbmm.http.base

import android.net.ParseException
import android.util.Log
import android.util.LruCache
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.hx.ice.base.BasicApplication.Companion.instance
import com.hx.ice.base.Preference
import com.xbmm.http.constants.HttpConstants.deviceType
import com.xbmm.http.constants.HttpConstants.deviceUUID
import com.xbmm.http.interceptor.BasicParamsInterceptor
import com.xbmm.http.interceptor.ReplaceUrlInterceptor
import com.xbmm.http.util.DeviceInfoUtil.Companion.getAppVersion
import com.xbmm.http.util.DeviceInfoUtil.Companion.getIPAddress
import com.xbmm.http.util.DeviceInfoUtil.Companion.getScreenHeight
import com.xbmm.http.util.DeviceInfoUtil.Companion.getScreenWidth
import com.xbmm.http.util.DeviceInfoUtil.Companion.getSystemVersion
import com.xbmm.http.util.DeviceInfoUtil.Companion.getVerName
import com.xbmm.http.util.DeviceUuidUtil.getDeviceID
import com.xbmm.http.util.NetTypeUtils.getNetWorkType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.*
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeUnit

private const val TAG = "BaseHttpClient"

/**
 * @ClassName: BaseHttpClient
 * @Description:OkHttp Retrofit 配置类
 * @Author: WY-HX
 * @Date: 2021/3/26 16:28
 */
abstract class BaseHttpClient {

    companion object {
        var accessToken by Preference("accessToken", "")
        private const val DEFAULT_CONNECT_TIME = 10L // 连接超时时间
        private const val DEFAULT_WRITE_TIME = 30L // 设置写操作超时时间
        private const val DEFAULT_READ_TIME = 30L // 设置读操作超时时间
        private const val SERVICE_CACHE_COUNT = 20 // 最多缓存的service数量
        /**
         * 通用的公共参数
         * */
        fun getBasicCommonMap(): MutableMap<String, String> {
            return mutableMapOf<String, String>().apply {
                put("Accept", "application/json")
                put("Content-Type", "application/json")
                put("os", "Android")
                put("terminalType", "app")
                put("channel", "app")
                put("version", getAppVersion(instance) ?: "")
                put("appId", getVerName(instance))
                put("systemVersion", getSystemVersion())
                //设备分辨率
                put("screenSize", "${getScreenWidth(instance)}*${getScreenHeight(instance)}")
                put("timestamp", System.currentTimeMillis().toString())
              put("accessToken", accessToken)
                if (deviceType.isNullOrEmpty() || deviceUUID.isNullOrEmpty())
                    getDeviceID(instance)
                put("deviceType", deviceType)
                put("deviceNum", deviceUUID)
                put("netType", getNetWorkType(instance))
                put("IP", getIPAddress())
                put("language", "en_US")

            }

        }

    }

    private var serviceCache: LruCache<String, Any> = LruCache(SERVICE_CACHE_COUNT)
    private val okClient: OkHttpClient
    private val retrofitClient: Retrofit

    init {
        okClient = buildOkHttpClient()
        retrofitClient = buildRetrofit()
    }

    private fun buildOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level =
                if (isDebug()) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        }
        val builder = OkHttpClient.Builder()
            .addInterceptor(ReplaceUrlInterceptor())
            .addInterceptor(BasicParamsInterceptor())
            .addInterceptor(loggingInterceptor)
            .connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)// 连接超时时间
            .writeTimeout(DEFAULT_WRITE_TIME, TimeUnit.SECONDS)// 设置写操作超时时间
            .readTimeout(DEFAULT_READ_TIME, TimeUnit.SECONDS)// 设置读操作超时时间
            .retryOnConnectionFailure(true)

        return builder.build()
    }

    private fun buildRetrofit(): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(okClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
        return builder.build()
    }
    /**
     * 获取service对象
     *
     * @param service api所在接口类
     */
    private fun <T> getService(service: Class<T>): T {
        var retrofitService: T? = serviceCache.get(service.canonicalName) as T
        if (retrofitService == null) {
            retrofitService = retrofitClient.create(service)
            serviceCache.put(service.canonicalName, retrofitService)
        }
        return retrofitService!!
    }
    /**
     * 获取service对象
     *
     * @param service api所在接口类
     */
 /*   private fun <T> getService(service: Class<T>): T {
        okClient = buildOkHttpClient()
        retrofitClient = buildRetrofit()
        val retrofitService = retrofitClient?.create(service)
        return retrofitService!!
    }*/

    /**
     * 特殊类型数据 可调用这个方法，不推荐
     *
     */
    suspend fun <T : Any, D : Any> requestAny(
        apiInterface: Class<T>,
        call: suspend (service: T) -> D
    ): D {
        val s = getService(apiInterface)
        return call(s)
    }

    /**
     * 建议调用此方法发送网络请求
     * 因为协程中出现异常时，会直接抛出异常，所以使用try...catch方法捕获异常
     */
    suspend fun <T : Any, D : Any> request(
        apiInterface: Class<T>,
        call: suspend (service: T) -> BaseResponse<D>
    ): ParseResult<D> {
        try {
            val s = getService(apiInterface)
            val response = call(s)
            return if (response.isSuccess()) {
                ParseResult.Success(response.data)
            } else {
                ParseResult.Failure(response.code, response.msg)
            }
        } catch (ex: Throwable) {
            Log.d(TAG, "request: $ex")
            return ParseResult.ERROR(ex, parseException(ex))
        }
    }

    /**
     * 解析网络请求异常
     */
    private fun parseException(e: Throwable): HttpError {
        return when (e) {
            is HttpException -> HttpError.BAD_NETWORK
            is ConnectException, is UnknownHostException -> HttpError.CONNECT_ERROR
            is InterruptedIOException -> HttpError.CONNECT_TIMEOUT
            is JsonParseException, is JSONException, is ParseException, is ClassCastException -> HttpError.PARSE_ERROR
            is CancellationException -> HttpError.CANCEL_REQUEST
            else -> HttpError.UNKNOWN
        }
    }

    /**
     * 获取Retrofit实例
     */
    fun getRetrofit(): Retrofit {
        return retrofitClient!!
    }

    /**
     * 获取Retrofit的BaseUrl
     */
    abstract fun getBaseUrl(): String

    /**
     * 是否处于调试模式
     */
    abstract fun isDebug(): Boolean




    /**
     * 动态的公共参数
     * */
    abstract fun getCommonMap(): MutableMap<String, String>


}