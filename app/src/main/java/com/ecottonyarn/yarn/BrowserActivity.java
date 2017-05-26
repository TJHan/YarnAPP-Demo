package com.ecottonyarn.yarn;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.Browser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //R.menu.defaulttoolbar参数可更换
        getMenuInflater().inflate(R.menu.defaulttoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(BrowserActivity.this, "back up", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void initActivity() {
        webView = (BaseWebView) findViewById(R.id.webView_browser);
        //加载易纱web站
        String yarnWebSiteUrl = getIntent().getStringExtra("url");
        LogUtil.d(TAG, "地址：" + yarnWebSiteUrl);
        webView.loadUrl(yarnWebSiteUrl);

        WebViewSettingParam params = new WebViewSettingParam(null, null, null, false, false, null);
        webView.initWebViewSettings(params);
        JavaScripMethods javaScripMethods = new JavaScripMethods(webView, this);
        webView.addWebViewJavascriptInterface(javaScripMethods);

        progressBar = (ProgressBar) findViewById(R.id.processBar_browser);
        initComponent(webView, progressBar);
    }

}
