package com.hx.ice.act.web

import android.content.Context
import android.os.Handler
import com.just.agentweb.AgentWeb
import android.os.Looper
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast

class AndroidInterface( private val context: Context) {
    private val deliver = Handler(Looper.getMainLooper())
    @JavascriptInterface
    fun callAndroid(msg: String? =null) {
        Log.d("Info", "Thread:" + Thread.currentThread())
        deliver.post {
            Log.d("Info", "main Thread:" + Thread.currentThread())
            Toast.makeText(context.applicationContext, "" + msg, Toast.LENGTH_LONG).show()
        }
        Log.d("Info", "Thread:" + Thread.currentThread())
    }
}