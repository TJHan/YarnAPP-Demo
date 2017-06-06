package Base;

import javascript.JavaScripMethods;

/**
 * Created by tjhan on 2017-06-05.
 */

public interface IEventHandler {

    void BindFunction(String event, String func);

    void UnBindFunction(String event, String func);

    void ExecHandler(String event, String returnResult, JavaScripMethods javaScripMethods);


}
