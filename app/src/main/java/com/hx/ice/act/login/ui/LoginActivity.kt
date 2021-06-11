package com.hx.ice.act.login.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.hx.ice.R
import com.hx.ice.base.BaseActivity
import com.hx.ice.common.RouterConstants.LOGIN
import com.hx.ice.common.RouterConstants.LOGIN_PASSWORD
import com.hx.ice.common.RouterConstants.LOGIN_SMS_PHONE
import com.hx.ice.constants.IceConstants
import com.hx.ice.constants.IceConstants.Login_Password_Activity
import com.hx.ice.constants.IceConstants.Login_SMS_Activity
import com.hx.ice.databinding.ActivityLoginBinding
import com.hx.ice.model.LoginModel
import com.hx.ice.util.NavigationUtil.routerNavigation
import crossoverone.statuslib.StatusUtil

@Route(path = LOGIN)
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginModel>() {
    override fun getLayoutId() = R.layout.activity_login

    override fun initialize(savedInstanceState: Bundle?) {

    }

    override fun createViewModel() = LoginModel()

    override fun initStatusBar() {
        StatusUtil.setSystemStatus(this, true, true)
        StatusUtil.setUseStatusBarColor(this, ContextCompat.getColor(this, R.color.color_00000000))
    }



}