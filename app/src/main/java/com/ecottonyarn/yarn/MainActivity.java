package com.ecottonyarn.yarn;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;

import Base.BaseWebView;
import Base.BaseWebViewActivity;
import Base.BaseWebViewClient;
import Base.WebViewSettingParam;
import Util.LogUtil;

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
        webView.setWebViewClient(new BaseWebViewClient(null));
        initComponent(webView, null);

        webView.setOnKeyListener(TAG, MainActivity.this, true);

        BaseWebViewClient baseWebViewClient = new BaseWebViewClient(null) {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                application.Event_Handler.ExecHandler(mCallbackaction, "start", application.JavaScrip_Methods);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                application.Event_Handler.ExecHandler(mCallbackaction, "end", application.JavaScrip_Methods);
            }
        };
        webView.setWebViewClient(baseWebViewClient);

    }

}
