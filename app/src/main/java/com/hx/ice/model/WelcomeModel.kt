package com.hx.ice.model

import androidx.lifecycle.MutableLiveData
import com.hx.ice.base.BaseViewModel

class WelcomeModel : BaseViewModel() {
    val countDownLiveData = MutableLiveData<String>()

    fun setCountDown(time: String) {
        countDownLiveData.value = time
    }
}