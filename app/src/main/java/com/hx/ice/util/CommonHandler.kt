package com.hx.ice.util

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.lang.ref.WeakReference

/**
 * Created by xiangshanglive on 2017/7/21.
 */
class CommonHandler : Handler {
    interface MessageHandler {
        fun handleMessage(msg: Message?)
    }

    private var mMessageHandler: WeakReference<MessageHandler>

    constructor(msgHandler: MessageHandler) {
        mMessageHandler =
            WeakReference(
                msgHandler
            )
    }

    constructor(
        looper: Looper?,
        msgHandler: MessageHandler
    ) : super(looper!!) {
        mMessageHandler =
            WeakReference(
                msgHandler
            )
    }

    override fun handleMessage(msg: Message) {
        val realHandler =
            mMessageHandler.get()
        realHandler?.handleMessage(msg)
    }
}