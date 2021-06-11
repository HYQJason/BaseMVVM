package com.hx.ice.util

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import org.greenrobot.eventbus.EventBus
import java.lang.ref.WeakReference
import java.util.*
import kotlin.system.exitProcess

/**
 * activity 回退栈管理
 * 1. 进入主界面， 调用 closeOtherOnlyActivity(MainActivity)
 * 2. 退出app closeAll()
 *
 */
object StackManageUtil {

    private val cacheStack = Stack<WeakReference<FragmentActivity>>()

    fun isStackEmpty() = getActStackSize() == 0

    fun getActStackSize()= cacheStack.size

    /**
     * add
     */
    fun add(activity: FragmentActivity) = cacheStack.push(WeakReference(activity))

    /**
     * remove
     */
    fun removeActivity(activity: Activity) {
        if (isStackEmpty()) return
        cacheStack.forEachIndexed { index, item ->
            if (item.get() != null && item.get()!!::class.java == activity::class.java) {
                if (!item.get()!!.isFinishing || !activity.isFinishing) {
                    try {
                        item.get()?.finish()
                    } catch (e: Exception) {
                    }
                    try {
                        activity.finish()
                    } catch (e: Exception) {
                    }
                }
               cacheStack.removeAt(index)
                return
            }
        }
    }

    /**
     * remove item（移除栈内的activity对象）
     */
    fun removeActivityItem(item: FragmentActivity) {
        if (isStackEmpty()) return
        val current =
            cacheStack.findLast { it.get() != null && it.get()!!::class.java == item::class.java }
        if (current != null) {
            cacheStack.removeElement(current)
        }
    }

    //判断是否已打开过某一个界面
    fun hasActivity(activity: FragmentActivity): Boolean {
        cacheStack.forEach {
            if (it.get() != null && !it.get()!!.isFinishing && it.get()!!::class.java == activity::class.java) {
                return true
            }
        }
        return false
    }

    /**
     * 关闭其他,保留参数activity,如果activity不存在则新建
     */
    fun closeOtherOnlyActivity(activity: FragmentActivity, new_activity_path: String?) {
        var activityMain: WeakReference<FragmentActivity>? = null
        while (cacheStack.isNotEmpty()) {
            cacheStack.pop().run {
                if (this.get() != null && this.get()!!::class.java != activity::class.java) {
                    this.get()?.finish()
                } else {
                    activityMain = this
                }
            }
        }
        if (activityMain != null) {
            cacheStack.push(activityMain)
        } else {
            if (new_activity_path != null)
                NavigationUtil.routerNavigation(new_activity_path)
        }
    }

    /**
     * 回退特定次数
     */
    fun closeActivityByCount(count: Int) {
        var tempCount = count
        while ((--tempCount) >= 0) {
            cacheStack.pop().get()?.finish()
        }
    }

    /**
     * closeAll
     */
    fun closeAll() {
        while (!cacheStack.empty()) {
            cacheStack.pop().get()?.finish()
        }
        cacheStack.clear()
        exitProcess(0)
//        android.os.Process.killProcess(android.os.Process.myPid())
    }

//    fun closeAllOnlyOne() {
//        while (!cacheStack.empty()) {
//            val activity = cacheStack.pop().get()
//            if (activity != null && activity::class.java != MainActivity::class.java) {
//                activity.finish()
//            }
//        }
//        //NavigationUtil.routerNavigation(SkConstantsRouterPath.path_hxd_welcome)
//        currentActivity()?.finish()
//        LancherAppUtils.openCLD(currentActivity()?.packageName, currentActivity())
//
//        //cacheStack.clear()
//        //System.exit(0)
//    }

    fun currentActivity() = if (cacheStack.isNullOrEmpty()) null else cacheStack.peek().get()

}
