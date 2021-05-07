package com.xbmm.http.interceptor


import com.xbmm.http.base.BaseHttpClient
import okhttp3.Interceptor
import okhttp3.Response

private const val TAG = "BasicParamsInterceptor"

/**
 * @ClassName: BasicParamsInterceptor
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/4/12 10:52
 */
class BasicParamsInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder().apply {
            if (BaseHttpClient.getBasicCommonMap().isNotEmpty()) {
                //请求定制(添加请求头)
                BaseHttpClient.getBasicCommonMap().forEach {
                    addHeader(it.key, it.value)
                }
            }

        }
     return   chain.proceed(requestBuilder.build())

    }

}