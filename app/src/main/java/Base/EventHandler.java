package Base;

import com.ecottonyarn.yarn.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.LogUtil;
import javascript.JavaScripMethods;

/**
 * Created by tjhan on 2017-06-05.
 */

public class EventHandler implements IEventHandler {

    private Map<String, List<String>> event_List;

    private BaseWebView mBaseWebView;

    public EventHandler() {
        this.event_List = new HashMap<>();
    }

    /**
     * 绑定事件方法
     *
     * @param event
     * @param func
     */
    @Override
    public void BindFunction(String event, String func) {
        String eventName = event.toLowerCase();
        if (!event_List.containsKey(eventName)) {
            List<String> list = new ArrayList<>();
            list.add(func);
            event_List.put(eventName, list);
        } else {
            List<String> list = event_List.get(eventName);
            if (!list.contains(func))
                list.add(func);
        }
    }

    /**
     * 解除事件方法的绑定
     *
     * @param event
     * @param func
     */
    @Override
    public void UnBindFunction(String event, String func) {
        String eventName = event.toLowerCase();
        if (event_List.containsKey(eventName)) {
            List<String> list = event_List.get(eventName);
            if (list.contains(func))
                list.remove(func);
        }
    }

    /**
     * 执行已绑定的事件方法
     *
     * @param event
     */
    @Override
    public void ExecHandler(String event, String returnResult, JavaScripMethods javaScripMethods) {
        String eventName = event.toLowerCase();
        List<String> list = event_List.get(eventName);
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
