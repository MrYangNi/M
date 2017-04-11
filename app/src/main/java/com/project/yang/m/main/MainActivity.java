package com.project.yang.m.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
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
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.*;
import com.project.yang.m.R;
import com.project.yang.m.chart.ChartActivity;
import com.project.yang.m.common.MApplication;
import com.project.yang.m.databinding.ActivityMainBinding;
import com.project.yang.m.databinding.DrawerLayoutRecyclerViewItemBinding;
import com.project.yang.m.other.OtherActivity;
import com.project.yang.m.personal.PersonalCenterActivity;
import com.project.yang.m.setting.SettingActivity;
import com.project.yang.m.utils.LogUtil;
import com.project.yang.m.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GeocodeSearch.OnGeocodeSearchListener, DrawerLayoutRecyclerViewAdapter.OnItemClickListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG,"==============================");
        LogUtil.d(TAG, "切换到Activity");
        Intent intent = new Intent(this, LocationService.class);
        stopService(intent);
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_main, null, false);
        setContentView(this.binding.getRoot());
        setUserName();
        this.binding.mapView.onCreate(savedInstanceState);
        if (this.aMap == null) {
            this.aMap = this.binding.mapView.getMap();//获取AMap对象
        }
        if (Utils.checkPermission(this)) {
//            initLocationOption();
            initLocation();
        }
        Log.d(TAG, Utils.sHA1(this));
        initView();
    }

    private void setUserName() {
        Bundle bundle = getIntent().getExtras();
        this.binding.txtNickname.setText("昵称："+bundle.getString("username"));
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

    private void initView() {
        initDrawerLayout();
        this.binding.recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        List<String> titles = new ArrayList<>();
        titles.add("图表分析");
        titles.add("我的");
        titles.add("其他");
        titles.add("设置");
        int[] option = new int[]{R.mipmap.icon_chart,R.mipmap.icon_personal,R.mipmap.icon_other,R.mipmap.icon_setting};
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
            case 3:
                Intent intentSetting = new Intent(this, SettingActivity.class);
                startActivity(intentSetting);
                break;
            default:break;
        }
    }

    private void startActivity(Class classO) {

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
        this.mLocationClient = new AMapLocationClient(MApplication.getContext());
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

    @Override
    protected void onResume() {
        super.onResume();
        this.binding.mapView.onResume();

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
