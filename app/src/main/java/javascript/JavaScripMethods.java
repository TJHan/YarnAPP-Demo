package javascript;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;

import com.ecottonyarn.FeatureActivity.ScanLoadActivity;
import com.ecottonyarn.yarn.BrowserActivity;
import com.ecottonyarn.yarn.PopUpActivity;
import com.ecottonyarn.yarn.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import Base.BaseActivity;
import Base.BaseApplication;
import Base.BaseWebView;
import DB.SharedPreferencesContext;
import Util.ActivityController;
import Util.CommonUtil;
import Util.DeviceUtil;
import Util.EventController;
import Util.LBSUtil;
import Util.LogUtil;
import Util.NetUtil;

/**
 * Created by tjhan on 2017-05-22.
 * Android原生API开放JS接口类
 * 是否需要考虑WebViewJSBridge
 * https://github.com/firewolf-ljw/WebViewJSBridge
 */

public class JavaScripMethods {

    private BaseWebView mWebView;
    private BaseActivity mActivity;
    private String mTAG;


    public JavaScripMethods(BaseWebView webView, BaseActivity activity) {
        this.mActivity = activity;
        this.mWebView = webView;
    }

    /**
     * Android调用无返回值的website的js方法
     *
     * @param jsFunctionName
     * @param params
     */
    public boolean invokeJavaScript(final String jsFunctionName, final String params) {
        if (TextUtils.isEmpty(jsFunctionName))
            return false;

        if (!NetUtil.IsNetworkAvalible(this.mActivity)) {
            return false;
        }
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    //android 19版本之后提供能够获取js方法返回值的js调用函数。
                    mWebView.evaluateJavascript("javascript:" + jsFunctionName + "('" + params + "')", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {

                        }
                    });
                } else {
                    mWebView.loadUrl("javascript:" + jsFunctionName + "('" + params + "')");
                }
            }
        });
        return true;
    }

    /**
     * 获取APP版本号
     *
     * @return
     */
    @JavascriptInterface
    public String getVer() {
        LogUtil.d(mTAG, "获取APP版本号");
        String version = null;
        try {
            PackageManager manager = mActivity.getPackageManager();
            version = manager.getPackageInfo(mActivity.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取App名称
     *
     * @return
     */
    @JavascriptInterface
    public String getName() {
        LogUtil.d(mTAG, "获取App名称");
        String appName = mActivity.getString(R.string.app_name);
        return appName;
    }

    /**
     * 获取设备类型
     *
     * @return
     */
    @JavascriptInterface
    public String getType() {
        LogUtil.d(mTAG, "获取设备类型");
        //是否需要判断平板或手机
        return "Android Phone";
    }

    /**
     * 拨打电话
     *
     * @param number
     */
    @JavascriptInterface
    public void callPhone(String number) {
        LogUtil.d(mTAG, "拨打电话:" + number);
        if (TextUtils.isEmpty(number))
            return;
        //android 6.0及以上版本运行时授权
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mActivity.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                mActivity.call_Phone = number;
                mActivity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CommonUtil.PermissionCode.P_CALL_PHONE);
            }
        }
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        mActivity.startActivity(intent);
    }

    /**
     * 当前webview导航
     *
     * @param url
     */
    @JavascriptInterface
    public void navigation(final String url) {
        if (TextUtils.isEmpty(url))
            return;
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(url);
            }
        });
    }

    /**
     * 重新加载页面
     */
    @JavascriptInterface
    public void reload() {
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                mWebView.reload();
            }
        });
    }

    /**
     * 打开页面
     * 回调函数返回打开页面的唯一标识
     *
     * @param url
     * @param browser
     */
    @JavascriptInterface
    public void openPage(String url, boolean browser) {
        if (browser) {
            //浏览页
            Intent intent = new Intent(mActivity, BrowserActivity.class);
            intent.putExtra("url", url);
            mActivity.startActivityForResult(intent, CommonUtil.PermissionCode.P_OPEN_ACTIVITY_UUID);
        } else {
            //弹出页
            Intent intent = new Intent(mActivity, PopUpActivity.class);
            intent.putExtra("url", url);
            mActivity.startActivityForResult(intent, CommonUtil.PermissionCode.P_OPEN_ACTIVITY_UUID);
        }
    }

    /**
     * 显示页面
     *
     * @param id
     * @return
     */
    @JavascriptInterface
    public boolean showPage(String id) {
        BaseActivity activity = ActivityController.GetActivityById(id);
        if (activity != null) {
            mActivity.startActivity(new Intent(mActivity, activity.getClass()));
            return true;
        } else {
            return false;
        }
    }

    /**
     * 隐藏当前页，当前页只能是弹出页，浏览页，否则不予响应
     */
    @JavascriptInterface
    public void hidePage() {
        if (mActivity instanceof BrowserActivity || mActivity instanceof PopUpActivity) {
//            View view = ((ViewGroup) mActivity.findViewById(android.R.id.content)).getChildAt(0);

        }
    }

    /**
     * 关闭当前弹出页，当前页只能是弹出页，浏览页，否则不予响应
     */
    @JavascriptInterface
    public void closePage() {
        if (mActivity instanceof BrowserActivity || mActivity instanceof PopUpActivity) {
            //删除已启动活动列表中的当前活动
            ActivityController.RemoveActivity(mActivity);
            mActivity.finish();
        }
    }

    /**
     * 检查网络状态
     * 若没联网，怎么返回数据给H5 server？
     *
     * @return
     */
    @JavascriptInterface
    public boolean checkNetState() {
        boolean result = NetUtil.IsNetworkAvalible(mActivity);
        return result;
    }

    /**
     * 扫描二维码
     *
     * @return
     */
    @JavascriptInterface
    public void qrScan() {
        Intent intent = new Intent(mActivity, ScanLoadActivity.class);
        mActivity.startActivityForResult(intent, CommonUtil.PermissionCode.P_SCAN_CODE);
    }

    /**
     * 拍照并远程存储
     *
     * @param saveUrl
     * @param json
     * @return
     */
    @JavascriptInterface
    public void camera(String saveUrl, String json) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String picName = df.format(new Date()) + ".jpg";
        File outputImage = new File(mActivity.getExternalCacheDir(), picName);
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(BaseApplication.getContext(), "com.ecotoonyarn.yarn.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //若要获得高质量的图片，需要制定照片的保存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        mActivity.camera_PhotoPath = imageUri.getPath();
        mActivity.camera_Json = json;
        mActivity.camera_SaveUrl = saveUrl;
        mActivity.startActivityForResult(intent, CommonUtil.PermissionCode.P_TAKE_PHOTO);
    }

    /**
     * 启动app升级
     *
     * @param url
     */
    @JavascriptInterface
    public void upgrade(String url) {
        if (null == mActivity || TextUtils.isEmpty(url))
            return;
        DownloadManager manager = (DownloadManager) mActivity.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //设置允许MediaScanner扫描到此更新文件
        request.allowScanningByMediaScanner();
        request.setTitle("锦桥易纱APP"); //设置通知栏标题
        request.setDescription("yes it is");//设置通知栏介绍
        //设置下载完成后显示通知栏，默认只在下载过程中显示通知栏，下载完成不显示。
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI); //设置允许下载的网络类型
//        request.setAllowedOverRoaming(true); //移动网络下是否允许漫游.

        //设置下载文件的mimeType，因为下载管理Ui中点击某个已下载完成文件及下载完成点击通知栏提示都会根据mimeType去打开文件，
        // 所以我们可以利用这个属性。比如上面设置了mimeType为application/cn.trinea.download.file，
        // 我们可以同时设置某个Activity的intent-filter为application/cn.trinea.download.file，用于响应点击的打开文件。
        request.setMimeType("application/cn.trinea.download.file");

