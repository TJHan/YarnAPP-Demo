package Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javascript.JavaScripMethods;

/**
 * Created by tjhan on 2017-06-07.
 * 事件方法注册类
 */

public class EventController {
    public static Map<String, List<String>> Event_List = new HashMap<>();

    /**
     * 绑定事件方法
     * @param event
     * @param func
     */
    public static void BindFunction(String event,String func)
    {
        String eventName = event.toLowerCase();
        if (!Event_List.containsKey(eventName)) {
            List<String> list = new ArrayList<>();
            list.add(func);
            Event_List.put(eventName, list);
        } else {
            List<String> list = Event_List.get(eventName);
            if (!list.contains(func))
                list.add(func);
        }
    }

    /**
     * 解除事件方法的绑定
     * @param event
     * @param func
     */
    public static void UnBindFunction(String event, String func) {
        String eventName = event.toLowerCase();
        if (Event_List.containsKey(eventName)) {
            List<String> list = Event_List.get(eventName);
            if (list.contains(func))
                list.remove(func);
        }
    }

    /**
     * 执行已绑定的事件方法
     * @param event
     * @param returnResult
     * @param javaScripMethods
     */
    public static void ExecHandler(String event, String returnResult, JavaScripMethods javaScripMethods) {
        String eventName = event.toLowerCase();
        List<String> list = Event_List.get(eventName);
        StringBuffer log = new StringBuffer();
        if (list != null) {
            //遍历所有注册的事件方法并执行回调
            List<String> rList = new ArrayList<>();
            for (String str : list) {
                if (javaScripMethods.invokeJavaScript(str, returnResult)) {
                    rList.add(str);
                }
            }
            //回调成功后从集合中删除该方法
            for (String str : rList) {
                if (list.contains(str))
                    list.remove(str);
            }
        }
    }
}
