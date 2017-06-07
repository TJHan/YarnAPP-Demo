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
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import Base.BaseWebView;
import Base.BaseWebViewActivity;
import Base.BaseWebViewClient;
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
    private RelativeLayout rl_Loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        WebView webView_loading = (WebView) findViewById(R.id.webview_loading);
        webView_loading.loadUrl("file:///android_asset/LOADING.html");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initActivity();
        ShowLog();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

        WebViewSettingParam params = new WebViewSettingParam(null, null, false, false, false, null);
        webView.initWebViewSettings(params);
        progressBar = (ProgressBar) findViewById(R.id.processBar_browser);
        initComponent(webView, progressBar);

        rl_Loading = (RelativeLayout) findViewById(R.id.rl_loading);
        BaseWebViewClient baseWebViewClient = new BaseWebViewClient(progressBar) {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                rl_Loading.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
                super.onPageStarted(view, url, favicon);
                application.Event_Handler.ExecHandler(mCallbackaction, "start", application.JavaScrip_Methods);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                rl_Loading.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
                application.Event_Handler.ExecHandler(mCallbackaction, "end", application.JavaScrip_Methods);
            }
        };
        webView.setWebViewClient(baseWebViewClient);

        webView.setOnKeyListener(TAG, BrowserActivity.this, false);
    }

    private void ShowLog() {

        application.Event_Handler.ExecHandler("netStateChanged", "hello return value.", application.JavaScrip_Methods);
    }

}
