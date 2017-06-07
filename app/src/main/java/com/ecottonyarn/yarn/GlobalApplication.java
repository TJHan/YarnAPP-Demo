package com.ecottonyarn.yarn;

import android.app.Application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Base.BaseActivity;
import Base.BaseWebView;
import Base.EventHandler;
import Util.LogUtil;
import javascript.JavaScripMethods;

/**
 * Created by tjhan on 2017-06-05.
 */

public class GlobalApplication extends Application {

    //全局已开启的活动集合
    public Map<String, BaseActivity> Global_Activity_List;
    public EventHandler Event_Handler;

    //全局JavaScript处理对象
    public JavaScripMethods JavaScrip_Methods;

    @Override
    public void onCreate() {
        Global_Activity_List = new HashMap<>();
        Event_Handler = new EventHandler();
        LogUtil.d("PopUpActivity", "开始啦");
        super.onCreate();

        CrashHandler crashHandler = CrashHandler.getInStance();
        crashHandler.init(getApplicationContext());
    }

    /**
     * 终止所有已开启的活动
     */
    public void ClearActivityList() {
        if (Global_Activity_List != null && Global_Activity_List.size() > 0) {
            for (BaseActivity activity : Global_Activity_List.values()) {
                activity.finish();
            }
        }
    }


}