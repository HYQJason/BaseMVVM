package com.hx.ice.base

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.hx.ice.BuildConfig
import com.hx.ice.util.AutoDensityUtil
import org.greenrobot.eventbus.EventBus
import kotlin.properties.Delegates

/**
 * @ClassName: MVApplication
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/4/1 16:26
 */
class BaseApplication : BasicApplication() {

    companion object {
        private const val TAG = "MVApplication"

        var context: Context by Delegates.notNull()
            private set

        lateinit var instance: BaseApplication

    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        initARouter()
        AutoDensityUtil.init()
       // EventBus.builder().addIndex(MyEventBusIndex()).installDefaultEventBus()
    }

    private fun initARouter() {
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()  // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this)
    }


}