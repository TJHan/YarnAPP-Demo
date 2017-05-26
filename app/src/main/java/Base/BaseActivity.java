package Base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.ecottonyarn.yarn.R;

import Util.CommonUtil;
import Util.UploadUtil;
import javascript.JavaScripMethods;

/**
 * Created by tjhan on 2017-05-23.
 */

public class BaseActivity extends AppCompatActivity {

    public String call_Phone = null;
    public String camera_PhotoPath;
    public String camera_SaveUrl;
    public String camera_Json;



    /**
     * APP活动运行时授权回调统一管理方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            /**
             * 打电话授权结果
             */
            case CommonUtil.PermissionCode.P_CALL_PHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestPermissionCallPhone();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 活动返回结果统一管理方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            /**
             * 拍照返回结果
             */
            case CommonUtil.PermissionCode.P_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    activityResultPostPhoto(data);
                }
                break;
        }
    }

    /**
     * 运行时授权回调方法中调用的打电话方法
     */
    private void requestPermissionCallPhone() {
        if (TextUtils.isEmpty(call_Phone))
            return;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + call_Phone));
        startActivity(intent);
    }


    /**
     * 获取摄像头拍摄的照片并上传
     *
     * @param data
     */
    private void activityResultPostPhoto(Intent data) {
        if (TextUtils.isEmpty(camera_PhotoPath) || TextUtils.isEmpty(camera_Json) || TextUtils.isEmpty(camera_SaveUrl))
            return;
//        Bitmap bitmap = BitmapUtil.getSmallBitmap(camera_PhotoPath);
//        String base64 = BitmapToBase64Util.bitmapToBase64(bitmap);
        //拍照成功上传照片操作
        UploadUtil uploadUtil = UploadUtil.getInstance();
        String photoUrl = uploadUtil.UploadFile(camera_SaveUrl, camera_Json, camera_PhotoPath);

        //照片上传成功，调用拍照上传的回调函数
        BaseWebView mWebView = new BaseWebView(getApplicationContext());
        if (mWebView != null) {
            JavaScripMethods javaScripMethods = new JavaScripMethods(mWebView, null);
            //获取回调函数名称
            String callbackAction = this.getString(R.string.yarn_js_callback_cameraResult);
            //调用回调函数
            javaScripMethods.invokeJavaScript(callbackAction, photoUrl);
            mWebView.destroy();
            mWebView = null;
        }
    }


}
