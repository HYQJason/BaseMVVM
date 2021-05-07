package com.hx.ice.base

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.hx.ice.R
import com.hx.ice.util.ToastUtil
import com.hx.ice.widget.LoadingDialog
import com.hx.ice.widget.LoginSuccessDialog
import crossoverone.statuslib.StatusUtil

/**
 * @ClassName: BaseActivity
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/3/26 11:38
 */
abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : IceActivity<B, VM>() {
    private var tvTitle: TextView? = null
    private var rightTv: TextView? = null
    private var toolbar: Toolbar? = null
    var accessToken by Preference("accessToken", "")
    var isLoginSuccess by Preference("LoginSuccess", false)

    lateinit var loadingDialog: LoadingDialog
    override fun showLoadingUI(isShow: Boolean) {
        if (isShow) {
            if (!this::loadingDialog.isInitialized) {
                loadingDialog = LoadingDialog()
            } else {
                if (loadingDialog.dialog != null) {
                    if (loadingDialog.dialog!!.isShowing) {
                        loadingDialog.dismiss()
                    }
                }

            }

            loadingDialog.show(supportFragmentManager, "loadDialog")
        } else {
            dismiss()
        }

    }

    override fun dismiss() {
        if (!this::loadingDialog.isInitialized) return
        if (loadingDialog.dialog != null) {
            if (loadingDialog.dialog!!.isShowing) {
                loadingDialog.dismiss()
            }
        }

    }

    override fun showEmptyUI(isShow: Boolean) {

    }

    override fun showToast(message: Any) {
        if (message is Int) {
            ToastUtil.show(message)
        } else {
            ToastUtil.show(message.toString())
        }
        if (!this::loadingDialog.isInitialized) return
        if (loadingDialog.dialog != null) {
            if (loadingDialog.dialog!!.isShowing) {
                loadingDialog.dismiss()
            }
        }

    }

    override fun iniTitle() {
        toolbar = binding.root.findViewById(R.id.toolbar)
        toolbar?.let {
            tvTitle = toolbar?.findViewById(R.id.title_centerTv)
            rightTv = toolbar?.findViewById(R.id.title_rightTv)
            setNavigationOnClickListener(null)
        }

    }

    /**
     * Title VISIBLE or GONE
     * */
    fun setTitleVisibility(b: Boolean) {
        toolbar?.visibility = if (b) View.VISIBLE else View.GONE
    }

    /**
     * 左侧返回图标 resId
     * */
    fun setNavigationIcon(@DrawableRes resId: Int) {
        toolbar?.setNavigationIcon(resId)
    }

    /**
     * 左侧返回图标 drawable
     * */
    fun setNavigationIcon(drawable: Drawable) {
        toolbar?.navigationIcon = drawable
    }

    /**
     * 左侧箭头返回事件
     * */
    fun setNavigationOnClickListener(listener: View.OnClickListener? = null) {
        toolbar?.setNavigationOnClickListener {
            if (listener == null) finish() else listener
        }
    }

    /**
     * title  中心标题
     * back   左侧返回事件
     * rightBack 右侧返回事件
     * */
    fun setAppTitle(
        title: String,
        back: View.OnClickListener? = null,
        rightBack: View.OnClickListener? = null
    ) {
        tvTitle?.text = title
        setNavigationOnClickListener(back)
        setRightOnClickListener(rightBack)
    }

    /**
     * title  设置中心标题
     * */
    fun setCenterTitle(title: String) {
        tvTitle?.text = title
    }

    /**
     * 设置右侧title
     * true VISIBLE else GONE
     * */
    fun setRightVisibility(b: Boolean) {
        rightTv?.run {
            visibility = if (b) View.VISIBLE else View.GONE
        }
    }

    /**
     * title  设置右侧标题
     * */
    fun setRightTitle(title: String) {
        rightTv?.run {
            visibility = View.VISIBLE
            text = title
        }
    }

    /**
     * 设置右侧点击事件
     * */
    fun setRightOnClickListener(l: View.OnClickListener? = null) {
        rightTv?.setOnClickListener(l)
    }

    override fun initStatusBar() {
        StatusUtil.setUseStatusBarColor(this, ContextCompat.getColor(this, R.color.color_FFFFFF))

    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.ACTION_DOWN) {
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onResume() {
        super.onResume()
        if (!TextUtils.isEmpty(accessToken)&&!isLoginSuccess){
            //显示过一次之后不在显示
            isLoginSuccess=true
            LoginSuccessDialog.getInstance().show(supportFragmentManager,"LoginSuccessDialog")
        }

    }
}