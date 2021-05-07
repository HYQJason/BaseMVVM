package com.hx.ice.util

import androidx.fragment.app.FragmentManager
import com.hx.ice.widget.LoadingDialog

/**
 * @ClassName: DialogUtil
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/4/9 11:35
 */
object DialogUtil {


    fun showLoadingDialog(manager: FragmentManager) {
        val dialog = LoadingDialog()
        dialog.show(manager, "LoadingDialog")
    }


}