package com.hx.ice.widget

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.hx.ice.R

class LoginSuccessDialog : DialogFragment() {



    companion object {
        private val loginSuccessDialog by lazy {
            LoginSuccessDialog()
        }

        fun getInstance(): LoginSuccessDialog {
            return loginSuccessDialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      //  dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return View.inflate(context, R.layout.login_success_layout, container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.TranslucentBackground)
    }


 /*   override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
         val dialog = activity?.let { Dialog(it, R.style.TranslucentBackground) }
      val view: View = LayoutInflater.from(activity).inflate(R.layout.login_success_layout, null)
           dialog?.setContentView(view)
        *//*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }*//*
        return dialog!!

    }*/
}