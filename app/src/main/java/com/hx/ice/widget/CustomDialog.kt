package com.hx.ice.widget

import android.os.Bundle
import android.view.View
import com.hx.ice.R
import com.hx.ice.base.BaseDialogFragment
import com.hx.ice.databinding.DialogCustomBinding

/**
 * @ClassName: CustomDialog
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/4/30 15:15
 */
class CustomDialog : BaseDialogFragment<DialogCustomBinding>() {
    override fun layoutId()= R.layout.dialog_custom

    override fun initDialog(view: View, savedInstanceState: Bundle?) {
      //  mBinding.tvCustom
    }


}