package com.hx.ice.widget

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText

/**
 * @ClassName: BackEditText
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/4/25 18:17
 */
class BackEditText : AppCompatEditText {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    //不让其返回键隐藏软键盘
    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        return if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else super.onKeyPreIme(keyCode, event)
    }
}