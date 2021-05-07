package com.hx.ice.util

import android.app.Activity
import android.os.Parcelable
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.hx.ice.R
import java.io.Serializable


/**
 * 页面跳转
 */
object NavigationUtil {

    fun routerNavigation(path: String) {
        ARouter.getInstance().build(path).withTransition(R.anim.fade_in, R.anim.fade_out)
            .navigation()
    }

    fun routerNavigation(path: String, activity: Activity, callback: () -> Unit) {
        ARouter.getInstance().build(path).withTransition(R.anim.fade_in, R.anim.fade_out)
            .navigation(activity, object : NavCallback() {
                override fun onArrival(postcard: Postcard?) {
                    callback()
                }
            })
    }

    /**
     * 带回调跳转
     */
    fun routerNavigation(
        path: String,
        map: MutableMap<String, Any>,
        activity: Activity,
        callback: () -> Unit
    ) {
        ARouter.getInstance().build(path).withTransition(R.anim.fade_in, R.anim.fade_out).apply {
            map.forEach {
                when (it.value) {
                    is String -> this.withString(it.key, it.value.toString())
                    is Int -> this.withInt(it.key, formatIntPlaces(it.value.toString()))
                    is Long -> this.withLong(it.key, it.value.toString().toLong())
                    is Parcelable -> this.withParcelable(it.key, it.value as Parcelable)
                    is Serializable -> this.withSerializable(it.key, it.value as Serializable)
                    is Boolean -> this.withBoolean(it.key, it.value.toString().toBoolean())
                    else -> this.withObject(it.key, it.value)
                }
            }
        }.navigation(activity, object : NavCallback() {
            override fun onArrival(postcard: Postcard?) {
                callback()
            }
        })
    }

    /**
     * 带回调跳转
     */
    fun routerNavigation(
        path: String,
        map: MutableMap<String, Any>,
        activity: Activity
    ) {
        ARouter.getInstance().build(path).withTransition(R.anim.fade_in, R.anim.fade_out).apply {
            map.forEach {
                when (it.value) {
                    is String -> this.withString(it.key, it.value.toString())
                    is Int -> this.withInt(it.key, formatIntPlaces(it.value.toString()))
                    is Long -> this.withLong(it.key, it.value.toString().toLong())
                    is Boolean -> this.withBoolean(it.key, it.value.toString().toBoolean())
                    is Parcelable -> this.withParcelable(it.key, it.value as Parcelable)
                    is Serializable -> this.withSerializable(it.key, it.value as Serializable)
                    else -> this.withObject(it.key, it.value)
                }
            }
        }.navigation(activity, 10086)
    }

    /**
     * 跳转
     */
    fun routerNavigation(path: String, map: MutableMap<String, Any>) {
        ARouter.getInstance().build(path).withTransition(R.anim.fade_in, R.anim.fade_out).run {
            map.forEach {
                when (it.value) {
                    is String -> this.withString(it.key, it.value.toString())
                    is Int -> this.withInt(it.key, formatIntPlaces(it.value.toString()))
                    is Long -> this.withLong(it.key, it.value.toString().toLong())
                    is Boolean -> this.withBoolean(it.key, it.value.toString().toBoolean())
                    is Parcelable -> this.withParcelable(it.key, it.value as Parcelable)
                    is Serializable -> this.withSerializable(it.key, it.value as Serializable)
                    else -> {
                        this.withObject(it.key, it.value)
                    }
                }
            }
            navigation()
        }
    }

    fun routerNavigation(path: String, key: String, value: Any) {
        if (value is String && (value.isEmpty())) return
        routerNavigation(path, mutableMapOf(key to value))
    }

    fun formatIntPlaces(value: String?): Int {
        if (value.isNullOrEmpty())
            return 0

        return value.toInt()
    }

}


