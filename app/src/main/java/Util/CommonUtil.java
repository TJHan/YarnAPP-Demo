package Util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelUuid;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import Base.BaseActivity;
import Base.BaseApplication;

/**
 * Created by tjhan on 2017-05-23.
 * 通用助手类
 */

public class CommonUtil {

    /**
     * 运行时授权码类
     */
    public class PermissionCode {
        /**
         * 打电话授权码
         */
        public static final int P_CALL_PHONE = 001;

        /**
         * 扫描二维码
         */
        public static final int P_SCAN_CODE = 002;

        /**
         * 拍照
         */
        public static final int P_TAKE_PHOTO = 003;

        public static final int P_LBS_LOCATION = 004;
    }

    /**
     * 调用摄像头拍照片
     *
     * @param activity
     * @param saveUrl
     * @param json
     */
    public void Camera(BaseActivity activity, String saveUrl, String json) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String picName = df.format(new Date()) + ".jpg";
        File outputImage = new File(activity.getExternalCacheDir(), picName);
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(BaseApplication.getContext(), "com.ecotoonyarn.yarn.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //若要获得高质量的图片，需要制定照片的保存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("js_camera_saveurl", saveUrl);
        intent.putExtra("js_camera_json", json);
        activity.startActivityForResult(intent, CommonUtil.PermissionCode.P_TAKE_PHOTO);

    }
}
