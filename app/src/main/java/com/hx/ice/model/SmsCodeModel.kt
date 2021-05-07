package com.hx.ice.model

import androidx.lifecycle.MutableLiveData
import com.hx.ice.act.login.bean.LoginBean
import com.hx.ice.base.BaseViewModel
import com.hx.ice.net.ApiClient
import com.hx.ice.net.service.IceService
import com.hx.ice.util.RequestUtil
import java.util.*

/**
 * @ClassName: SmsCode
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/4/25 14:53
 */
class SmsCodeModel : BaseViewModel() {
    val countDown = MutableLiveData<String>()
    val sendCodeLiveData = MutableLiveData<Boolean>()
    val loginLiveData = MutableLiveData<LoginBean>()
    val loginStateLiveData = MutableLiveData<Boolean>()
    fun sendCode(areaCode: String, phone: String, type: Int) {
        launchOnUI(true) {
            ApiClient.request(IceService::class.java) {
                it.sendCode(areaCode, phone, type)
            }.doSuccess {
                dismiss()
                sendCodeLiveData.value = it ?: false
            }
                .doFailure { _, msg -> showToast(msg ?: "") }
                .doError { _, error -> showToast(error.message) }
                .procceed()
        }
    }

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
            ApiClient.request(IceService::class.java) {
                it.login(RequestUtil.getRequestBody(treeMap))
            }.doSuccess {
                dismiss()
                loginLiveData.value = it
                loginStateLiveData.value = true
            }
                .doFailure { _, msg ->
                    showToast(msg ?: "")
                    loginStateLiveData.value = false
                }
                .doError { _, error ->
                    showToast(error.message)
                    loginStateLiveData.value = false
                }
                .procceed()
        }
    }


    fun setCountDown(time: String) {
        countDown.value = time
    }


}