package com.hx.ice.act.web

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.just.agentweb.WebViewClient

/**
 * @ClassName: BaseWebViewClient
 * @Description:
 * @Author: WY-HX
 * @Date: 2021/4/28 11:08
 */
class BaseWebViewClient : WebViewClient() {

    /**
     * 拦截 url 跳转,在里边添加点击链接跳转或者操作
     */
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return super.shouldOverrideUrlLoading(view, request)
    }

    /**
     * 在开始加载网页时会回调
     */
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }

    /**
     * 在结束加载网页时会回调
     */
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
    }

    /**加载错误的时候会回调，在其中可做错误处理，比如再请求加载一次，或者提示404的错误页面
    public void onReceivedError
     当接收到https错误时，会回调此函数，在其中可以做错误处理
    public void onReceivedSslError
     在每一次请求资源时，都会通过这个函数来回调
    public WebResourceResponse shouldInterceptRequest*/
}