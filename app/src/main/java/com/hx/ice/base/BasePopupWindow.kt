package com.hx.ice.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by wzx on 2018/5/8.
 */
abstract class BasePopupWindow<VB : ViewDataBinding>(protected var mActivity: Activity, w: Int, h: Int) {
   private var contentView: View
   private var popupWindow: PopupWindow


   protected abstract fun layoutId(): Int
    protected abstract fun initView()
    protected var mBinding: VB = DataBindingUtil.inflate(mActivity.layoutInflater, layoutId(), null, false)


    init {
        contentView = mBinding.root
        initView()
        popupWindow = PopupWindow(contentView, w, h, true)
        initWindow()
    }

    private fun initWindow() {
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.isOutsideTouchable = true
        popupWindow.isTouchable = true
        popupWindow.setOnDismissListener { darkenBackgroud(mActivity, 1f) }
    }

    fun showAsDropDown(anchor: View, xoff: Int, yoff: Int, light: Float) {
        popupWindow.showAsDropDown(anchor, xoff, yoff)
        darkenBackgroud(mActivity, light)
    }

    fun showAtLocation(parent: View?, gravity: Int, x: Int, y: Int, light: Float) {
        popupWindow.showAtLocation(parent, gravity, x, y)
        darkenBackgroud(mActivity, light)
    }

    private fun darkenBackgroud(activity: Activity, bgcolor: Float) {
        val lp = activity.window.attributes
        lp.alpha = bgcolor
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        activity.window.attributes = lp
    }


}