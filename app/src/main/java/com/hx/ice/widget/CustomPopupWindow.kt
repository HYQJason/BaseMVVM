package com.hx.ice.widget

import android.app.Activity
import com.hx.ice.R
import com.hx.ice.base.BasePopupWindow
import com.hx.ice.databinding.DialogCustomBinding
import com.hx.ice.util.DensityUtil

/**
 * @ClassName: CustomPopuwindow
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/4/30 15:31
 */
class CustomPopupWindow(mActivity: Activity) : BasePopupWindow<DialogCustomBinding>(mActivity, DensityUtil.dip2px(mActivity, 200f), DensityUtil.dip2px(mActivity, 200f)) {

    /**
     *
     *   val customPopupWindow = CustomPopupWindow(this)
    customPopupWindow.showAtLocation(binding.root, Gravity.CENTER,0,0,0.5f)
     * */

    override fun layoutId() = R.layout.dialog_custom

    override fun initView() {
        mBinding.tvCustom.text = "CustomPopupWindow"

    }


}