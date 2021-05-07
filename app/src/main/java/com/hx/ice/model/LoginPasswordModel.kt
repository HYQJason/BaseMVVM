package com.hx.ice.model

import androidx.lifecycle.MutableLiveData
import com.hx.ice.act.login.bean.LoginBean
import com.hx.ice.base.BaseViewModel
import com.hx.ice.net.ApiClient
import com.hx.ice.net.service.IceService
import com.hx.ice.util.RequestUtil
import java.util.*

class LoginPasswordModel:BaseViewModel() {
    val loginLiveData = MutableLiveData<LoginBean>()

    fun login(areaCode: String, inviterCode: String, mobile: String, password: String, smsCode: String, source: String, type: Int) {
        launchOnUI(true) {
            val treeMap = TreeMap<String, Any>()
            treeMap["areaCode"] = areaCode
            treeMap["inviterCode"] = inviterCode
            treeMap["mobile"] = mobile
            treeMap["password"] = password
            treeMap["smsCode"] = smsCode
            treeMap["source"] = source
            treeMap["type"] = type
            val requestBody = RequestUtil.getRequestBody(treeMap)
            ApiClient.request(IceService::class.java) {
                it.login(requestBody)
            }.doSuccess {
                dismiss()
                loginLiveData.value = it
            }
                .doFailure { _, msg -> showToast(msg ?: "") }
                .doError { _, error -> showToast(error.message) }
                .procceed()
        }
    }

}