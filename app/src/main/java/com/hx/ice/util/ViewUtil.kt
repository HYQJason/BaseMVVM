package com.hx.ice.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * @ClassName: ViewUtil
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/4/16 10:13
 */
object ViewUtil {

    /**
     * Extension function to simplify setting an afterTextChanged action to EditText components.
     */
    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}