package com.ecottonyarn.yarn;

import android.content.Context;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;

import com.amap.api.location.APSService;

import Util.ActivityController;
import Util.LogUtil;

/**
 * Created by tjhan on 2017-06-06.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private static CrashHandler INSTANCE = new CrashHandler();

    private CrashHandler() {
    }

    public static CrashHandler getInStance() {
        return INSTANCE;
    }

    public void init(Context context) {
        this.mContext = context;
        //获取系统的默认UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (!handleException(throwable) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, throwable);
        } else {
            try {
                //显示Toast的线程
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                LogUtil.e("error", e.toString());
            }
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }


    private boolean handleException(final Throwable ex) {

        if (ex == null)
            return false;

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
//                String error = mContext.getString(R.string.app_error_message);
                Toast.makeText(mContext, "抱歉，程序出错啦", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        //做该做的事儿
        ExecEventHandler(ex);

        return true;
    }

    /**
     * 提交异常信息给注册的事件方法
     *
     * @param ex
     */
    private void ExecEventHandler(Throwable ex) {
        GlobalApplication application = (GlobalApplication) mContext.getApplicationContext();
        String callbackaction = mContext.getString(R.string.yarn_js_callback_shellException);
        application.Event_Handler.ExecHandler(callbackaction, ex.toString(), application.JavaScrip_Methods);
        //清空活动
        ActivityController.ClearAllActivity();
    }
}
