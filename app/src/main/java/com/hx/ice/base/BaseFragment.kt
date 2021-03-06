package com.hx.ice.base

import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.hx.ice.widget.LoadingDialog

/**
 * @ClassName: BaseAppVMFragment
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/4/19 10:23
 */
 abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : BasicFragment<B, VM>() {
    lateinit var loadingDialog: LoadingDialog
    override fun showLoadingUI(isShow: Boolean) {
        if (isShow) {
            if (!this::loadingDialog.isInitialized) {
                loadingDialog = LoadingDialog()
            } else {
                if (loadingDialog.dialog != null) {
                    if (loadingDialog.dialog!!.isShowing) {
                        loadingDialog.dismiss()
                    }
                }

            }
            loadingDialog.show(parentFragmentManager, "loadDialog")
        } else {
            dismiss()
        }

    }

    override fun dismiss() {
        if (!this::loadingDialog.isInitialized) return
        if (loadingDialog.dialog != null) {
            if (loadingDialog.dialog!!.isShowing) {
                loadingDialog.dismiss()
            }
        }

    }

    override fun showEmptyUI(isShow: Boolean) {

    }

    override fun showToast(arg: Any) {
        Toast.makeText(
            activity,
            if (arg is Int) resources.getText(arg) else arg.toString(),
            Toast.LENGTH_SHORT
        ).show()
        if (!this::loadingDialog.isInitialized) return
        if (loadingDialog.dialog != null) {
            if (loadingDialog.dialog!!.isShowing) {
                loadingDialog.dismiss()
            }
        }

    }
}