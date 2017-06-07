package com.ecottonyarn.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ecottonyarn.yarn.GlobalApplication;
import com.ecottonyarn.yarn.R;

import Util.EventController;

/**
 * 网络链接广播接收器
 */
public class NetStateBroadcastReceiver extends BroadcastReceiver {

    /**
     * 设备网络使用状态更改广播接收处理
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //网络是否连接状态结果，true 或 false
        boolean result = (networkInfo != null && networkInfo.isAvailable());

        GlobalApplication application = (GlobalApplication) context.getApplicationContext();
        String callbackAction = context.getString(R.string.yarn_js_callback_netStateChanged);
        EventController.ExecHandler(callbackAction, String.valueOf(result), application.JavaScrip_Methods);
    }
}
