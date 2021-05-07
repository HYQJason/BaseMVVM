package com.hx.ice.act.login.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hx.ice.R
import com.hx.ice.base.BaseActivity
import com.hx.ice.common.RouterConstants.LOGIN_PASSWORD
import com.hx.ice.common.RouterConstants.LOGIN_SMS_PHONE
import com.hx.ice.constants.IceConstants.Login_Find_Activity
import com.hx.ice.constants.IceConstants.Login_Forgot_Activity
import com.hx.ice.constants.IceConstants.Login_Password_Activity
import com.hx.ice.databinding.ActivityLoginPasswordBinding
import com.hx.ice.model.LoginPasswordModel
import com.hx.ice.util.ListenerUtil
import com.hx.ice.util.NavigationUtil.routerNavigation
import com.hx.ice.util.ViewUtil.afterTextChanged

@Route(path = LOGIN_PASSWORD)
class LoginPasswordActivity : BaseActivity<ActivityLoginPasswordBinding, LoginPasswordModel>() {
    @JvmField
    @Autowired
    var activityType = ""
    var title = ""
    var phoneHint = ""
    var passwordHint = ""
    private val phoneLength = 10
    private var phoneReady = false
    private var passwordReady = false

    override fun getLayoutId(): Int = R.layout.activity_login_password

    override fun initialize(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        binding.loginPasswordActivity = this
        initView()
        initEditTextWatcher()
        initOnClick()
    }

    override fun createViewModel(): LoginPasswordModel = LoginPasswordModel()

    private fun initView() {
        title = when (activityType) {
            Login_Password_Activity -> getString(R.string.activity_login_password_title)
            Login_Forgot_Activity -> getString(R.string.activity_login_forgot_title)
            else -> ""
        }
        phoneHint = when (activityType) {
            Login_Password_Activity -> getString(R.string.mobile_number)
            Login_Forgot_Activity -> getString(R.string.new_password)
            else -> ""
        }
        passwordHint = when (activityType) {
            Login_Password_Activity -> getString(R.string.password)
            Login_Forgot_Activity -> getString(R.string.new_password_again)
            else -> ""
        }
    }

    private fun initEditTextWatcher() {
        binding.etPhone.afterTextChanged {
            phoneReady = it.length >= phoneLength
            setContinueBg()
        }
        binding.etPassword.afterTextChanged {
            passwordReady = it.isNotEmpty()
            setContinueBg()
        }
    }

    private fun initOnClick() {
        binding.onClickUtil = ListenerUtil {
            when (it.id) {
                R.id.tv_forgot -> routerNavigation(LOGIN_SMS_PHONE, "activityType", Login_Find_Activity)
                R.id.iv_continue -> {
                    if (phoneReady && passwordReady)
                        viewModel.login(binding.tvCountry.text.toString().replace("+", ""), "", binding.etPhone.text.toString(), binding.etPassword.text.toString(), "", "", 2)
                }
            }
        }
    }

    private fun setContinueBg() {
        binding.ivContinue.setImageResource(if (phoneReady && passwordReady) R.mipmap.login_next_enable else R.mipmap.login_next_unable)
    }

}