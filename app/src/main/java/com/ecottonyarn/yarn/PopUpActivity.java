package com.ecottonyarn.yarn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import Base.BaseWebView;
import Base.BaseWebViewActivity;
import Base.BaseWebViewClient;
import Base.WebViewSettingParam;
import Util.LogUtil;
import javascript.JavaScripMethods;

/**
 * 弹出活动页
 */
public class PopUpActivity extends BaseWebViewActivity {

    private static final String TAG = "PopUpActivity";

    private BaseWebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        initActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initActivity() {
        webView = (BaseWebView) findViewById(R.id.webView_popup);
        //加载易纱web站
        String yarnWebSiteUrl = getIntent().getStringExtra("url");
        LogUtil.d(TAG, "地址：" + yarnWebSiteUrl);
        webView.loadUrl(yarnWebSiteUrl);

        WebViewSettingParam params = new WebViewSettingParam(false, null, false, null, false, null);
        webView.initWebViewSettings(params);
        JavaScripMethods javaScripMethods = new JavaScripMethods(webView, PopUpActivity.this);
        webView.addWebViewJavascriptInterface(javaScripMethods);

        progressBar = (ProgressBar) findViewById(R.id.processBar_popup);
        BaseWebViewClient baseWebViewClient=new BaseWebViewClient(progressBar);
        webView.setWebViewClient(baseWebViewClient);
        webView.setOnKeyListener(TAG, PopUpActivity.this, false);
        initComponent(webView, progressBar);
    }
}
