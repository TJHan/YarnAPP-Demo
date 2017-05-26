package Base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import java.io.Serializable;

import javascript.JavaScripMethods;

/**
 * Created by tjhan on 2017-05-22.
 * WebView控件基类
 */

public class BaseWebView extends WebView implements Serializable {

    private static final String JS_INTERFACE = "androidJS"; // BaseApplication.getContext().getString(R.string.js_interface);
    private int key = 0;

    public BaseWebView(Context context) {
        super(context);
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setWebViewClient(WebViewClient client) {

        super.setWebViewClient(client);
    }

    /**
     * WebView除js之外的其他设置的配置方法,如果不显示调用则使用系统默认设置。
     */
    public void initWebViewSettings(WebViewSettingParam webViewSettingParam) {
        if (webViewSettingParam.getSupportZoom() != null)
            this.getSettings().setSupportZoom(webViewSettingParam.getSupportZoom());

        if (webViewSettingParam.getBuiltInZoomControls() != null)
            this.getSettings().setBuiltInZoomControls(webViewSettingParam.getBuiltInZoomControls());

        if (webViewSettingParam.getDisplayZoomControls() != null)
            this.getSettings().setDisplayZoomControls(webViewSettingParam.getDisplayZoomControls());

        if (webViewSettingParam.getAllowFileAccess() != null)
            this.getSettings().setAllowFileAccess(webViewSettingParam.getAllowFileAccess());

        if (webViewSettingParam.getJsCanOpenWindowsAutomatically() != null)
            this.getSettings().setJavaScriptCanOpenWindowsAutomatically(webViewSettingParam.getJsCanOpenWindowsAutomatically());

        if (webViewSettingParam.getDefaultTextEncodingName() != null)
            this.getSettings().setDefaultTextEncodingName(webViewSettingParam.getDefaultTextEncodingName());
    }

    /**
     * 监听按键
     */
    public void setOnKeyListener(final String tag, final Context context, final boolean isMainActivity) {
        super.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (i == keyEvent.KEYCODE_BACK && canGoBack()) {
                        goBack();
                        return true;
                    } else {
                        //只给主活动注册点击两次退出App功能
                        if (isMainActivity) {
                            Log.d(tag, "onKey: " + key);
                            if (key == 0) {
                                key += 1;
                                Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                                return true;
                            } else {
                                key = 0;
                                return false;
                            }
                        }
                    }
                }
                return false;
            }
        });
    }

    /**
     * 注册android的js接口
     *
     * @param obj 桥梁类对象，该对象提供方法让js调用。
     */
    public void addWebViewJavascriptInterface(JavaScripMethods obj) {
        this.getSettings().setJavaScriptEnabled(true);
        addJavascriptInterface(obj, JS_INTERFACE);
    }

}
