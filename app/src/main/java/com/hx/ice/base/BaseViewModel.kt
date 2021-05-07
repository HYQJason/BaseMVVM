package com.hx.ice.base

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.hx.ice.net.ApiClient
import kotlinx.coroutines.*

/**
 * @ClassName: BaseViewModel
 * @Description: Android中的ViewModel是一个可以用来存储UI相关的数据的类。ViewModel的生命周期会比创建它的Activity、Fragment的生命周期长。
 * @Author: WY-HX
 * @Date: 2021/3/26 10:21
 */
abstract class BaseViewModel : ViewModel(), ViewModelLifecycle, ViewBehavior {


    @SuppressLint("StaticFieldLeak")
    lateinit var application: Application

    private lateinit var lifcycleOwner: LifecycleOwner

    // loading视图显示Event
    var _loadingEvent = MutableLiveData<Boolean>()
        private set

    // 无数据视图显示Event
    var _emptyPageEvent = MutableLiveData<Boolean>()
        private set

    // toast提示Event
    var _toastEvent = MutableLiveData<Any?>()
        private set


    /**
     * 当一个函数没有返回值的时候，我们用Unit来表示这个特征，而不是null，大多数时候我们不需要显示地返回Unit，或者生命一个函数的返回值是Unit，编译器会推断它。
     * 在主线程中执行一个协程
     */
    protected fun launchOnUI(isShow: Boolean=false,block: suspend CoroutineScope.() -> Unit): Job {

        return viewModelScope.launch(Dispatchers.Main) {
            showLoadingUI(isShow)
            block()
        }
    }

    /**
     * 在IO线程中执行一个协程
     */
    protected fun launchOnIO(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(Dispatchers.IO) { block() }
    }



    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
        this.lifcycleOwner = owner
    }

    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onResume() {


    }

    override fun onPause() {


    }

    override fun onStop() {


    }

    override fun onDestroy() {


    }

    override fun dismiss() {
        _loadingEvent.postValue(false)
    }
    override fun showLoadingUI(isShow: Boolean) {
        _loadingEvent.postValue(isShow)
    }

    override fun showEmptyUI(isShow: Boolean) {
        _emptyPageEvent.postValue(isShow)
    }

    override fun showToast(arg: Any) {
        _toastEvent.postValue(arg)
    }

    protected fun showToast(@StringRes resId: Int) {
        _toastEvent.postValue(resId)
    }
    companion object {
        @JvmStatic
        fun <T : BaseViewModel> createViewModelFactory(viewModel: T): ViewModelProvider.Factory {
            return ViewModelFactory(viewModel)
        }
    }
}

/**
 *@deprecated 改用DagGer
 * 创建ViewModel的工厂，以此方法创建的ViewModel，可在构造函数中传参
 */
class ViewModelFactory(val viewModel: BaseViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModel as T
    }
}