package com.ecottonyarn.yarn;

import android.app.Application;

import Base.EventHandler;
import Util.LogUtil;
import javascript.JavaScripMethods;

/**
 * Created by tjhan on 2017-06-05.
 */

public class GlobalApplication extends Application {

    public EventHandler Event_Handler;

    //全局JavaScript处理对象
    public JavaScripMethods JavaScrip_Methods;

    @Override
    public void onCreate() {
        Event_Handler = new EventHandler();
        LogUtil.d("PopUpActivity", "开始啦");
        super.onCreate();

        CrashHandler crashHandler = CrashHandler.getInStance();
        crashHandler.init(getApplicationContext());
    }

}