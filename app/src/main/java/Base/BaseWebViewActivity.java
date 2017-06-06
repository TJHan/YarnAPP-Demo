package Base;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.ecottonyarn.yarn.R;

import java.util.ArrayList;
import java.util.List;

import Util.CommonUtil;
import javascript.JavaScripMethods;

/**
 * Created by tjhan on 2017-05-22.
 * WebView活动类的基类
 */

public class BaseWebViewActivity extends BaseActivity {

    /**
     * 当前活动的webView控件对象
     */
    public BaseWebView mWebView;
    public String mCallbackaction;
    //进度条
    private ProgressBar mProgressBar;
    private int currentProgress;
    private boolean isAnimStart = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * webview类型的活动初始化时校验运行时授权
         */
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, CommonUtil.PermissionCode.P_LBS_LOCATION);
        }

    }

    /**
     * 活动类基类初始化方法
     *
     * @param webView 当前活动的WebView对象
     */
    public void initComponent(final BaseWebView webView, ProgressBar progressBar) {
        mWebView = webView;
        mCallbackaction = getString(R.string.yarn_js_callback_navigationEvent);
        this.mProgressBar = progressBar;
        //初始化APP的全局js操作对象
        application.JavaScrip_Methods = new JavaScripMethods(mWebView, this);
        mWebView.addWebViewJavascriptInterface(application.JavaScrip_Methods);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (mProgressBar != null) {
                    currentProgress = mProgressBar.getProgress();
                    if (newProgress >= 100 && !isAnimStart) {
                        //加载完毕
                        isAnimStart = true;
                        mProgressBar.setProgress(newProgress);
                        startDismissAnimation(mProgressBar.getProgress());
                    } else {
                        //加载中...
                        startProgressAnimation(newProgress);
                    }
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.clearCache(true);
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            //WebView销毁方式在Android level 21及以上版本与低版本有所不同
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((ViewGroup) mWebView.getParent()).removeView(mWebView);
                mWebView.destroy();
            } else {
                mWebView.destroy();
                ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            }
            mWebView = null;
        }
        super.onDestroy();
    }

    /**
     * progressBar递增动画
     */
    private void startProgressAnimation(int newProgress) {
        ObjectAnimator animator = ObjectAnimator.ofInt(mProgressBar, "progress", currentProgress, newProgress);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }


    /**
     * progressBar消失动画
     */
    private void startDismissAnimation(final int progress) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(mProgressBar, "alpha", 1.0f, 0.0f);
        anim.setDuration(1500);  // 动画时长
        anim.setInterpolator(new DecelerateInterpolator());     // 减速
        // 关键, 添加动画进度监听器
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();      // 0.0f ~ 1.0f
                int offset = 100 - progress;
                mProgressBar.setProgress((int) (progress + offset * fraction));
            }
        });

        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束
                mProgressBar.setProgress(0);
                mProgressBar.setVisibility(View.GONE);
                isAnimStart = false;
            }
        });
        anim.start();
    }
}
