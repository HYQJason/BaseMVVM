package com.hx.ice.act.main

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.hx.ice.R
import com.hx.ice.base.BaseActivity
import com.hx.ice.common.RouterConstants
import com.hx.ice.common.RouterConstants.MAIN
import com.hx.ice.databinding.ActivityMainBinding
import com.hx.ice.model.MainModel
import com.hx.ice.util.ListenerUtil
import com.hx.ice.util.NavigationUtil.routerNavigation

@Route(path = MAIN)
class MainActivity : BaseActivity<ActivityMainBinding, MainModel>() {


    private val imageUrl="https://img0.baidu.com/it/u=1591022950,3097001901&fm=26&fmt=auto&gp=0.jpg"

    override fun getLayoutId() = R.layout.activity_main

    override fun createViewModel() = MainModel()


    override fun initialize(savedInstanceState: Bundle?) {
        binding.url=imageUrl
        binding.onClickUtil = ListenerUtil {
            when (it.id) {
                R.id.tv_login -> {
                    routerNavigation(RouterConstants.LOGIN)
                }
                R.id.tv_out -> {
                   // ShareFactory.getShareStrategy("text")?.share("content")
                }
            }
        }
    }

}