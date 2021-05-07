package com.hx.ice.widget

import android.app.Activity
import android.content.Context
import android.os.Vibrator
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.RelativeLayout
import com.hx.ice.R

/**
 * @ClassName: SecurityCodeView
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/4/25 18:28
 */
class SecurityCodeView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    private var editViews: Array<EditText>? = null
    private val count = 6
    private var mContent: String? = null
    private var mIsClear = false

    init {
        initView()
    }

    private fun initView() {

        inflate(context, R.layout.view_security_code, this)
        editViews=  arrayOf(
            findViewById(R.id.item_code_iv1),
            findViewById(R.id.item_code_iv2),
            findViewById(R.id.item_code_iv3),
            findViewById(R.id.item_code_iv4),
            findViewById(R.id.item_code_iv5),
            findViewById(R.id.item_code_iv6),
        )



        for (i in 0 until count) {
            editViews?.let {
                it[i].isEnabled = i == 0
                it[i].addTextChangedListener(textWatch(i))
                it[i].setOnKeyListener(keyListener(i))
                it[i].onFocusChangeListener = focusListener(i)
            }
        }

    }

    /**
     * 焦点获取事件
     *
     * @param index
     * @return
     */
    private fun focusListener(index: Int): OnFocusChangeListener? {
        return OnFocusChangeListener { _, hasFocus ->
            if (index == 5) {
                if (hasFocus) {
                    editViews!![index].isSelected = TextUtils.isEmpty(editViews!![index].text.toString())
                } else {
                    editViews!![index].isSelected = false
                }
            } else {
                editViews!![index].isSelected = hasFocus
            }
        }
    }

    /**
     * 软键盘删除事件监听
     *
     * @param index
     * @return
     */
    private fun keyListener(index: Int): OnKeyListener? {
        return object : OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (index == 5) {
                        editViews?.let {
                            if (it[5].text.toString().isEmpty()) {
                                it[index - 1].setText("")
                                it[index - 1].isFocusableInTouchMode = true
                                it[index - 1].requestFocus()
                                it[index].isEnabled = false
                            } else {
                                it[index].isCursorVisible = true
                                it[index].setText("")
                                it[index].isFocusableInTouchMode = true
                                it[index].requestFocus()
                                it[index].isSelected = true
                            }
                        }

                    } else if (index > 0) {
                        editViews?.let {
                            it[index - 1].setText("")
                            it[index - 1].isFocusableInTouchMode = true
                            it[index - 1].requestFocus()
                            it[index].isEnabled = false
                        }

                    }
                    return true
                }
                return false
            }
        }
    }

    /**
     * EditText编辑事件
     *
     * @param index
     * @return
     */
    private fun textWatch(index: Int): TextWatcher? {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!mIsClear || index == 0 && mIsClear) {
                    if (s.toString().length == 1) {
                        mIsClear = false
                        if (index < 5) {
                            editViews?.let {
                                it[index].isFocusableInTouchMode = false
                                it[index].isFocusable = false
                                it[index + 1].isEnabled = true
                                it[index + 1].isFocusableInTouchMode = true
                                it[index + 1].requestFocus()
                                if (index == 4) it[index + 1].isCursorVisible = true //显示光标
                            }

                        } else if (index == 5) {
                            editViews?.let {
                                it[index].isCursorVisible = false //将光标隐藏
                                it[index].isSelected = false
                                it[index].isEnabled = false
                            }

                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {
                if (!mIsClear) {
                    val buffer = StringBuffer()
                    if (TextUtils.isEmpty(s.toString())) {
                        if (index == 0) {
                            mContent = s.toString()
                        } else {
                            if (!TextUtils.isEmpty(mContent) && mContent!!.isNotEmpty()) {
                                mContent = mContent!!.substring(0, index)
                            }
                        }
                    } else {
                        if (TextUtils.isEmpty(mContent)) {
                            mContent = buffer.append(s).toString()
                        } else {
                            mContent = buffer.append(mContent).append(s).toString()
                        }
                    }
                    if (mContent!!.length == count && inputCompleteListener != null) {
                        inputCompleteListener!!.inputComplete(mContent)
                    }
                }
            }
        }
    }

    /**
     * 清空输入内容
     */
    fun clearEditText(context: Context) {
        mContent = ""
        mIsClear = true
        //手机震动
        /*   val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
           vibrator.vibrate(300)
           val shake = AnimationUtils.loadAnimation(context, R.anim.input_error_shake)*/
        for (i in count - 1 downTo 0) {
            // editViews!![i].startAnimation(shake)
            editViews?.let {
                it[i].setText("")
                if (i == 0) {
                    it[i].isFocusableInTouchMode = true
                    it[i].isFocusable = true
                    it[i].isEnabled = true
                    it[i].requestFocus()
                    it[i].isSelected = true
                } else {
                    it[i].isEnabled = false
                    it[i].isFocusableInTouchMode = false
                    it[i].isFocusable = false
                }
            }

        }
    }

    private var inputCompleteListener: InputCompleteListener? = null

    fun setInputCompleteListener(inputCompleteListener: InputCompleteListener?) {
        this.inputCompleteListener = inputCompleteListener
    }

    interface InputCompleteListener {
        fun inputComplete(code: String?)
    }

    /**
     * 获取输入文本
     *
     * @return
     */
    fun getEditContent(): String? {
        return mContent
    }


    /**
     * 该activity自动显示软键盘
     *
     * @param context
     */
    fun showInput(context: Activity) {
        context.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }


    /**
     * 拦截软键盘上的完成键
     *
     * @param event
     * @return
     */
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return if (event.keyCode == KeyEvent.KEYCODE_BACK || event.keyCode == KeyEvent.KEYCODE_ENTER) {
            true
        } else super.dispatchKeyEvent(event)
    }
}