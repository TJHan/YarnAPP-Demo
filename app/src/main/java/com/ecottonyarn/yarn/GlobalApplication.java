package com.ecottonyarn.yarn;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

import Base.BaseActivity;
import Util.LogUtil;

/**
 * Created by tjhan on 2017-06-05.
 */

public class GlobalApplication extends Application {

    public Map<String, BaseActivity> Global_Activity_List;

    @Override
    public void onCreate() {
        Global_Activity_List = new HashMap<>();
        LogUtil.d("PopUpActivity", "开始啦");
        super.onCreate();
    }

}