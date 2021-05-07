package com.hx.ice.act.web

import android.os.Bundle
import android.view.KeyEvent
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hx.ice.R
import com.hx.ice.base.BaseActivity
import com.hx.ice.common.RouterConstants.WEB_VIEW_ACTIVITY
import com.hx.ice.databinding.ActivityWebBinding
import com.hx.ice.model.WebModel
import com.just.agentweb.*


private const val TAG = "WebActivity"

@Route(path = WEB_VIEW_ACTIVITY)
class WebActivity : BaseActivity<ActivityWebBinding, WebModel>() {
    var mAgentWeb: AgentWeb? = null

    @JvmField
    @Autowired
    var webUrl: String? = null

    @JvmField
    @Autowired
    var title: String? = null

    @JvmField
    @Autowired
    var isShowTitle = true


    override fun getLayoutId() = R.layout.activity_web

    override fun initialize(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        initTitle()

        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.lineContainer, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebChromeClient(BaseWebChromeClient())
            .setWebViewClient(BaseWebViewClient())
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setWebView(NestedScrollAgentWebView(this))
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK) //打开其他应用时，弹窗咨询用户是否前往其他应用
            .interceptUnkownUrl() //拦截找不到相关页面的Scheme
            .createAgentWeb()
            .ready()
            .go(webUrl)

        //js  to android
        mAgentWeb!!.jsInterfaceHolder.addJavaObject("android", AndroidInterface(this))

    }

    private fun initTitle() {
        setTitleVisibility(isShowTitle)
        if (!title.isNullOrEmpty()) {
            setCenterTitle(title!!)
        }
    }

    override fun createViewModel() = WebModel()


    override fun onResume() {
        mAgentWeb?.let {
            it.webLifeCycle.onResume()
        }
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb?.let {
            it.webLifeCycle.onPause()
        }
        super.onPause()

    }

    override fun onDestroy() {
        super.onDestroy()
        mAgentWeb?.let {
            //mAgentWeb.destroy();
            it.getWebLifeCycle().onDestroy()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        mAgentWeb?.let {
            if (it.handleKeyEvent(keyCode, event)) {
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}