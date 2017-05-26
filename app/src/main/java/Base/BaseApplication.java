package Base;

import android.app.Application;
import android.content.Context;

/**
 * Created by tjhan on 2017-05-22.
 * 用于获取全局上下文对象
 */

public class BaseApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }

    /**
     * 获取全局上下文
     * */
    public static Context getContext()
    {
        return context;
    }
}
