package com.hx.ice.base

import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.hx.ice.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * @ClassName: BaseDialogFragment
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/4/9 17:55
 * https://www.jianshu.com/p/db2946b0b217
 * https://blog.csdn.net/yu540135101/article/details/114885370
 */
/**
 * @author kuky.
 * @description
 */
typealias OnDialogFragmentDismissListener = (DialogInterface) -> Unit

typealias OnDialogFragmentCancelListener = (DialogInterface) -> Unit

abstract class BaseDialogFragment<VB : ViewDataBinding> : DialogFragment(),
    CoroutineScope by MainScope() {
    var onDialogFragmentDismissListener: OnDialogFragmentDismissListener? = null
    var onDialogFragmentCancelListener: OnDialogFragmentCancelListener? = null

    protected lateinit var mBinding: VB
    private var mSavedState = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setStyle(
            STYLE_NO_FRAME,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                android.R.style.Theme_Material_Dialog_Alert else android.R.style.Theme_Dialog
        )

        dialog?.window?.apply {
            requestFeature(Window.FEATURE_NO_TITLE)
            setWindowAnimations(dialogFragmentAnim())
        }

        mBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mSavedState = true
        super.onSaveInstanceState(outState)
    }

    /**
     * we suggest use this method to show dialog fragment instead of [show]
     */
    fun showAllowStateLoss(manager: FragmentManager, tag: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (manager.isStateSaved) return
        }

        if (mSavedState) return

        show(manager, tag)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setBackgroundDrawable(dialogFragmentBackground())
            attributes = dialogFragmentAttributes()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDialog(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
        cancel()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onDialogFragmentCancelListener?.invoke(dialog)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDialogFragmentDismissListener?.invoke(dialog)
    }

    // self define dialog fragment enter and exit animation
    open fun dialogFragmentAnim() = R.style.DialogPushInOutAnimation

    // self define dialog fragment attributes
    open fun dialogFragmentAttributes() = dialog?.window?.attributes?.apply {
        width = (Resources.getSystem().displayMetrics.widthPixels * 0.8f).toInt()
        height = WindowManager.LayoutParams.WRAP_CONTENT
        gravity = Gravity.CENTER
    }

    // self define dialog fragment background
    open fun dialogFragmentBackground() = ColorDrawable(Color.TRANSPARENT)

    abstract fun layoutId(): Int

    abstract fun initDialog(view: View, savedInstanceState: Bundle?)
}