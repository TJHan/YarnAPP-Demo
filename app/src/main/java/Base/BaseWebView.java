package Base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ecottonyarn.yarn.GlobalApplication;
import com.ecottonyarn.yarn.R;

import java.io.Serializable;

import Util.NetUtil;
import javascript.JavaScripMethods;

/**
 * Created by tjhan on 2017-05-22.
 * WebView控件基类
 */

public class BaseWebView extends WebView {

    private static final String JS_INTERFACE = "androidJS"; // BaseApplication.getContext().getString(R.string.js_interface);
    private int key = 0;
    private GlobalApplication mApplication;
    private String mCallbackaction;

    public BaseWebView(Context context) {
        super(context);
        initCompent(context);
    }

    public BaseWebView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initCompent(context);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCompent(context);
    }

    public void setBaseWebViewClient(BaseWebViewClient client) {

        super.setWebViewClient(client);
    }

    private void initCompent(Context context) {
        mApplication = (GlobalApplication) context.getApplicationContext();
        mCallbackaction = mApplication.getString(R.string.yarn_js_callback_deviceKey);
    }

    /**
     * WebView除js之外的其他设置的配置方法,如果不显示调用则使用系统默认设置。
     */
    public void initWebViewSettings(WebViewSettingParam webViewSettingParam) {
        //设置webview是否支持使用屏幕上的错放控件和手势进行缩放，默认为true。
        //设置setBuiltInZoomControls(boolean)可以使用特殊的缩放机制。该项设置不会影响zoomIn() and zoomOut()的缩放操作。
        if (webViewSettingParam.getSupportZoom() != null)
            this.getSettings().setSupportZoom(webViewSettingParam.getSupportZoom());

        //是否使用内置的缩放机制，内置的缩放机制包括屏幕上的缩放控件（浮于WebView内容之上）和缩放手势的运用。
        // 通过setDisplayZoomControls(boolean)可以控制是否显示这些控件，默认值为false。
        if (webViewSettingParam.getBuiltInZoomControls() != null)
            this.getSettings().setBuiltInZoomControls(webViewSettingParam.getBuiltInZoomControls());

        //使用内置的缩放机制时是否展示缩放控件，默认值true。参见setBuiltInZoomControls(boolean).
        if (webViewSettingParam.getDisplayZoomControls() != null)
            this.getSettings().setDisplayZoomControls(webViewSettingParam.getDisplayZoomControls());

        //是否允许访问文件，默认允许。注意，这里只是允许或禁止对文件系统的访问，
        // Assets 和 resources 文件使用file:///android_asset和file:///android_res仍是可访问的。
        if (webViewSettingParam.getAllowFileAccess() != null)
            this.getSettings().setAllowFileAccess(webViewSettingParam.getAllowFileAccess());

        //让JavaScript自动打开窗口，默认false。适用于JavaScript方法window.open()。
        if (webViewSettingParam.getJsCanOpenWindowsAutomatically() != null)
            this.getSettings().setJavaScriptCanOpenWindowsAutomatically(webViewSettingParam.getJsCanOpenWindowsAutomatically());

        //设置默认的字符编码集，默认”UTF-8”.
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
                    if (i == keyEvent.KEYCODE_MENU)
                        ExecJSEventHandler();
                    if (i == keyEvent.KEYCODE_BACK) {
                        if (canGoBack()) {
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

    @Override
    public void loadUrl(String url) {
        //如果断网状态下尝试链接服务器URL将跳转error页面
        boolean c = url.startsWith("http", 0);
        if (c) {
            boolean result = NetUtil.IsNetworkAvalible(this.getContext());
            if (!result)
                url = "file:///android_asset/NETWORKERROR.html";
        }
        super.loadUrl(url);
    }

    private void ExecJSEventHandler() {
        mApplication.Event_Handler.ExecHandler(mCallbackaction, "Menu", mApplication.JavaScrip_Methods);
    }
}
