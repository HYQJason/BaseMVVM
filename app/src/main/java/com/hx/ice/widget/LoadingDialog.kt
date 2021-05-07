package com.hx.ice.widget

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.hx.ice.R

/**
 * @ClassName: LoadingDialog
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/4/9 11:40
 */

private const val TAG = "LoadingDialog"
class LoadingDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
        dialog?.setCanceledOnTouchOutside(false)
        return View.inflate(context, R.layout.loading_layout,container)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
       /* val dialog = Dialog(context!!, R.style.TranslucentBackground)
        val view: View = LayoutInflater.from(activity).inflate(R.layout.loading_layout, null)
        val imageView: ImageView = view.findViewById(R.id.progressBar)
        *//* val animation = imageView.drawable as AnimationDrawable
         animation.start()*//*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        return dialog*/
        return super.onCreateDialog(savedInstanceState)
    }


}