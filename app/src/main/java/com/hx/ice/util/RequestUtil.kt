package com.hx.ice.util

import okhttp3.FormBody

/**
 * post-json格式验签 需要调用此方法生成body
 */
object RequestUtil {

    fun getRequestBody(map: Map<String, Any>): FormBody {
        val builder =  FormBody.Builder()

        if (map.isNotEmpty()) {
            map.keys.forEach {
                builder.add(it, map[it].toString())
            }
        }
       return builder.build()
    }


}