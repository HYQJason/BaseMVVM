package com.hx.ice.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.hx.ice.extensions.observeNonNull
import com.hx.ice.util.AutoDensityUtil
import com.hx.ice.util.StackManageUtil


/**
 * @ClassName: IceActivity
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/3/26 10:42
 */
private const val TAG = "IceActivity"

abstract class IceActivity<B : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(),
    ViewBehavior {

    lateinit var viewModel: VM

    lateinit var binding: B
        private set


    private fun injectViewModel() {
        val vm = createViewModel()
        viewModel = ViewModelProvider(this, BaseViewModel.createViewModelFactory(vm))
            .get(vm::class.java)
        viewModel.application = application
        lifecycle.addObserver(viewModel)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStatusBar()
        AutoDensityUtil.setCustomDensity(this, application)
        StackManageUtil.add(this)
        injectDataBinding()
        injectViewModel()
        initialize(savedInstanceState)
        initInternalObserver()


    }

    fun getActivityViewModel(): VM {
        return viewModel
    }

    protected fun injectDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
        iniTitle()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
        lifecycle.removeObserver(viewModel)

    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int


    private fun initInternalObserver() {
        viewModel._loadingEvent.observeNonNull(this) {
            showLoadingUI(it)
        }
        viewModel._emptyPageEvent.observeNonNull(this) {
            showEmptyUI(it)
        }
        viewModel._toastEvent.observeNonNull(this) {
            showToast(it!!)
        }

    }


    protected abstract fun iniTitle()
    protected abstract fun initStatusBar()

    /**
     *  初始化操作
     */
    protected abstract fun initialize(savedInstanceState: Bundle?)


    protected abstract fun createViewModel(): VM
}