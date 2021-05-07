package com.hx.ice.act.welcome

import android.os.Bundle
import com.hx.ice.R
import com.hx.ice.base.BaseActivity
import com.hx.ice.common.RouterConstants.MAIN
import com.hx.ice.databinding.ActivityWelcomeBinding
import com.hx.ice.model.WelcomeModel
import com.hx.ice.util.ListenerUtil
import com.hx.ice.util.NavigationUtil.routerNavigation
import com.hx.ice.util.RxTimerUtil
import io.reactivex.rxjava3.disposables.Disposable

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding, WelcomeModel>() {
    private var timer: Disposable? = null

    override fun getLayoutId(): Int = R.layout.activity_welcome

    override fun initialize(savedInstanceState: Bundle?) {
        binding.welcomeModel = viewModel
        startCountDown()
        initOnClick()
    }

    override fun createViewModel(): WelcomeModel = WelcomeModel()

    private fun initOnClick() {
        binding.onClickUtil = ListenerUtil {
            when (it.id) {
                R.id.tv_skip -> toMain()
            }
        }
    }

    private fun startCountDown() {
        timer = RxTimerUtil.startTimer(3,
            {
                viewModel.setCountDown("${it}s")
            }, {
                toMain()
            })
    }

    private fun toMain() {
        timer?.dispose()
        routerNavigation(MAIN)
        finish()
    }
}