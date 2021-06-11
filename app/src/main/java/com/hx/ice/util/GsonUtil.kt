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
    fun gsonString(`object`: Any?)=gson.toJson(`object`)

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    fun <T> gsonToBean(gsonString: String?, cls: Class<T>?)=gson.fromJson(gsonString, cls)

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
        return gson.fromJson(
            gsonString,
            object :
                TypeToken<List<Map<String?, T>?>?>() {}.type
        )
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    fun <T> gsonToMaps(gsonString: String?): Map<String, T>? {
        return  gson.fromJson(
            gsonString,
            object : TypeToken<Map<String?, T>?>() {}.type
        )
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
    }
}