package Util;

import android.util.Log;

/**
 * Created by tjhan on 2017-05-22.
 * 自定义日志助手类，用于打印日志
 */

public class LogUtil {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    /**
     * 日志级别，发布生产环境前需要赋值为 NOTHING
     */
    public static int level = VERBOSE;

    public static void v(String tag, String msg) {
        if (level <= VERBOSE)
            Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (level <= DEBUG)
            Log.v(tag, msg);
    }

    public static void i(String tag,String msg)
    {
        if(level<=INFO)
            Log.v(tag,msg);
    }

    public static void w(String tag,String msg)
    {
        if(level<=WARN)
            Log.w(tag,msg);
    }

    public static void e(String tag,String msg)
    {
        if(level<=ERROR)
            Log.e(tag,msg);
    }
}
