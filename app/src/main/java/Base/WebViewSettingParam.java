package Base;

/**
 * Created by tjhan on 2017-05-22.
 */

public class WebViewSettingParam {

    public WebViewSettingParam(Boolean supportZoom, Boolean builtInZoomControls
            , Boolean displayZoomControls, Boolean allowFileAccess
            , Boolean jsCanOpenWindowsAutomatically, String defaultTextEncodingName) {
        this.supportZoom = supportZoom;
        this.builtInZoomControls = builtInZoomControls;
        this.displayZoomControls = displayZoomControls;
        this.allowFileAccess = allowFileAccess;
        this.jsCanOpenWindowsAutomatically = jsCanOpenWindowsAutomatically;
        this.defaultTextEncodingName = defaultTextEncodingName;
    }

    /**
     * 支持缩放，默认为true
     */
    private Boolean supportZoom;
    /***
     * 设置内置的缩放控件，若为false，则该webview不可缩放
     */
    private Boolean builtInZoomControls;

    /**
     * 隐藏原生的缩放控件
     */
    private Boolean displayZoomControls;

    /**
     * 允许访问文件
     */
    private Boolean allowFileAccess;

    /**
     * 支持通过js打开新窗口
     */
    private Boolean jsCanOpenWindowsAutomatically;

    /**
     * 编码格式
     */
    private String defaultTextEncodingName;

    public void setSupportZoom(Boolean supportZoom) {
        this.supportZoom = supportZoom;
    }

    public Boolean getSupportZoom() {
        return this.supportZoom;
    }

    public void setBuiltInZoomControls(Boolean builtInZoomControls) {
        this.builtInZoomControls = builtInZoomControls;
    }

    public Boolean getBuiltInZoomControls() {
        return this.builtInZoomControls;
    }

    public void setDisplayZoomControls(Boolean displayZoomControls) {
        this.displayZoomControls = displayZoomControls;
    }

    public Boolean getDisplayZoomControls() {
        return this.displayZoomControls;
    }

    public void setAllowFileAccess(Boolean allowFileAccess) {
        this.allowFileAccess = allowFileAccess;
    }

    public Boolean getAllowFileAccess() {
        return this.allowFileAccess;
    }

    public void setJsCanOpenWindowsAutomatically(Boolean jsCanOpenWindowsAutomatically) {
        this.jsCanOpenWindowsAutomatically = jsCanOpenWindowsAutomatically;
    }

    public Boolean getJsCanOpenWindowsAutomatically() {
        return this.jsCanOpenWindowsAutomatically;
    }

    public void setDefaultTextEncodingName(String defaultTextEncodingName) {
        this.defaultTextEncodingName = defaultTextEncodingName;
    }

    public String getDefaultTextEncodingName() {
        return this.defaultTextEncodingName;
    }

}
