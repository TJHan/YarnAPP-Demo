package Util;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import Base.BaseApplication;

/**
 * Created by tjhan on 2017-05-22.
 */

public class DeviceUtil {

    private static TelephonyManager telephonyManager;

    private static TelephonyManager getTelephonyManager() {
        if (telephonyManager == null) {
            Context context = BaseApplication.getContext();
            telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        }
        return telephonyManager;
    }

    /**
     * 获取设备的本机号码
     * 此方法暂无效
     */
    public static String getDevicePhoneNumber() {
        return getTelephonyManager().getLine1Number();
    }

    /**
     * 获取设备操作系统及版本号
     */
    public static String getDeviceOS() {
        return "Android " + Build.VERSION.RELEASE;
    }

    /**
     * 获取设备串号
     */
    public static String getDeviceImei() {
        return getTelephonyManager().getDeviceId();
    }

    /**
     * 获取设备型号信息
     */
    public static String getDeviceModel() {
        String str = Build.MANUFACTURER + " " + Build.MODEL;
        return str;
    }
}
