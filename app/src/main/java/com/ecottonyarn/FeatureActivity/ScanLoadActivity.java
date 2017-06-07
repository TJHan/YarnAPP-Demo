package com.ecottonyarn.FeatureActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.ecottonyarn.yarn.GlobalApplication;
import com.ecottonyarn.yarn.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import Base.BaseApplication;
import Base.BaseWebView;
import Util.EventController;
import javascript.JavaScripMethods;

/**
 * Created by tjhan on 2017-05-23.
 * 扫描二维码启动类
 */

public class ScanLoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentIntegrator intentIntegrator = new IntentIntegrator(ScanLoadActivity.this);
        intentIntegrator.setPrompt("扫描二维码");
        intentIntegrator.setCaptureActivity(YarnCaptureActivity.class);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "扫描失败", Toast.LENGTH_LONG).show();
            } else {
                //调用JS回调方法,返回给website扫描结果
                GlobalApplication application = (GlobalApplication) getApplication();
                String callbackAction = this.getString(R.string.yarn_js_callback_qrScanResult);
                EventController.ExecHandler(callbackAction, result.getContents(), application.JavaScrip_Methods);
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
