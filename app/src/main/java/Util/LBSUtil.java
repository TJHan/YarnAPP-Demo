package Util;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.ecottonyarn.yarn.GlobalApplication;
import com.ecottonyarn.yarn.R;
import com.google.gson.Gson;

import Base.BaseActivity;
import Base.BaseApplication;
import Base.BaseWebView;
import Entity.LBSLocation;
import javascript.JavaScripMethods;

/**
 * Created by tjhan on 2017-05-24.
 * 高德地图工具类
 */

public class LBSUtil {

    private AMapLocationClient aMapLocationClient = null;
    private String callbackAction = null;
    private Context mContext;

    public LBSUtil(Context context) {
        mContext = context;
        callbackAction = mContext.getString(R.string.yarn_js_callback_getLocationResult);

        aMapLocationClient = new AMapLocationClient(mContext);
        AMapLocationClientOption aMapLocationClientOption = getLocationOption();

        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.setLocationListener(locationListener);

    }

    public void startLocation() {
        aMapLocationClient.startLocation();
    }

    public void stopLocation() {
        aMapLocationClient.stopLocation();
    }

    /**
     * 初始化AMapLocationClientOption对象
     *
     * @return
     */
    private AMapLocationClientOption getLocationOption() {
        AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否GPS优先，只在高精度模式下有效
        aMapLocationClientOption.setGpsFirst(false);
        //是否返回逆地理信息
        aMapLocationClientOption.setNeedAddress(true);
        //设置是否单次定位，默认为false
        aMapLocationClientOption.setOnceLocation(true);
        //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        aMapLocationClientOption.setOnceLocationLatest(false);
        //设置定位间隔，默认是2000毫秒
        aMapLocationClientOption.setInterval(2000);
        //设置网络超时
        aMapLocationClientOption.setHttpTimeOut(3000);
        //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        aMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        aMapLocationClientOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        aMapLocationClientOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return aMapLocationClientOption;
    }

    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            LogUtil.d("lbs", "坐标反馈啦");
            if (null != aMapLocation) {
                LogUtil.d("lbs", "aMapLocation 不是null");
                LBSLocation lbsLocation = new LBSLocation();
                if (aMapLocation.getErrorCode() == 0) {
                    lbsLocation.setLongitude(aMapLocation.getLongitude());
                    lbsLocation.setLatitude(aMapLocation.getLatitude());
                    lbsLocation.setCountry(aMapLocation.getCountry());
                    lbsLocation.setProvince(aMapLocation.getProvince());
                    lbsLocation.setCity(aMapLocation.getCity());
                    lbsLocation.setDistrict(aMapLocation.getDistrict());
                    lbsLocation.setAddress(aMapLocation.getAddress());
                }

                //回调js获取当前位置的回调方法
                if (!TextUtils.isEmpty(callbackAction)) {
                    Gson gson = new Gson();
                    String json = gson.toJson(lbsLocation);
                    LogUtil.d("lbs", "坐标: " + json);
                    requestCallBackAction(json);
                }
            }
        }
    };

    private void requestCallBackAction(final String json) {
        if (TextUtils.isEmpty(json))
            return;
        //调用JS回调方法,返回设备位置信息
        GlobalApplication application = (GlobalApplication) mContext.getApplicationContext();
        EventController.ExecHandler(callbackAction, json, application.JavaScrip_Methods);
    }
}
