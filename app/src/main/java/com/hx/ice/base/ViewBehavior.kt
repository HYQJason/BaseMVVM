package com.hx.ice.base

interface ViewBehavior {

    /**
     * 是否显示Loading视图
     */
    fun showLoadingUI(isShow: Boolean)

    fun dismiss(){}

    /**
     * 是否显示空白视图
     */
    fun showEmptyUI(isShow: Boolean)

    /**
     * 弹出Toast提示
     */
    fun showToast(arg: Any)



}