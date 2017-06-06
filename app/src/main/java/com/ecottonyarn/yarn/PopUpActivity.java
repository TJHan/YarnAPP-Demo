package com.ecottonyarn.yarn;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;

import Base.BaseActivity;
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
        PrintLog();
    }

    private void initActivity() {
        webView = (BaseWebView) findViewById(R.id.webView_popup);
        //加载易纱web站
        String yarnWebSiteUrl = getIntent().getStringExtra("url");
        LogUtil.d(TAG, "地址：" + yarnWebSiteUrl);
        webView.loadUrl(yarnWebSiteUrl);

        WebViewSettingParam params = new WebViewSettingParam(false, null, false, null, false, null);
        webView.initWebViewSettings(params);

        progressBar = (ProgressBar) findViewById(R.id.processBar_popup);
        BaseWebViewClient baseWebViewClient = new BaseWebViewClient(progressBar){
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
        webView.setOnKeyListener(TAG, PopUpActivity.this, false);
        initComponent(webView, progressBar);
//        PrintLog();

    }

    private void PrintLog() {
        StringBuffer list = new StringBuffer();
        if (application != null && application.Global_Activity_List != null) {
            for (String key : application.Global_Activity_List.keySet()) {
                list.append("活动ID：" + application.Global_Activity_List.get(key).Activity_UUID + ",活动名称："+application.Global_Activity_List.get(key).getLocalClassName()+" \n");
            }
        }
        LogUtil.d(TAG, "活动列表：\n" + list.toString());
    }
}
