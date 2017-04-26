package com.project.yang.m.main;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.*;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.route.WalkStep;
import com.project.yang.m.R;
import com.project.yang.m.beans.GeoFenceInfo;
import com.project.yang.m.chart.ChartActivity;
import com.project.yang.m.common.App;
import com.project.yang.m.databinding.ActivityMainBinding;
import com.project.yang.m.databinding.DrawerLayoutRecyclerViewItemBinding;
import com.project.yang.m.historyrecord.HistoryRecordActivity;
import com.project.yang.m.other.OtherActivity;
import com.project.yang.m.overlay.WalkRouteOverlay;
import com.project.yang.m.personal.PersonalCenterActivity;
import com.project.yang.m.stores.Pref;
import com.project.yang.m.utils.LogUtil;
import com.project.yang.m.utils.ToastUtil;
import com.project.yang.m.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements GeocodeSearch.OnGeocodeSearchListener, DrawerLayoutRecyclerViewAdapter.OnItemClickListener, AMap.OnMapClickListener, OnCollectDataListener {
    private static final String TAG = "DataCollection";
    private ActivityMainBinding binding = null;
    private ActionBarDrawerToggle toggle = null;
    private DrawerLayoutRecyclerViewAdapter adapter = null;
    private AMap aMap = null;
    private GeocodeSearch geocodeSearch = null;
    private AMapLocationClient mLocationClient = null;
    private MarkerOptions markerOptions = new MarkerOptions();
    private AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            RegeocodeQuery regeocodeQuery = new RegeocodeQuery(new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 5, GeocodeSearch.AMAP);
            geocodeSearch.getFromLocationAsyn(regeocodeQuery);
        }
    };

    //地理围栏
    private Marker marker = null;
    private ProgressDialog progressDialog = null;
    private LatLng startLatLng = null;
    private LatLng endLatLng = null;
    private RouteSearch mRouteSearch = null;
    private Marker startMarker = null;
    private Marker endMarker = null;
    private int flag = 1;
    private Timer mTimer = null;
    private Calendar calendar = Calendar.getInstance();
    private int interval = 5;
    private int hour = 15;
    private int minute = 30;
    private int second = 0;
    private ActivityManager mActivityManager = null;
    private LatLng latLng = null;

    private List<GeoFenceInfo> geoFenceInfo = new ArrayList<>();
    private boolean isInner = false;
    private GeoFenceInfo geoFence = null;
    private String appName = "unusedApp";
    private int locationLabel = -1;
    private boolean isFirstEnter = false;
    private boolean isShowGeo = false;
    private boolean isAuto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "==============================");
        LogUtil.d(TAG, "切换到Activity");
//        Intent intent = new Intent(this, LocationService.class);
//        stopService(intent);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_main, null, false);
        setContentView(this.binding.getRoot());
        this.binding.mapView.onCreate(savedInstanceState);
        if (this.aMap == null) {
            this.aMap = this.binding.mapView.getMap();//获取AMap对象
        }
        this.aMap.setOnMapClickListener(this);
        if (Utils.checkPermission(this)) {
            initLocationOption();
        }
        Log.d(TAG, Utils.sHA1(this));
        initView();
        initRouteSearch();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        this.mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        this.geocodeSearch = new GeocodeSearch(this);
