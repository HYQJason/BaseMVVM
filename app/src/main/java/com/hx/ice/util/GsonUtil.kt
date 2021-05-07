package com.hx.ice.util

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.util.*

object GsonUtil {
    private const val TAG = "GsonUtil"
    private var gson: Gson = Gson()


    /**
     * 转成json
     *
     * @param object
     * @return
     */
    fun gsonString(`object`: Any?): String? {
        var gsonString: String? = null
        if (gson != null) {
            gsonString = gson.toJson(`object`)
        }
        return gsonString
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    fun <T> gsonToBean(gsonString: String?, cls: Class<T>?): T? {
        var t: T? = null
        if (gson != null) {
            t = gson.fromJson(gsonString, cls)
        }
        return t
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
    </T> */
    fun <T> gsonToList(json: String?, cls: Class<T>?): List<T> {
        val gson = Gson()
        val list: MutableList<T> = ArrayList()
        val array = JsonParser().parse(json).asJsonArray
        for (elem in array) {
            list.add(gson.fromJson(elem, cls))
        }
        return list
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    fun <T> gsonToListMaps(gsonString: String?): List<Map<String, T>>? {
        var list: List<Map<String, T>>? = null
        if (gson != null) {
            list = gson.fromJson(
                gsonString,
                object :
                    TypeToken<List<Map<String?, T>?>?>() {}.type
            )
        }
        return list
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    fun <T> gsonToMaps(gsonString: String?): Map<String, T>? {
        var map: Map<String, T>? = null
        if (gson != null) {
            map = gson.fromJson(
                gsonString,
                object : TypeToken<Map<String?, T>?>() {}.type
            )
        }
        return map
    }

    /**
     * 将Map转化为Json
     *
     * @param map
     * @return String
     */
    fun <T> mapToJson(map: Map<String?, T>?): String {
        val gson = Gson()
        return gson.toJson(map)
    }

    init {
        if (gson == null) {
            gson = Gson()
        }
    }
}