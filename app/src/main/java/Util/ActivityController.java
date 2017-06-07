package Util;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Base.BaseActivity;

/**
 * Created by tjhan on 2017-06-07.
 *
 * 活动管理类
 */

public class ActivityController {
    public static Map<String, BaseActivity> Global_Activity_List = new HashMap<>();

    /**
     * 添加活动
     *
     * @param activity
     */
    public static void AddActivity(BaseActivity activity) {
        if (!TextUtils.isEmpty(activity.Activity_UUID) && Global_Activity_List != null) {
            if (!Global_Activity_List.containsKey(activity.Activity_UUID))
                Global_Activity_List.put(activity.Activity_UUID, activity);
        }
    }

    /**
     * 移除活动
     *
     * @param activity
     */
    public static void RemoveActivity(BaseActivity activity) {
        if (!TextUtils.isEmpty(activity.Activity_UUID) && Global_Activity_List != null) {
            if (Global_Activity_List.containsKey(activity.Activity_UUID)) {
                Iterator iterator = Global_Activity_List.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    if (activity.Activity_UUID.equals(key)) {
                        iterator.remove();
                        Global_Activity_List.remove(key);
                    }
                }
            }
        }
    }

    /**
     * 销毁所有已开启的活动
     */
    public static void ClearAllActivity() {
        if (Global_Activity_List != null && Global_Activity_List.size() > 0) {
            for (BaseActivity activity : Global_Activity_List.values()) {
                if (!activity.isFinishing())
                    activity.finish();
            }
        }
    }

    public static BaseActivity GetActivityById(String id)
    {
        if (Global_Activity_List != null && Global_Activity_List.size() > 0) {
            return Global_Activity_List.get(id);
        }
        return null;
    }
}
