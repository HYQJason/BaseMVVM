package com.hx.ice.act.login

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hx.ice.common.RouterConstants
import com.hx.ice.common.RouterConstants.WEB_VIEW_ACTIVITY
import com.hx.ice.util.FaskClickUitl
import com.hx.ice.util.ListenerUtil
import com.hx.ice.util.NavigationUtil

/**
 * @ClassName: OnVerificationCodeListener
 * @Description: 复杂的业务逻辑可继承ListenerUtil，在 this 处理自己的业务,可重写父类的onClick
 * @Author: WY-HX
 * @Date: 2021/4/26 13:43
 */
class OnVerificationCodeListener(val activity: AppCompatActivity,back: ((View) -> Unit)? = null) : ListenerUtil(back) {

    fun changeNumberClick(v: View) {
        if (FaskClickUitl.isFastClick()) return
     // activity.finish()

        NavigationUtil.routerNavigation(RouterConstants.MAIN)
    }


}