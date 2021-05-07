package com.hx.ice.util


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Created by jason on 2017/7/21.
 */
object RxTimerUtil {

    fun startTimer(time: Int, doNext: (time: Int) -> Unit, finish: () -> Unit): Disposable? {
        return countdown(time) {
            if (it <= 0) finish() else doNext(it)
        }
    }

    /**
     * 倒计时
     */
    fun countdown(time: Int, doNext: (time: Int) -> Unit): Disposable? {
        var time = time
        if (time < 0) time = 0
        val countTime = time
        return Observable.interval(0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .map { increaseTime -> countTime - increaseTime.toInt() }
            .take(countTime + 1.toLong()).subscribe {
                doNext(it)
            }
    }

    /**
     * milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    fun timer(milliseconds: Long, doNext: (time: Long) -> Unit) {
        Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                doNext(it)
            }
    }

    /**
     * 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    fun interval(milliseconds: Long, doNext: (time: Long) -> Unit) {
        Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                doNext(it)
            }
    }

}