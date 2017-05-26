package com.ecottonyarn.yarn;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import Base.BaseActivity;
import Base.BaseWebView;
import Base.BaseWebViewActivity;
import Base.WebViewSettingParam;
import Util.LogUtil;
import javascript.JavaScripMethods;

public class TestActivity extends BaseWebViewActivity {
    private BaseWebView webView;
    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button btn = (Button) findViewById(R.id.txtTest);
        initActivity();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JavaScripMethods javaScripMethods = new JavaScripMethods(webView, TestActivity.this);
                javaScripMethods.invokeJavaScript("qrScanResult", "hello world");
            }
        });

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
        initComponent(webView, null);
    }
}
