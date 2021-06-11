package com.hx.ice.model

import androidx.lifecycle.MutableLiveData
import com.hx.ice.base.BaseViewModel

class LoginSmsPhoneModel:BaseViewModel() {

    val phoneNum= MutableLiveData<String>()

    fun setPhoneNum(num:String){
        phoneNum.value=num
    }
    fun isTrue():Boolean{
        if (phoneNum.value==null){return false
        }else{
            if (phoneNum.value!=null){
                if (phoneNum.value!!.length>=10 ) return true
            }

        }
      return false
    }
}