//        this.geocodeSearch.setOnGeocodeSearchListener(this);
    }

    private void initAllGeoFence() {
        for (int i = 0; i < AllGeofence.allGeofence.size(); i++) {
            GeoFenceInfo geofenceInfo = new GeoFenceInfo(aMap.addPolygon(AllGeofence.allGeofence.get(i).strokeWidth(4).strokeColor(Color.argb(50, 1, 1, 1)).fillColor(Color.argb(50, 1, 1, 1))), i);
            this.geoFenceInfo.add(geofenceInfo);
        }
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.056825, 117.143742), 18));
        this.isShowGeo = true;
    }

    @Override
    public void onMapClick(final LatLng latLng) {
        if (marker != null) {
            marker.remove();
        }
        marker = aMap.addMarker(new MarkerOptions().position(latLng));
//        LogUtil.d("onMapClick", String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude));
//        Toast.makeText(MainActivity.this, String.valueOf(latLng.latitude)+","+String.valueOf(latLng.longitude), Toast.LENGTH_SHORT).show();

        //如果显示了地理围栏，则可以采集数据
        if (this.isShowGeo && !this.isAuto) {
            getFakeData(latLng);
        }

//        LogUtil.d("onMapClick",latLng.latitude + "," + latLng.longitude + ","+);
//        setRouteLine(latLng);

//        latLngs.add(latLng);
//        if (this.latLngs.size() >= 2) {
//            drawLine(this.latLngs);
//        }
    }

    /**
     * 自动状态下收集数据
     * @param latLng
     * @param address
     */
    private void judgeIsInsideGeoFence(LatLng latLng, String address) {
        for (GeoFenceInfo geoFenceInfo : this.geoFenceInfo) {
            geoFence = geoFenceInfo;
            if (geoFenceInfo.getPolygon().contains(latLng)) {
                this.isInner = true;
                this.isFirstEnter = true;
                this.locationLabel = geoFenceInfo.getLabel();
                String data = latLng.latitude + "," + latLng.longitude + "," + AllGeofence.location.get(geoFence.getLabel()) + "," + Utils.dateTransformStringDetail(System.currentTimeMillis()) + "," + getUsedApp() + "\n";
                LogUtil.d("onMapClick", data);
                if (!appName.equals(getUsedApp())) {
                    appName = getUsedApp();
                    if (!"unusedApp".equals(appName)) {
                        LogUtil.d("onMapClick", Utils.dateTransformStringDetail(System.currentTimeMillis()) + "," + AllGeofence.location.get(geoFence.getLabel()) + "," + appName + "\n");
                    }
                }
//                    Utils.storeData("lbs.txt", data);
                break;
            }
            this.isInner = false;
        }
        if (!isInner) {
            if (this.isFirstEnter) {
                LogUtil.d("onMapClick", Utils.dateTransformStringDetail(System.currentTimeMillis()) + "," + AllGeofence.location.get(locationLabel) + "," + "unusedApp" + "\n");
                this.isFirstEnter = false;
            }
            String data = latLng.latitude + "," + latLng.longitude + "," + address + "," + Utils.dateTransformStringDetail(System.currentTimeMillis()) + "," + getUsedApp() + "\n";
            LogUtil.d("onMapClick", data);
//            Utils.storeData("lbs.txt", data);
        }
    }

    private void getFakeData(LatLng latLng) {
        this.latLng = latLng;
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(new LatLonPoint(latLng.latitude, latLng.longitude), 5, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);
    }

    private void fakeData(final LatLng latLng, final String address) {
        for (GeoFenceInfo geoFenceInfo : this.geoFenceInfo) {
            geoFence = geoFenceInfo;
            if (geoFenceInfo.getPolygon().contains(latLng)) {
                appName = "unusedApp";
                this.isInner = true;
                this.isFirstEnter = true;
                this.locationLabel = geoFenceInfo.getLabel();
                if (this.mTimer != null) {
                    this.mTimer.cancel();
                }
                this.mTimer = new Timer();
                this.mTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        setCurrentTime();
                        String data = latLng.latitude + "," + latLng.longitude + "," + AllGeofence.location.get(geoFence.getLabel()) + "," + getCurrentTime() + "," + getUsedApp() + "\n";
                        LogUtil.d("onMapClick", data);
                        if (!appName.equals(getUsedApp())) {
                            appName = getUsedApp();
                            if (!appName.equals("unusedApp")) {
                                String dataAnalysis = getCurrentTime() + "," + AllGeofence.location.get(geoFence.getLabel()) + "," + appName + "\n";
                                LogUtil.d("onMapClick", dataAnalysis);
                                Utils.storeData("dataAnalysis.txt", dataAnalysis);
                            }
                        }
                    Utils.storeData("lbs.txt", data);
                    }
                }, 0, 5000);
                break;
            }
            this.isInner = false;
        }
        if (!isInner) {
            if (this.isFirstEnter) {
                String dataAnalysis = getCurrentTime() + "," + AllGeofence.location.get(locationLabel) + "," + "unusedApp" + "\n";
                LogUtil.d("onMapClick", dataAnalysis);
                Utils.storeData("dataAnalysis.txt", dataAnalysis);
                this.isFirstEnter = false;
            }
            if (this.mTimer != null) {
                this.mTimer.cancel();
            }
            setCurrentTime();
            String data = latLng.latitude + "," + latLng.longitude + "," + address + "," + getCurrentTime() + "," + getUsedApp() + "\n";
            LogUtil.d("onMapClick", data);
            Utils.storeData("lbs.txt", data);
        }
    }

    private String getCurrentTime() {
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
    }

    private void setCurrentTime() {
        second += interval;
        if (second == 60) {
            minute++;
            second = 0;
        }
        if (minute == 60) {
            hour++;
            minute = 0;
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
    }

    private String getUsedApp() {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = mActivityManager.getRunningAppProcesses();
        ActivityManager.RunningAppProcessInfo runningAppProcessInfo = runningAppProcessInfos.get(0);
        String name = runningAppProcessInfo.processName;
        if (!"com.miui.home".equals(name) && !"com.project.yang.m".equals(name)) {
            return name;
        } else {
            return "unusedApp";
        }
    }

    private void drawLine(List<LatLng> latLngs) {
        aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));
    }

    private void setRouteLine(LatLng latLng) {
        if (this.startMarker != null && this.endMarker != null) {
            this.startMarker.remove();
            this.endMarker.remove();
        }
        if (this.flag == 1) {
            this.startLatLng = latLng;
            this.startMarker = this.aMap.addMarker(new MarkerOptions().position(latLng));
            this.startMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.start));
            this.flag++;
            return;
        }
        if (this.flag == 2) {
            this.endLatLng = latLng;
            this.endMarker = this.aMap.addMarker(new MarkerOptions().position(latLng));
            this.endMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.end));
            this.flag = 1;
        }
        showProgressDialog();
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(new LatLonPoint(this.startLatLng.latitude, this.startLatLng.longitude), new LatLonPoint(this.endLatLng.latitude, this.endLatLng.longitude));
        RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo);
        this.mRouteSearch.calculateWalkRouteAsyn(query);
        this.startLatLng = null;
        this.endLatLng = null;
    }

    private void initRouteSearch() {
        this.mRouteSearch = new RouteSearch(this);
        this.mRouteSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
                dismissProgressDialog();
                aMap.clear();
                if (i == AMapException.CODE_AMAP_SUCCESS) {
                    ToastUtil.showToast("搜索成功");
                    WalkPath walkPath = walkRouteResult.getPaths().get(0);
                    WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(MainActivity.this, aMap, walkPath, walkRouteResult.getStartPos(), walkRouteResult.getTargetPos());
                    walkRouteOverlay.removeFromMap();
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();
                    for (WalkStep walkStep : walkPath.getSteps()) {
                        for (LatLonPoint latLonPoint : walkStep.getPolyline()) {
                            LogUtil.d("line LatLon:", latLonPoint.getLatitude() + "\t" + latLonPoint.getLongitude());
                        }
                    }
                } else {
                    ToastUtil.showToast("对不起，没有搜索到相关数据！");
                }
            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });
    }

    private void showProgressDialog() {
        if (this.progressDialog == null) {
            this.progressDialog = new ProgressDialog(this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            this.progressDialog.setIndeterminate(false);
            this.progressDialog.setCancelable(true);
            this.progressDialog.setMessage("正在搜索");
            this.progressDialog.show();
        }
    }

    private void dismissProgressDialog() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
    }

    /**
     * 定位到当前位置
     *
     * @param latitude  纬度值
     * @param longitude 经度值
     */
    private void currentLocation(double latitude, double longitude) {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18));
        markerOptions.position(new LatLng(latitude, longitude));
        markerOptions.title("当前位置");
        markerOptions.visible(true);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_location)));
        markerOptions.draggable(true);
        aMap.addMarker(markerOptions);
    }

    /**
     * 初始化DrawerLayout
     */
    private void initDrawerLayout() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        this.toggle = new ActionBarDrawerToggle(this, this.binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.binding.drawerLayout.setDrawerListener(this.toggle);
        this.toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.toggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.my_location:
                initMyLocation();//定位到我的位置
                break;
            case R.id.hand_collect://手动收集数据
                HandCollectDataHintDialog handCollectDataHintDialog = new HandCollectDataHintDialog(this);
                handCollectDataHintDialog.setOnCollectDataListener(this);
                handCollectDataHintDialog.show();
                break;
            case R.id.auto_collect://自动收集数据
                AutoCollectDataHintDialog autoCollectDataHintDialog = new AutoCollectDataHintDialog(this);
                autoCollectDataHintDialog.setOnCollectDataListener(this);
                autoCollectDataHintDialog.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化抽屉布局
     */
    private void initView() {
        initDrawerLayout();
        this.binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<String> titles = new ArrayList<>();
        titles.add("图表分析");
        titles.add("历史记录");
        titles.add("我的");
        titles.add("其他");
        int[] option = new int[]{R.mipmap.icon_chart, R.mipmap.icon_record, R.mipmap.icon_personal, R.mipmap.icon_other};
        this.adapter = new DrawerLayoutRecyclerViewAdapter(this, titles, option);
        this.adapter.setOnItemClickListener(this);
        this.binding.recyclerView.setAdapter(this.adapter);
    }

    @Override
    public void onItemClick(DrawerLayoutRecyclerViewItemBinding itemBinding, int position) {
        switch (position) {
            case 0:
                Intent intentChart = new Intent(this, ChartActivity.class);
                startActivity(intentChart);
                break;
            case 1:
                Intent intentHistoryRecord = new Intent(this, HistoryRecordActivity.class);
                startActivity(intentHistoryRecord);
                break;
            case 2:
                Intent intentPersonal = new Intent(this, PersonalCenterActivity.class);
                startActivity(intentPersonal);
                break;
            case 3:
                Intent intentOther = new Intent(this, OtherActivity.class);
                startActivity(intentOther);
                break;
            default:
                break;
        }
    }

    /**
     * 定位到我的位置
     */
    private void initMyLocation() {
        MyLocationStyle style = new MyLocationStyle();
        style.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
//        style.interval(5000);
        this.aMap.setMyLocationStyle(style);
        this.aMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        this.aMap.setMyLocationEnabled(true);//启动定位
    }

    /**
     * 初始化AMapLocationClient和AMapLocationClientOption
     */
    private void initLocationOption() {
        this.geocodeSearch = new GeocodeSearch(this);
        this.geocodeSearch.setOnGeocodeSearchListener(this);
        this.mLocationClient = new AMapLocationClient(App.getContext());
        this.mLocationClient.setLocationListener(this.mLocationListener);
        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationClientOption.setInterval(10000);//10秒钟定位一次
        locationClientOption.setNeedAddress(true);
        locationClientOption.setHttpTimeOut(20000);

        this.mLocationClient.setLocationOption(locationClientOption);
    }

    /**
     * 反地理编码成功后回调该方法返回编码结果
     *
     * @param regeocodeResult
     * @param i
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
        if (!isAuto) {//手动状态下进行手动的逻辑
            fakeData(this.latLng, regeocodeAddress.getFormatAddress());
        } else {//自动状态下进行自动逻辑
            judgeIsInsideGeoFence(this.latLng, regeocodeAddress.getFormatAddress());
        }
    }

    /**
     * 地理编码成功后回调该方法返回编码结果
     *
     * @param geocodeResult
     * @param i
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    private void getUserName() {
        if (Pref.getString(Pref.Key.User.USER_NAME, null) != null) {
            this.binding.txtNickname.setText("昵称：" + Pref.getString(Pref.Key.User.USER_NAME, ""));
        } else {
            this.binding.txtNickname.setText("未登陆");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.binding.mapView.onResume();
        getUserName();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.binding.mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.binding.mapView.onDestroy();//销毁map
        if (this.mLocationClient != null) {
            this.mLocationClient.onDestroy();//销毁client
        }
        if (this.mTimer != null) {
            this.mTimer.cancel();
        }
//        Intent intent = new Intent(this, LocationService.class);
//        startService(intent);
    }

    /*实现按下back键Activity不会销毁*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }

                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartAutoCollectDataListener() {
        ToastUtil.showToast("开始自动收集数据！");
        this.aMap.clear();
        this.geoFenceInfo.clear();
        initAllGeoFence();
        if (this.mTimer != null) {
            this.mTimer.cancel();
        }
        this.isAuto = true;
        this.mLocationClient.stopLocation();
        this.mLocationClient.startLocation();//开启定位
    }

    @Override
    public void onEndAutoCollectDataListener() {
        ToastUtil.showToast("结束自动收集数据！");
    }

    @Override
    public void onStartHandCollectDataListener() {
        ToastUtil.showToast("开始手动收集数据！");
        this.aMap.clear();
        this.geoFenceInfo.clear();
        initAllGeoFence();
        this.isAuto = false;
        if (this.mTimer != null) {
            this.mTimer.cancel();
        }
        this.mLocationClient.stopLocation();//停止定位
    }

    @Override
    public void onEndHandCollectDataListener() {
        ToastUtil.showToast("结束手动收集数据！");
    }
}
