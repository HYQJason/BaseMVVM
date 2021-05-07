package com.hx.ice.util

import android.os.Handler
import android.os.Process
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.hx.ice.R
import com.hx.ice.base.BaseApplication
import com.hx.ice.base.BaseApplication.Companion.instance
import com.hx.ice.base.BaseApplication.Companion.mainThreadId

/**
 * @Function:Toast工具类
 */
private const val TAG = "ToastUtil"
object ToastUtil {

    private var oldMsg: CharSequence = StringUtil.EMPTY
    private  var oldStringId = 0
    private var mToast: Toast? = null
    private  var oneTime: Long = 0
    private  var twoTime: Long = 0


    /**
     * 获取主线程的handler
     */
    private fun getHandler(): Handler {
        return BaseApplication.handler
    }

    /**
     * 在主线程执行runnable
     */
    private fun post(runnable: Runnable): Boolean {
        return getHandler().post(runnable)
    }

    /**
     * 判断当前的线程是不是在主线程
     * @return
     */
    private fun isRunInMainThread(): Boolean {
        return Process.myTid().toLong() == getMainThreadId()
    }

    private fun getMainThreadId(): Long {
        return mainThreadId.toLong()
    }


    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    fun show(str: String) {
        if (isRunInMainThread()) {
            showToastSafe(str)
        } else {
            post(Runnable { showToastSafe(str) })
        }
    }


    private fun showToastSafe(msg: CharSequence) {
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.instance, msg, Toast.LENGTH_LONG)
            try {
                mToast!!.show()
            } catch (e: java.lang.Exception) {
            }
            oneTime = System.currentTimeMillis()
        } else {
            twoTime = System.currentTimeMillis()
          mToast?.let {
              if (msg == oldMsg) {
                  if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                      try {
                          it.show()
                      } catch (e: java.lang.Exception) {
                      }
                  }
              } else {
                  oldMsg = msg
                  it.setText(msg)
                  try {
                      it.show()
                  } catch (e: java.lang.Exception) {
                  }
              }
          }
        }
        oneTime = twoTime
    }


    fun show(@StringRes stringId: Int) {

        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.instance, stringId, Toast.LENGTH_LONG)
            try {
                mToast!!.show()
            } catch (e: java.lang.Exception) {
            }
            oneTime = System.currentTimeMillis()
        } else {
            twoTime = System.currentTimeMillis()
           mToast?.apply {
               if (stringId == oldStringId) {
                   if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                       try {
                           this.show()
                       } catch (e: java.lang.Exception) {
                       }
                   }
               } else {
                   oldStringId = stringId
                   this.setText(stringId)
                   try {
                       this.show()
                   } catch (e: java.lang.Exception) {
                   }
               }
           }
        }
        oneTime = twoTime
    }



    fun showShortCenter(msg: CharSequence) {
        if (TextUtils.isEmpty(msg)) {
            return
        }
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.instance, msg, Toast.LENGTH_SHORT)
            mToast!!.setGravity(Gravity.CENTER, 0, 0)
            try {
                mToast!!.show()
            } catch (e: java.lang.Exception) {
            }
            oneTime = System.currentTimeMillis()
        } else {
            twoTime = System.currentTimeMillis()
            if (msg == oldMsg) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    try {
                        mToast!!.show()
                    } catch (e: java.lang.Exception) {
                    }
                }
            } else {
                oldMsg = msg
                mToast!!.setText(msg)
                try {
                    mToast!!.show()
                } catch (e: java.lang.Exception) {
                }
            }
        }
        oneTime = twoTime
    }


    fun showShortCenter(@StringRes stringId: Int) {
        showShortCenter(instance.getString(stringId))
    }




    /**
     * 带问号图片的Toast
     * @param resId
     */
    fun showTip(@StringRes resId: Int) {
        showTip(instance.getString(resId), -1)
    }

    /**
     * 带问号图片的Toast
     */
    fun showTip(tip: String) {
        showTip(tip, -1)
    }

    /**
     * 带图片的Toast
     * @param resId
     */
    fun showTip(
        @StringRes resId: Int,
        @DrawableRes imgResId: Int
    ) {
        showTip(instance.getString(resId), imgResId)
    }


    /**
     * 带问号图片的Toast
     *
     * @param tip
     */
    fun showTip(
        tip: String?,
        @DrawableRes imgResId: Int
    ) {
        val toast = Toast(BaseApplication.instance)
        val inflater = LayoutInflater.from(BaseApplication.instance)
        val view: View = inflater.inflate(R.layout.toast_tip, null, false)
        val textView = view.findViewById<View>(R.id.tv_toast_tip) as TextView
        if (imgResId != -1) {
            val imageView: ImageView =
                view.findViewById<View>(R.id.img_toast_tip) as ImageView
            imageView.setImageResource(imgResId)
        }
        if (!TextUtils.isEmpty(tip)) textView.text = tip
        toast.view = view
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }



}
