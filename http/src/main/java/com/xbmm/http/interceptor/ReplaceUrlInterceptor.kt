package com.xbmm.http.interceptor

import com.xbmm.http.util.SignUtils
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.util.*

private const val TAG = "ReplaceUrlInterceptor"
class ReplaceUrlInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val method = request.method
       var body= request.body

        val treeMap = TreeMap<String, Any>()
        val rootJsonObject = JSONObject()
        if (method == "POST" || method == "PUT") {
            //post 单独处理
            if (body is FormBody) {
                if (body.size == 0) {
                    treeMap.clear()
                } else {
                    for (index in 0 until body.size) {
                        rootJsonObject.put(body.name(index), body.value(index))
                        treeMap[body.name(index)] = body.value(index)
                    }
                }
                val stringBody =rootJsonObject.toString().toRequestBody("application/json;charset=utf-8".toMediaType())
                val newBuilder = request.newBuilder().addHeader("appSignKey", SignUtils.AppSign(treeMap))
                    .url(request.url)
                    .method(request.method, stringBody)
                    .build()


                return chain.proceed(newBuilder)
            }

            return chain.proceed(request)

        }
        /**
         * 将地址中的乱码过滤，多余的?页过滤掉
         */
        val httpUrl = request.url.toString()

        val parse = httpUrl.toHttpUrlOrNull() ?: return chain.proceed(request)

        // 其它方式处理 添加新的参数
        //解析URL中的key和value
        val encodedPathSegments = parse.queryParameterNames
        encodedPathSegments.forEach {
            treeMap[it] = parse.queryParameter(it) ?: ""
        }

        val authorizedUrlBuilder = parse
            .newBuilder()
            .scheme(parse.scheme)
            .host(parse.host)


        val requestBuilder = request.newBuilder().apply {
//            if (!TextUtils.isEmpty(mSecondBaseUrl)) {
//                this.url(mSecondBaseUrl.toHttpUrlOrNull()!!)
//            }
        }.method(request.method, request.body)
            .url(authorizedUrlBuilder.build())

        requestBuilder.removeHeader("appSignKey")
        requestBuilder.addHeader("appSignKey", SignUtils.AppSign(treeMap))

        return chain.proceed(requestBuilder.build())
    }


}