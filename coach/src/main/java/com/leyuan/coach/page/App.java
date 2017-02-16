package com.leyuan.coach.page;

import android.app.Application;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.leyuan.coach.bean.UserCoach;
import com.leyuan.coach.config.Constant;
import com.leyuan.commonlibrary.manager.VersionManager;
import com.leyuan.coach.utils.LogUtil;
import com.leyuan.coach.utils.SharePrefUtils;

import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class App extends Application {

    private static App mInstance;
    public static Context context;
    private String jpushId;
    private UserCoach user;
    private String token;
    private String versionName;

    public static double lat;
    public static double lon;
    public static String city = "上海";
    public static String addressStr;


    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    public final static List<BaseActivity> mActivities = new LinkedList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();
        initConfig();
    }

    private void initConfig() {
        SDKInitializer.initialize(this);
        Fresco.initialize(this);
        initBaiduLoc();
        Stetho.initializeWithDefaults(this);
        // You can enable debug mode in developing state. You should close debug mode when release.
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }


    private void initBaiduLoc() {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        initLocation();
        mLocationClient.start();
    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 5 * 60 * 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设隔需要大于等于1000ms才是置发起定位请求的间有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        //        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        //        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        //option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            App.lat = location.getLatitude();
            App.lon = location.getLongitude();
            if (location.getCity() != null)
                city = location.getCity().replace("市", "");
            if (location.getAddrStr() != null)
                addressStr = location.getAddrStr();
            if (city != null) {
                mLocationClient.stop();
            }
            LogUtil.i("lat = " + lat + ",   lon = " + lon);
        }
    }

    public boolean isLogin() {
        if (getUser() == null) {
            return false;
        }
        return true;
    }

    public void exitLogin() {
        setUser(null);
    }

    public UserCoach getUser() {
        if (user == null) {
            user = SharePrefUtils.getUser(this);
        }
        return user;

    }

    public void setUser(UserCoach user) {
        this.user = user;
        if (user != null && user.getToken() != null) {
            setToken(user.getToken());
        }
        SharePrefUtils.setUser(context, user);
    }

    public String getToken() {

        if (token == null) {
            token = SharePrefUtils.getString(context, "token", null);
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        SharePrefUtils.putString(context, "token", token);
    }

    public static App getInstance() {
        return mInstance;
    }


    public void setJPushID(String regId) {
        jpushId = regId;
        SharePrefUtils.putString(context, Constant.JPUSH_ID, regId);
    }

    public String getJPushId() {
        if (jpushId == null) {
            jpushId = SharePrefUtils.getString(context, Constant.JPUSH_ID, "");
        }
        return jpushId;
    }

    public String getVersionName() {

        if (versionName == null) {
            versionName = VersionManager.getVersionName(context);
        }

        return versionName;
    }

    public static List<BaseActivity> getmActivities() {
        return mActivities;
    }
}
