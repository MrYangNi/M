package com.project.yang.m.main;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.*;
import com.project.yang.m.R;
import com.project.yang.m.common.App;
import com.project.yang.m.utils.LogUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 定位Service
 * @Author: NiYang
 * @Date: 2017/3/26.
 */
public class LocationService extends Service implements GeocodeSearch.OnGeocodeSearchListener {
    private static final String TAG = "DataCollection";
    private GeocodeSearch geocodeSearch = null;
    private AMapLocationClient mLocationClient = null;
    private ActivityManager mActivityManager = null;
    private Timer mTimer = new Timer();
    private AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Log.d(TAG, "" + aMapLocation.getLatitude() + "\t" + aMapLocation.getLongitude());
            RegeocodeQuery regeocodeQuery = new RegeocodeQuery(new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 5, GeocodeSearch.AMAP);
            geocodeSearch.getFromLocationAsyn(regeocodeQuery);
        }
    };
    private String processName = "";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG,"=================================");
        LogUtil.d(TAG, "切换到Service");
        initOption();
        startForegroundService();
        this.mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    }

    /**
     * 初始化AMapLocationClient并开启定位
     */
    private void initOption() {
        this.geocodeSearch = new GeocodeSearch(this);
        this.geocodeSearch.setOnGeocodeSearchListener(this);
        this.mLocationClient = new AMapLocationClient(App.getContext());
        this.mLocationClient.setLocationListener(this.mLocationListener);
        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationClientOption.setInterval(5000);
        locationClientOption.setNeedAddress(true);
        locationClientOption.setHttpTimeOut(20000);
        this.mLocationClient.setLocationOption(locationClientOption);
        this.mLocationClient.startLocation();//开启定位
    }

    /**
     * 开启前台Service
     */
    private void startForegroundService() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Miracle")
                .setContentText("This is content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1, notification);
    }

    private void getRecentProcess() {
        this.mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
//                Log.d(TAG, "+++++++++++++++++++++++++++++++++++++");
                List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = mActivityManager.getRunningAppProcesses();
                ActivityManager.RunningAppProcessInfo runningAppProcessInfo = runningAppProcessInfos.get(0);
                String name = runningAppProcessInfo.processName;
                if (!"com.miui.home".equals(name) && !"com.project.yang.m".equals(name) && !processName.equals(name)) {
                    processName = runningAppProcessInfo.processName;//缓存在本地
                    Log.d(TAG, runningAppProcessInfo.processName);
                }
            }
        }, 0, 5000);
    }

    private void getUsedApp() {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = mActivityManager.getRunningAppProcesses();
        ActivityManager.RunningAppProcessInfo runningAppProcessInfo = runningAppProcessInfos.get(0);
        String name = runningAppProcessInfo.processName;
        if (!"com.miui.home".equals(name) && !"com.project.yang.m".equals(name) && !processName.equals(name)) {
            processName = runningAppProcessInfo.processName;//缓存在本地
            Log.d(TAG, runningAppProcessInfo.processName);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mTimer.cancel();
        this.mLocationClient.stopLocation();
        this.mLocationClient.onDestroy();
        LogUtil.d(TAG, "onDestroy");
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
        Log.d(TAG, regeocodeAddress.getFormatAddress());
        getUsedApp();
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
