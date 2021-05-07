package com.hx.ice.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hx.ice.act.main.bean.UserInfo
import com.hx.ice.base.BaseViewModel
import com.hx.ice.net.ApiClient
import com.hx.ice.net.service.IceService

/**
 * @ClassName: MainModel
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/4/19 12:56
 */
private const val TAG = "MainModel"

class MainModel : BaseViewModel() {
    private val data = MutableLiveData<UserInfo>()

    val infoData: LiveData<UserInfo> = data

    fun getData() {

    }


    init {
        data.value = UserInfo("111", 2)
    }

    override fun onCreate() {
        super.onCreate()
         getData()
    }


    fun changedInfo(s: String) {
        var userInfo = infoData.value
        userInfo?.name = s
        data.value = userInfo!!

    }

}