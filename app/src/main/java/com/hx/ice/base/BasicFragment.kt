package com.hx.ice.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hx.ice.extensions.observeNonNull


/**
 * @ClassName: IceFragment
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/4/16 10:27
 */
abstract class BasicFragment<B : ViewDataBinding, VM : BaseViewModel> : Fragment(), ViewBehavior {

    lateinit var viewModel: VM

    lateinit var binding: B
        private set

    /**
     * 缓存视图，如果视图已经创建，则不再初始化视图
     */
    private var rootView: View? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            injectDataBinding(inflater,container)
        }

        injectViewModel()
        initialize(savedInstanceState)
        initInternalObserver()
        return rootView
    }


    private fun injectViewModel() {
          val vm = createViewModel()
          viewModel = ViewModelProvider(this, BaseViewModel.createViewModelFactory(vm))
              .get(vm::class.java)
        viewModel.application = activity!!.application
        lifecycle.addObserver(viewModel)


    }

    protected abstract fun createViewModel(): VM
    fun getActivityViewModel(): VM {
        return viewModel
    }

    protected open fun injectDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.lifecycleOwner = this
        rootView = binding.root
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


    /**
     *  初始化操作
     */
    protected abstract fun initialize(savedInstanceState: Bundle?)

}