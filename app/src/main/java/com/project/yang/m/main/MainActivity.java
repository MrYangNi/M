package com.project.yang.m.main;

import android.app.ProgressDialog;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;
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
import com.project.yang.m.chart.ChartActivity;
import com.project.yang.m.common.App;
import com.project.yang.m.databinding.ActivityMainBinding;
import com.project.yang.m.databinding.DrawerLayoutRecyclerViewItemBinding;
import com.project.yang.m.other.OtherActivity;
import com.project.yang.m.overlay.WalkRouteOverlay;
import com.project.yang.m.personal.PersonalCenterActivity;
import com.project.yang.m.stores.Pref;
import com.project.yang.m.utils.LogUtil;
import com.project.yang.m.utils.ToastUtil;
import com.project.yang.m.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GeocodeSearch.OnGeocodeSearchListener, DrawerLayoutRecyclerViewAdapter.OnItemClickListener, AMap.OnMapClickListener, View.OnClickListener {
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
            Log.d(TAG, "" + aMapLocation.getLatitude() + "\t" + aMapLocation.getLongitude());
//            RegeocodeQuery regeocodeQuery = new RegeocodeQuery(new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 5, GeocodeSearch.AMAP);
//            geocodeSearch.getFromLocationAsyn(regeocodeQuery);

        }
    };

    //地理围栏
    private Polygon polygon = null;
    private Polygon polygon1 = null;
    private Marker marker = null;
    private List<LatLng> latLngs = new ArrayList<>();

    private ProgressDialog progressDialog = null;
    private LatLng startLatLng = null;
    private LatLng endLatLng = null;
    private RouteSearch mRouteSearch = null;
    private Marker startMarker = null;
    private Marker endMarker = null;
    private int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG,"==============================");
        LogUtil.d(TAG, "切换到Activity");
        Intent intent = new Intent(this, LocationService.class);
        stopService(intent);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_main, null, false);
        setContentView(this.binding.getRoot());
        this.binding.mapView.onCreate(savedInstanceState);
        if (this.aMap == null) {
            this.aMap = this.binding.mapView.getMap();//获取AMap对象
        }
        this.aMap.setOnMapClickListener(this);
        if (Utils.checkPermission(this)) {
//            initLocationOption();
//            initMyLocation();
        }
        Log.d(TAG, Utils.sHA1(this));
        initView();
        setGeo();
        setGeo1();
        initRouteSearch();

        this.binding.btnDrawLine.setOnClickListener(this);
    }

    private void setGeo() {
        // 绘制一个长方形
        PolygonOptions pOption = new PolygonOptions();
        pOption.add(new LatLng(39.056825, 117.143742));
        pOption.add(new LatLng(39.056853, 117.144429));
        pOption.add(new LatLng(39.05631,117.14439));
        pOption.add(new LatLng(39.056192,117.144034));
        pOption.add(new LatLng(39.056292,117.14369));
        polygon = aMap.addPolygon(pOption.strokeWidth(4)
                .strokeColor(Color.argb(50, 1, 1, 1))
                .fillColor(Color.argb(50, 1, 1, 1)));
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.057316,117.143659), 18));
    }

    private void setGeo1() {
        PolygonOptions pOption = new PolygonOptions();
        pOption.add(new LatLng(39.059223,117.14382));
        pOption.add(new LatLng(39.059065,117.143514));
        pOption.add(new LatLng(39.059407,117.143501));
//        pOption.add(new LatLng(39.059383,117.143487));
//        pOption.add(new LatLng(39.05941,117.143489));
        polygon1 = aMap.addPolygon(pOption.strokeWidth(4)
                .strokeColor(Color.argb(50, 1, 1, 1))
                .fillColor(Color.argb(50, 1, 1, 1)));
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.059223,117.14382), 18));
    }

    @Override
    public void onMapClick(LatLng latLng) {
//        if (marker != null) {
//            marker.remove();
//        }
//        marker = aMap.addMarker(new MarkerOptions().position(latLng));
//        LogUtil.d("onMapClick", String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude));
//        Toast.makeText(MainActivity.this, String.valueOf(latLng.latitude)+","+String.valueOf(latLng.longitude), Toast.LENGTH_SHORT).show();

//        setRouteLine(latLng);

        latLngs.add(latLng);
        if (this.latLngs.size() >= 2) {
            drawLine(this.latLngs);
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
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(new LatLonPoint(this.startLatLng.latitude,this.startLatLng.longitude), new LatLonPoint(this.endLatLng.latitude,this.endLatLng.longitude));
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
                            LogUtil.d("line LatLon:",latLonPoint.getLatitude()+"\t"+latLonPoint.getLongitude());
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
     * @param latitude 纬度值
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
            case R.id.add_Geo_Fencing:
                break;
            case R.id.my_location:
                initMyLocation();
                break;
            case R.id.history_data:
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化抽屉布局
     */
    private void initView() {
        initDrawerLayout();
        this.binding.recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        List<String> titles = new ArrayList<>();
        titles.add("图表分析");
        titles.add("我的");
        titles.add("其他");
        int[] option = new int[]{R.mipmap.icon_chart,R.mipmap.icon_personal,R.mipmap.icon_other};
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
                Intent intentPersonal = new Intent(this, PersonalCenterActivity.class);
                startActivity(intentPersonal);
                break;
            case 2:
                Intent intentOther = new Intent(this, OtherActivity.class);
                startActivity(intentOther);
                break;
            default:break;
        }
    }

    /**
     * 定位一次
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
        locationClientOption.setInterval(5000);
        locationClientOption.setNeedAddress(true);
        locationClientOption.setHttpTimeOut(20000);

        this.mLocationClient.setLocationOption(locationClientOption);
        this.mLocationClient.startLocation();//开启定位
    }

    /**
     * 反地理编码成功后回调该方法返回编码结果
     * @param regeocodeResult
     * @param i
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
        Log.d(TAG, regeocodeAddress.getFormatAddress());
    }

    /**
     * 地理编码成功后回调该方法返回编码结果
     * @param geocodeResult
     * @param i
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    private void getUserName() {
        if (Pref.getString(Pref.Key.User.USER_NAME, null) != null) {
            this.binding.txtNickname.setText("昵称："+Pref.getString(Pref.Key.User.USER_NAME, ""));
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
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_draw_line:
                break;
            default:break;
        }
    }
}
