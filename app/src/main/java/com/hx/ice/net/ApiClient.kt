package com.hx.ice.net

import com.hx.ice.BuildConfig
import com.xbmm.http.base.BaseHttpClient


/**
 * @ClassName ApiClient
 * @description 网络请求包装类，单例
 * @author: Jason
 * @date:
 */
object  ApiClient : BaseHttpClient() {



    override fun getBaseUrl() = BuildConfig.baseUrl


    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    override fun getCommonMap(): MutableMap<String, String> {
        return mutableMapOf<String, String>().apply {
            put("app_channel", "app")
        }


    }




}