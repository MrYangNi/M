package com.project.yang.m.main;

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
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.*;
import com.project.yang.m.R;
import com.project.yang.m.chart.ChartActivity;
import com.project.yang.m.common.App;
import com.project.yang.m.databinding.ActivityMainBinding;
import com.project.yang.m.databinding.DrawerLayoutRecyclerViewItemBinding;
import com.project.yang.m.other.OtherActivity;
import com.project.yang.m.personal.PersonalCenterActivity;
import com.project.yang.m.stores.Pref;
import com.project.yang.m.utils.LogUtil;
import com.project.yang.m.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GeocodeSearch.OnGeocodeSearchListener, DrawerLayoutRecyclerViewAdapter.OnItemClickListener, AMap.OnMapClickListener {
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
            RegeocodeQuery regeocodeQuery = new RegeocodeQuery(new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 5, GeocodeSearch.AMAP);
            geocodeSearch.getFromLocationAsyn(regeocodeQuery);
        }
    };

    //地理围栏
    private Polygon polygon = null;
    private Polygon polygon1 = null;
    private Marker marker = null;
    private int count = 0;

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
//            initLocation();
        }
        Log.d(TAG, Utils.sHA1(this));
        initView();
        setGeo();
        setGeo1();
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

    List<LatLng> latLngs = new ArrayList<>();
    @Override
    public void onMapClick(LatLng latLng) {
        if (marker != null) {
            marker.remove();
        }
        marker = aMap.addMarker(new MarkerOptions().position(latLng));
//        count++;
//        if (count <= 5) {
//            latLngs.add(latLng);
//        }
//        if (count == 5) {
//            setGeo(latLngs);
//            count = 0;
//        }
//        boolean b1 = polygon.contains(latLng);
        LogUtil.d("onMapClick", String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude));
        Toast.makeText(MainActivity.this, String.valueOf(latLng.latitude)+","+String.valueOf(latLng.longitude), Toast.LENGTH_SHORT).show();
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

    private void initLocation() {
        MyLocationStyle style = new MyLocationStyle();
        style.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        style.interval(5000);
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
}
