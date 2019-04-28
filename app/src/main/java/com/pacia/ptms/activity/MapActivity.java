package com.pacia.ptms.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;

import butterknife.BindView;

public class MapActivity extends BaseActivity implements SensorEventListener {
    private static final String TAG = "MapActivity";
    @BindView(R.id.mapView)
    MapView mapView;
    private BaiduMap mBaiduMap;
    private MyLocationData locData;
    private LocationClient locationClient;
    private LocationClientOption option;
    private MyLocationConfiguration config;
    private SensorManager mSensorManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    public void initView() {
        getLocation();
    }

    @Override
    public void initData() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
    }

    private void getLocation() {
        mBaiduMap = mapView.getMap();
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(14.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,
                true, null);
        mBaiduMap.setMyLocationConfiguration(config);
        locationClient = new LocationClient(this);
        mBaiduMap.setMyLocationEnabled(true);
        locationClient.registerLocationListener(new MyLocationListener());
        option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        option.setCoorType("bd09ll");
        option.setOpenGps(true);
        locationClient.setLocOption(option);
        locationClient.start();
    }

    private class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            Log.e(TAG, "onReceiveLocation: " + location.getCity());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mapView = null;
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double x = event.values[SensorManager.DATA_X];
//        if (Math.abs(x - lastX) > 1.0) {
//            mCurrentDirection = (int) x;
//            locData = new MyLocationData.Builder()
//                    .accuracy(mCurrentAccracy)
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(mCurrentDirection).latitude(mCurrentLat)
//                    .longitude(mCurrentLon).build();
//            map.setMyLocationData(locData);
//        }
//        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
