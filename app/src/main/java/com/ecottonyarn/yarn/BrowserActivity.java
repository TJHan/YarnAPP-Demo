package com.ecottonyarn.yarn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import Base.BaseWebView;
import Base.BaseWebViewActivity;
import Base.WebViewSettingParam;
import Util.LogUtil;
import javascript.JavaScripMethods;

/**
 * 浏览活动页
 */
public class BrowserActivity extends BaseWebViewActivity {

    private static final String TAG = "BrowserActivity";
    private BaseWebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        initActivity();
    }


    private void initActivity() {
        webView = (BaseWebView) findViewById(R.id.webView_browser);
        //加载易纱web站
        String yarnWebSiteUrl = getIntent().getStringExtra("url");
        LogUtil.d(TAG, "地址：" + yarnWebSiteUrl);
        webView.loadUrl(yarnWebSiteUrl);

        WebViewSettingParam params = new WebViewSettingParam(null, null, null, null, false, null);
        webView.initWebViewSettings(params);
        JavaScripMethods javaScripMethods = new JavaScripMethods(webView, this);
        webView.addWebViewJavascriptInterface(javaScripMethods);

        progressBar = (ProgressBar) findViewById(R.id.processBar_browser);
        initComponent(webView, progressBar);
    }
}
