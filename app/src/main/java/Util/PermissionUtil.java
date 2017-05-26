package Util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import Base.BaseApplication;

/**
 * Created by tjhan on 2017-05-24.
 */

public class PermissionUtil {
    private static List<String> permissionList = new ArrayList<>();

    /**
     * 注册需要申请的授权
     *
     * @param permissionCode
     */
    public void AddRequestPermission(final String permissionCode) {
        if(ContextCompat.checkSelfPermission(BaseApplication.getContext(),permissionCode) != PackageManager.PERMISSION_GRANTED)
        {
            if (!permissionList.contains(permissionCode))
                permissionList.add(permissionCode);
        }
    }

    /**
     * 申请授权方法
     * @param activity
     * @param requestCode
     */
    public void RequestPermissions(Activity activity, int requestCode) {
        String[] permissions = permissionList.toArray(new String[permissionList.size()]);
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }


}
