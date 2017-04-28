package com.project.yang.m.historyrecord;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.TileOverlayOptions;
import com.project.yang.m.R;
import com.project.yang.m.databinding.ActivityHistoryRecordBinding;
import com.project.yang.m.databinding.PopupWindowMoreViewBinding;
import com.project.yang.m.db.DBManager;
import com.project.yang.m.db.beans.LBSData;
import com.project.yang.m.db.beans.RecordTime;
import com.project.yang.m.utils.LogUtil;
import com.project.yang.m.utils.ToastUtil;
import com.project.yang.m.utils.Utils;
import com.yang.m.greendao.gen.LBSDataDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.amap.api.maps.model.HeatmapTileProvider.DEFAULT_GRADIENT;

/**
 * Created by NiYang on 2017/4/26.
 */

public class HistoryRecordActivity extends AppCompatActivity implements View.OnClickListener, OnSelectedHistoryTimeListener {
    private static final String TAG = "HistoryRecordActivity";
    private ActivityHistoryRecordBinding historyRecordBinding = null;
    private AMap aMap = null;
    private int flag = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.historyRecordBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_history_record, null, false);
        setContentView(this.historyRecordBinding.getRoot());
        initMapView(savedInstanceState);
        initView();
    }

    private void initMapView(Bundle bundle) {
        this.historyRecordBinding.historyMapView.onCreate(bundle);
        if (aMap == null) {
            this.aMap = this.historyRecordBinding.historyMapView.getMap();
        }
    }

    private void initView() {
        this.historyRecordBinding.ivBack.setOnClickListener(this);
        this.historyRecordBinding.ivMore.setOnClickListener(this);
    }

    private void showPopupWindow() {
        PopupWindowMoreViewBinding moreViewBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.popup_window_more_view, null, false);
        PopupWindow popupWindow = new PopupWindow(moreViewBinding.getRoot(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(this.historyRecordBinding.ivMore);
        moreViewBinding.txtSelectHistoryTrace.setOnClickListener(this);
        moreViewBinding.txtHotspot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_more:
                showPopupWindow();
                break;
            case R.id.txt_select_history_trace:
                this.flag = 0;//历史路径
                showBottomHistoryRecordPopupWindow();
                break;
            case R.id.txt_hotspot:
                this.flag = 1;//热点图
                showBottomHistoryRecordPopupWindow();
                break;
            default:
                break;
        }
    }

    private void showBottomHistoryRecordPopupWindow() {
        List<RecordTime> dataTime = DBManager.getDbManager().getDaoSession().getRecordTimeDao().queryBuilder().list();
        if (dataTime == null || dataTime.size() == 0) {
            ToastUtil.showToast("您还没有历史数据！");
            return;
        }
        List<String> startTimeData = new ArrayList<>();
        List<String> endTimeData = new ArrayList<>();
        for (RecordTime recordTime : dataTime) {
            startTimeData.add(Utils.dateTransformStringDetail(recordTime.getStartTime()));
            endTimeData.add(Utils.dateTransformStringDetail(recordTime.getEndTime()));
        }
        HistoryRecordTimePopupWindow tracePopupWindow = new HistoryRecordTimePopupWindow(this, startTimeData, endTimeData);
        tracePopupWindow.setOnSelectedHistoryTimeListener(this);
        tracePopupWindow.showAtLocation(this.historyRecordBinding.historyRecordMainContainer, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.historyRecordBinding.historyMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.historyRecordBinding.historyMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.historyRecordBinding.historyMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.historyRecordBinding.historyMapView.onSaveInstanceState(outState);
    }

    /**
     * 定位到我的位置
     */
    private void initMyLocation() {
        MyLocationStyle style = new MyLocationStyle();
        style.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        this.aMap.setMyLocationStyle(style);
        this.aMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        this.aMap.setMyLocationEnabled(true);//启动定位
    }

    private void drawRouteLine(List<LBSData> datas) {
        List<LatLng> latLngs = new ArrayList<>();
        for (LBSData data : datas) {
            latLngs.add(new LatLng(data.getLatitude(), data.getLongitude()));
        }
        this.aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(0), 18));
    }

    private void drawHotSpot(List<LBSData> datas) {
        List<LatLng> latLngs = new ArrayList<>();
        for (LBSData data : datas) {
            latLngs.add(new LatLng(data.getLatitude(), data.getLongitude()));
        }
        // 构建热力图 HeatmapTileProvider
        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.data(latLngs) // 设置热力图绘制的数据
                .gradient(DEFAULT_GRADIENT); // 设置热力图渐变，有默认值 DEFAULT_GRADIENT，可不设置该接口
        // 构造热力图对象
        HeatmapTileProvider heatmapTileProvider = builder.build();
        // 初始化 TileOverlayOptions
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.tileProvider(heatmapTileProvider); // 设置瓦片图层的提供者
        // 向地图上添加 TileOverlayOptions 类对象
        aMap.addTileOverlay(tileOverlayOptions);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(0), 18));
    }

    @Override
    public void onSelectedHistoryTime(Long startTime, Long endTime) {
        ToastUtil.showToast(startTime + "," + endTime);
        QueryBuilder qb = DBManager.getDbManager().getDaoSession().getLBSDataDao().queryBuilder().where(LBSDataDao.Properties.Time.between(startTime, endTime));
        List<LBSData> list = qb.list();
        for (LBSData data : list) {
            LogUtil.d(TAG, data.getId() + "," + data.getLatitude() + "," + data.getLongitude() + "," + data.getAppName() + "," + data.getAddress() + "," + data.getTime());
        }
        if (this.flag == 0) {
            drawRouteLine(list);
        }
        if (this.flag == 1) {
            drawHotSpot(list);
        }
    }
}
