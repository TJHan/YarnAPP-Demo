package Util;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by tjhan on 2017-05-25.
 * 网络工具类
 */

public class NetUtil {

    /**
     * 判断网络情况
     *
     * @param context
     * @return
     */

    public static boolean IsNetworkAvalible(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == connectivityManager)
            return false;
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        if (networkInfos != null) {
            for (int i = 0; i < networkInfos.length; i++) {
                if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断有效网络的状态
     * @param context
     * @return
     */
    public static boolean NetState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean available=false;
        available = networkInfo.isAvailable();
        return available;
    }

    /**
     * 判断是否是SIM卡网络链接
     *
     * @param context
     * @return
     */
    public static boolean IsMobileNetConnect(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (NetworkInfo.State.CONNECTED == state)
            return true;
        else
            return false;
    }

}
