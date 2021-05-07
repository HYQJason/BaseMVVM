#### 项目结构
act 功能模块<br>
base 基础类<br>
common 公共类<br>
extensions 扩展类<br>
constants 常量类<br>
net 网络请求<br>
util 工具类<br>
widget 自定义组件

ReplaceUrlInterceptor  会将FormBody 转换为 RequestBody   需根据业务需求修改
val stringBody =rootJsonObject.toString().toRequestBody("application/json;charset=utf-8".toMediaType())
val newBuilder = request.newBuilder().addHeader("appSignKey", SignUtils.AppSign(treeMap))
.url(request.url)
.method(request.method, stringBody)
.build()

#### webview
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