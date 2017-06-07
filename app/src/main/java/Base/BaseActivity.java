package Base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.ecottonyarn.yarn.R;

import java.util.Iterator;
import java.util.UUID;

import com.ecottonyarn.yarn.GlobalApplication;

import Util.ActivityController;
import Util.CommonUtil;
import Util.LogUtil;
import Util.UploadUtil;

/**
 * Created by tjhan on 2017-05-23.
 */

public class BaseActivity extends AppCompatActivity {

    public String call_Phone = null;
    public String camera_PhotoPath;
    public String camera_SaveUrl;
    public String camera_Json;

    //活动的唯一标识
    public String Activity_UUID;

    public GlobalApplication application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.Activity_UUID = UUID.randomUUID().toString();
        application = (GlobalApplication) getApplication();
        ActivityController.AddActivity(this);
    }

    @Override
    protected void onDestroy() {
        LogUtil.d("PopUpActivity", "销毁这个活动");
        ActivityController.RemoveActivity(this);
        super.onDestroy();
    }



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
            /**
             * 返回启动的新活动的唯一标识
             */
            case CommonUtil.PermissionCode.P_OPEN_ACTIVITY_UUID:
                if (resultCode == RESULT_OK) {
                    ResultActivityUUID();
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

        //获取回调函数名称
        String callbackAction = this.getString(R.string.yarn_js_callback_cameraResult);
        application.Event_Handler.ExecHandler(callbackAction, photoUrl, application.JavaScrip_Methods);
    }

//
//    //添加已启动的活动到活动列表中
//    private void AddActivityList() {
//        if (!TextUtils.isEmpty(this.Activity_UUID) && application.Global_Activity_List != null) {
//            if (!application.Global_Activity_List.containsKey(this.Activity_UUID)) {
//                application.Global_Activity_List.put(this.Activity_UUID, this);
//            }
//        }
//    }
//
//    //从活动列表中删除已关闭的活动
//    public void RemoveFromActivityList() {
//        if (!TextUtils.isEmpty(this.Activity_UUID) && application.Global_Activity_List != null) {
//            if (application.Global_Activity_List.containsKey(this.Activity_UUID)) {
//                Iterator iterator = application.Global_Activity_List.keySet().iterator();
//                while (iterator.hasNext()) {
//                    String key = (String) iterator.next();
//                    if (this.Activity_UUID.equals(key)) {
//                        iterator.remove();
//                        application.Global_Activity_List.remove(key);
//                    }
//                }
//            }
//        }
//    }

    private void ResultActivityUUID() {
        //获取回调函数名称
        String callbackAction = this.getString(R.string.yarn_js_callback_openPageResult);
        application.Event_Handler.ExecHandler(callbackAction, this.Activity_UUID, application.JavaScrip_Methods);
    }
}
