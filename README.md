#### 项目结构
act 功能模块<br>
base 基础类<br>
common 公共类<br>
extensions 扩展类<br>
constants 常量类<br>
net 网络请求<br>
util 工具类<br>
widget 自定义组件

#### webview
mAgentWeb = AgentWeb.with(this)//
			.setAgentWebParent((LinearLayout) view, -1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件。
			.useDefaultIndicator(-1, 3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
			.setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
			.setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
			.setWebChromeClient(new CommonWebChromeClient()) //WebChromeClient
			.setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。
			.setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
			.setAgentWebUIController(new UIController(getActivity())) //自定义UI  AgentWeb3.0.0 加入。
			.setMainFrameErrorView(R.layout.agentweb_error_page, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
			.useMiddlewareWebChrome(getMiddlewareWebChrome()) //设置WebChromeClient中间件，支持多个WebChromeClient，AgentWeb 3.0.0 加入。
			.additionalHttpHeader(getUrl(), "cookie", "41bc7ddf04a26b91803f6b11817a5a1c")
			.useMiddlewareWebClient(getMiddlewareWebClient()) //设置WebViewClient中间件，支持多个WebViewClient， AgentWeb 3.0.0 加入。
			.setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
			.interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
			.createAgentWeb()//创建AgentWeb。
			.ready()//设置 WebSettings。
			.go(getUrl()); //WebView载入该url地址的页面并显示。
调用js 无参
mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroid");
调用js 带参数
mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroidParam","Hello ! Agentweb");
调用js 带参数，带回调
mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroidMoreParams", new ValueCallback<String>() {
          @Override
          public void onReceiveValue(String value) {
                Log.i("Info","value:"+value);
          }
},getJson(),"say:", " Hello! Agentweb");
gentWeb.getJsAccessEntrace().quickCallJs("callByAndroidInteraction","你好Js");