//        request.addRequestHeader("",""); //添加请求头信息
//        request.setDestinationInExternalPublicDir("upgrade","yarn.apk"); //设置下载地址为SD卡的upgrade文件夹，文件名为yarn.apk.

        //开始下载，并返回唯一的downloadId.
        long downloadId = manager.enqueue(request);
    }

    /**
     * 返回当前位置信息
     *
     * @return
     */
    @JavascriptInterface
    public void getLocation() {
        LBSUtil lbsUtil = new LBSUtil(mActivity);
        lbsUtil.startLocation();
    }

    /**
     * 返回设备信息
     *
     * @return
     */
    @JavascriptInterface
    public String getDeviceInfo() {
        String json = "{" +
                "tel:'" + DeviceUtil.getDevicePhoneNumber() + "'" +
                ",os:'" + DeviceUtil.getDeviceOS() + "'" +
                ",imei:'" + DeviceUtil.getDeviceImei() + "'" +
                ",device:'" + DeviceUtil.getDeviceModel() + "'}";
        return json;
    }

    /**
     * 本地存储
     *
     * @param json
     * @return
     */
    @JavascriptInterface
    public boolean save(String key, String json) {
        SharedPreferencesContext dbContext = new SharedPreferencesContext(mActivity, "demoDB");
        return dbContext.Save(key, json);
    }

    /**
     * 本地读取
     *
     * @return
     */
    @JavascriptInterface
    public String read(String key) {
        SharedPreferencesContext dbContext = new SharedPreferencesContext(mActivity, "demoDB");
        return dbContext.Read(key);
    }

    @JavascriptInterface
    public void bind(String event, String func) {
        EventController.BindFunction(event, func);
    }

    @JavascriptInterface
    public void unbind(String event, String func) {
        EventController.UnBindFunction(event, func);
    }

}
