package com.hx.ice.act.login.ui

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hx.ice.R
import com.hx.ice.act.login.OnVerificationCodeListener
import com.hx.ice.act.main.MainActivity
import com.hx.ice.base.BaseActivity
import com.hx.ice.base.Preference
import com.hx.ice.common.RouterConstants.LOGIN_PASSWORD
import com.hx.ice.common.RouterConstants.MAIN
import com.hx.ice.common.RouterConstants.VERIFICATION_SMS_CODE
import com.hx.ice.constants.IceConstants.Login_Forgot_Activity
import com.hx.ice.constants.IceConstants.Sms_Forgot_Activity
import com.hx.ice.databinding.ActivityVerificationCodeBinding
import com.hx.ice.model.SmsCodeModel
import com.hx.ice.util.NavigationUtil.routerNavigation
import com.hx.ice.util.RxTimerUtil
import com.hx.ice.util.StackManageUtil
import com.hx.ice.widget.SecurityCodeView


/**
 * @ClassName VerificationCodeActivity
 * @description 描述 验证短信验证码
 * @author: Jason
 * @date:
 */
@Route(path = VERIFICATION_SMS_CODE)
class VerificationCodeActivity : BaseActivity<ActivityVerificationCodeBinding, SmsCodeModel>() {
    @JvmField
    @Autowired
    var areaCode = ""

    @JvmField
    @Autowired
    var phone = ""

    @JvmField
    @Autowired
    var type = -1

    @JvmField
    @Autowired
    var activityType = ""
    var inviterCode by Preference("inviterCode", "")
    var nickname by Preference("nickname", "")

    override fun getLayoutId() = R.layout.activity_verification_code

    override fun initialize(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        binding.viewListener = OnVerificationCodeListener(this)

        binding.smsCodeModel = viewModel

        viewModel.sendCode(areaCode, phone, type)

        viewModel.sendCodeLiveData.observe(this) {
            if (it) startCountDown()
        }

        viewModel.loginLiveData.observe(this) {
            accessToken = it.accessToken ?: ""
            inviterCode = it.inviterCode ?: ""
            nickname = it.nickname ?: ""
            if (activityType == Sms_Forgot_Activity) {
                routerNavigation(LOGIN_PASSWORD, "activityType", Login_Forgot_Activity)
            } else {
                StackManageUtil.closeOtherOnlyActivity(MainActivity(), MAIN)
            }
        }
        viewModel.loginStateLiveData.observe(this) {
            if (!it) {
                binding.securityCodeView.run {
                    clearEditText(this@VerificationCodeActivity)
                    showInput(this@VerificationCodeActivity)
                }
            }

        }
        binding.securityCodeView.setInputCompleteListener(object : SecurityCodeView.InputCompleteListener {
            override fun inputComplete(code: String?) {
                code?.run {
                    viewModel.login(areaCode, "", phone, "", code, "", 1)
                }
            }
        })
    }

    override fun createViewModel() = SmsCodeModel()

    private fun startCountDown() {
        RxTimerUtil.startTimer(60,
            {
                viewModel.setCountDown("${getString(R.string.Resend)} ${it}s")
            }, {
                viewModel.setCountDown("Resend Again")
                binding.tvResend.setTextColor(ContextCompat.getColor(this, R.color.color_FF2F51))
            })
    }
}