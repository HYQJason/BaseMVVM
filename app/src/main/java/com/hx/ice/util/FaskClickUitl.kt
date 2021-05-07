package com.hx.ice.util

/**
 * 快速单击处理
 */
object FaskClickUitl {
    private const val MIN_DELAY_TIME = 500  // 两次点击间隔不能少于1000ms
    private var lastClickTime: Long = 0
    private var lastClickTimeFor401: Long = 0

    fun isFastClick(): Boolean {
        var flag = true
        val currentClickTime = System.currentTimeMillis()
        if (currentClickTime - lastClickTime >= MIN_DELAY_TIME) {
            flag = false
        }
        lastClickTime = currentClickTime
        return flag
    }

    fun isFastClickFor401(): Boolean {
        var flag = true
        val currentClickTime = System.currentTimeMillis()
        if (currentClickTime - lastClickTimeFor401 >= MIN_DELAY_TIME) {
            flag = false
        }
        lastClickTimeFor401 = currentClickTime
        return flag
    }
}