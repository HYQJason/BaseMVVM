package com.hx.ice.base

import android.app.Application
import android.os.Handler
import android.os.Process


/**
 * @ClassName: BaseApplication
 * @Description: 如果我们代码中已经有继承的父类Application，则可以根据DaggerApplication源码去实现对应接口。
 * HasAndroidInjector接口，用于替代HasActivityInjector, HasServiceInjector, HasBroadcastReceiverInjector, HasSupportFragmentInjector四个接口，让Application中的代码更简洁
 * @Author: WY-HX
 * @Date: 2021/4/12 14:00
 */
 abstract class BaseApplication  : Application() {
    override fun onCreate() {
        super.onCreate()
        mainThreadId = Process.myTid()
        mainThread = Thread.currentThread()
        mMainThreadHandler = Handler(mainLooper)

    }



    companion object {
        lateinit var instance: BaseApplication

        /**
         * 获取主线程ID
         */
        /**
         * 主线程ID
         */
        var mainThreadId = -1

        /**
         * 获取主线程
         */
        /**
         * 主线程ID
         */
        var mainThread: Thread? = null

        /**
         * 主线程Handler
         */
      private lateinit  var mMainThreadHandler: Handler


        /**
         * 获取主线程的handler
         *//**
         * 获取主线程的handler
         */
         val handler: Handler
            get() = mMainThreadHandler


    }

    init {
        instance = this
    }
}