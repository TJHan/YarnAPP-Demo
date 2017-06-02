package Base;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017-05-30.
 */

public class BaseWebViewClient extends WebViewClient {


    private ProgressBar baseProgressBar;

    public BaseWebViewClient(ProgressBar progressBar) {
        this.baseProgressBar = progressBar;
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        view.loadUrl("about:blank");
        view.loadUrl("file:///android_asset/NETWORKERROR.html");
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        view.loadUrl("about:blank");
        view.loadUrl("file:///android_asset/NETWORKERROR.html");
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        view.loadUrl("about:blank");
        view.loadUrl("file:///android_asset/NETWORKERROR.html");
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (baseProgressBar != null) {
            baseProgressBar.setVisibility(View.VISIBLE);
            baseProgressBar.setAlpha(1.0f);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }
}
