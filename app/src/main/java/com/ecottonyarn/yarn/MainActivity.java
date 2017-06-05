package com.ecottonyarn.yarn;

import android.os.Bundle;

import java.util.HashMap;

import Base.BaseWebView;
import Base.BaseWebViewActivity;
import Base.BaseWebViewClient;
import Base.WebViewSettingParam;
import Util.LogUtil;
import javascript.JavaScripMethods;

public class MainActivity extends BaseWebViewActivity {
    private BaseWebView webView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivity();
    }

    private void initActivity() {
        webView = (BaseWebView) findViewById(R.id.webView);
        //加载易纱web站
        String yarnWebSiteUrl = this.getString(R.string.yarn_website_url);
        LogUtil.d(TAG, "地址：" + yarnWebSiteUrl);
        webView.loadUrl(yarnWebSiteUrl);

        WebViewSettingParam params = new WebViewSettingParam(null, null, null, null, false, null);
        webView.initWebViewSettings(params);
        JavaScripMethods javaScripMethods = new JavaScripMethods(webView, this);
        webView.addWebViewJavascriptInterface(javaScripMethods);
        webView.setWebViewClient(new BaseWebViewClient(null));
        initComponent(webView, null);
        webView.setOnKeyListener(TAG, MainActivity.this, true);
    }

}
