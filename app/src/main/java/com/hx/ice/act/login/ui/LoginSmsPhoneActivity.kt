package com.hx.ice.act.login.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hx.ice.R
import com.hx.ice.base.BaseActivity
import com.hx.ice.common.RouterConstants.LOGIN_SMS_PHONE
import com.hx.ice.common.RouterConstants.VERIFICATION_SMS_CODE
import com.hx.ice.constants.IceConstants.Login_Find_Activity
import com.hx.ice.constants.IceConstants.Login_Link_Activity
import com.hx.ice.constants.IceConstants.Login_SMS_Activity
import com.hx.ice.constants.IceConstants.Sms_Forgot_Activity
import com.hx.ice.constants.IceConstants.Sms_Login_Activity
import com.hx.ice.databinding.ActivityLoginSmsPhoneBinding
import com.hx.ice.model.LoginSmsPhoneModel
import com.hx.ice.util.ListenerUtil
import com.hx.ice.util.NavigationUtil.routerNavigation
import com.hx.ice.util.ViewUtil.afterTextChanged

@Route(path = LOGIN_SMS_PHONE)
class LoginSmsPhoneActivity : BaseActivity<ActivityLoginSmsPhoneBinding, LoginSmsPhoneModel>() {
    @JvmField
    @Autowired
    var activityType = ""
    var title = ""
    var topHint = ""
    private val phoneLength = 10

    override fun getLayoutId(): Int = R.layout.activity_login_sms_phone

    override fun initialize(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        binding.loginSmsActivity = this
        initView()
        initOnClick()
        initEditTextWatcher()

    }

    override fun createViewModel(): LoginSmsPhoneModel = LoginSmsPhoneModel()

    private fun initView() {
        title = when (activityType) {
            Login_SMS_Activity -> getString(R.string.activity_login_sms_phone_title)
            Login_Find_Activity -> getString(R.string.activity_login_find_phone_title)
            Login_Link_Activity -> getString(R.string.activity_login_link_phone_title)
            else -> ""
        }
        topHint = when (activityType) {
            Login_SMS_Activity -> getString(R.string.activity_login_sms_phone_top_hint)
            Login_Find_Activity -> getString(R.string.activity_login_find_phone_top_hint)
            Login_Link_Activity -> getString(R.string.activity_login_link_phone_top_hint)
            else -> ""
        }
    }

    private fun initOnClick() {
        binding.onClickUtil = ListenerUtil {
            if (binding.etPhone.text.length >= phoneLength) {
                routerNavigation(
                    VERIFICATION_SMS_CODE,
                    mutableMapOf(
                        "areaCode" to binding.tvCode.text.toString().replace("+", ""),
                        "phone" to binding.etPhone.text.toString(),
                        "type" to 1,
                        "activityType" to if (activityType == Login_Find_Activity) Sms_Forgot_Activity else Sms_Login_Activity
                    )
                )
            }
        }
    }

    private fun initEditTextWatcher() {
        binding.etPhone.afterTextChanged {
            binding.ivNext.setImageResource(if (it.length >= phoneLength) R.mipmap.login_next_enable else R.mipmap.login_next_unable)
        }
    }

}