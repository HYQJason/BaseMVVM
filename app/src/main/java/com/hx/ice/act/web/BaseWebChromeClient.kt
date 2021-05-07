package com.hx.ice.act.web

import android.webkit.WebView
import com.just.agentweb.WebChromeClient

/**
 * @ClassName: BaseWebChromeClient
 * @Description:WebChromeClient：当影响浏览器的事件到来时，就会通过WebChromeClient中的方法回调通知用法。
 * WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等比如
 * @Author: WY-HX
 * @Date: 2021/4/28 16:16
 */
class BaseWebChromeClient: WebChromeClient() {
    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
    }
}