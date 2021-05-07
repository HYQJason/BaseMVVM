package com.hx.ice.util

import android.view.View
import com.hx.ice.base.IceApplication

/**
 * @ClassName: ListenerUtil
 * @Description: 简单的业务 可直接使用本类，通过回调处理业务逻辑
 * @Author: WY-HX
 * @Date: 2021/4/26 13:43
 */
open class ListenerUtil(private  val callback: ((View) -> Unit)? = null) {

    open  fun onClick(v: View) {
        if (!NetUtil.isNetworkAvailable(IceApplication.instance)) {
            ToastUtil.show("The network connection is abnormal. Please try again later")
            return
        }
        if (FaskClickUitl.isFastClick()) return
        callback?.invoke(v)
    }

}