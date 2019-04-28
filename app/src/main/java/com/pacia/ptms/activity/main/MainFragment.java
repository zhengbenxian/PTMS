package com.pacia.ptms.activity.main;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pacia.ptms.R;
import com.pacia.ptms.activity.WebActivity;
import com.pacia.ptms.activity.base.BaseFragment;
import com.pacia.ptms.activity.carrier.CarrierListActivity;
import com.pacia.ptms.activity.statistics.StatisticsActivity;
import com.pacia.ptms.activity.transport.TransSuperActivity;
import com.pacia.ptms.activity.viola.ViolaReguActivity;
import com.pacia.ptms.adapter.BannerImageLoader;
import com.pacia.ptms.adapter.GridViewAdapter;
import com.pacia.ptms.adapter.NewsAdapter;
import com.pacia.ptms.bean.CommonBean;
import com.pacia.ptms.bean.NewsBean;
import com.pacia.ptms.carrier.info.CarrierInfoActivity;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.SPUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.widget.MyGridView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Context.LOCATION_SERVICE;

/**
 * 首页
 */
public class MainFragment extends BaseFragment implements MainInterface.View,
        BaseQuickAdapter.OnItemClickListener {
    private static final String TAG = "MainFragment";

    public static Fragment getInstance(String str) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", str);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.tv_main_location)
    TextView tv_main_location;
    @BindView(R.id.tv_main_search)
    TextView tv_main_search;
    @BindView(R.id.tv_main_msg)
    TextView tv_main_msg;
    @BindView(R.id.layout_main_msg)
    RelativeLayout layout_msg;
    @BindView(R.id.banner_carousel)
    Banner banner_carousel;
    @BindView(R.id.linear_top_main)
    LinearLayout linear_top_main;
    @BindView(R.id.grid_main)
    MyGridView grid_main;
    @BindView(R.id.tv_main_news)
    TextView tv_main_news;
    @BindView(R.id.img_main_pic)
    ImageView img_main_pic;
    @BindView(R.id.img_msg)
    ImageView img_msg;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    private GridViewAdapter adapter_main;
    private NewsAdapter adapter_news;
    private MainPresenter presenter;
    private LocationManager locationManager;
    private Location location;
    private String provider = LocationManager.NETWORK_PROVIDER, city = "";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView() {
        linear_top_main.setPadding(0, ToolUtils.getStatusBarHeight(fContext), 0, 0);
        ToolUtils.createRecycleManager(fContext, recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        presenter = new MainPresenter(this, this);
        presenter.getBanner();
        presenter.getGridData();
        presenter.getNewsData();
    }

    @Override
    public void initData() {
        locationForGPS();
    }

    @Override
    public void lazyLoadData() {

    }

    @OnClick({R.id.tv_main_search, R.id.tv_main_news, R.id.layout_main_msg})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_main_search:
                break;
            case R.id.tv_main_news:
                break;
            case R.id.layout_main_msg:
                break;
        }
    }

    @Override
    public void showBanner(List<String> title, List<Integer> paths) {
        banner_carousel.setImageLoader(new BannerImageLoader())
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImages(paths)
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();
    }

    @Override
    public void showGrid(List<CommonBean> list) {
        adapter_main = new GridViewAdapter(fContext, list, R.layout.item_gv_main, 1);
        grid_main.setAdapter(adapter_main);
        grid_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onGvItemClick(position);
            }
        });
    }

    @Override
    public void showNews(List<NewsBean> list_car) {
        adapter_news = new NewsAdapter(R.layout.item_rv_news, list_car);
        recyclerView.setAdapter(adapter_news);
        adapter_news.notifyDataSetChanged();
        adapter_news.setOnItemClickListener(this);
    }

    //gridView 点击事件
    private void onGvItemClick(int position) {
        switch (position) {
            case 0:
                if (SPUtils.getRoleType(fContext).equals(Constant.ROLE_CARRIER)) {
                    Intent intent = new Intent(fContext, CarrierInfoActivity.class);
                    intent.putExtra("gid", SPUtils.getUserGid(fContext));
                    intent.putExtra("name", SPUtils.getUserName(fContext));
                    startActivity(intent);
                } else {
                    doActivity(CarrierListActivity.class);
                }
                break;
            case 1:
                doActivity(TransSuperActivity.class);
                break;
            case 2:
                doActivity(StatisticsActivity.class);
                break;
            case 3:
                doActivity(ViolaReguActivity.class);
                break;
            case 4:
                Log.e(TAG, "onGvItemClick: " + position);
                break;
            case 5:
                Log.e(TAG, "onGvItemClick: " + position);
                break;
            case 6:
                Log.e(TAG, "onGvItemClick: " + position);
                break;
            case 7:
                Log.e(TAG, "onGvItemClick: " + position);
                break;
        }
    }

    @SuppressLint("MissingPermission")
    private void locationForGPS() {
        locationManager = (LocationManager) fContext.getSystemService(LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(provider);
        if (null != location) {
            city = ToolUtils.getAddress(fContext, location);
            tv_main_location.setText(city);
            return;
        } else {
            Log.e(TAG, "locationForGPS:  else  net");
            provider = LocationManager.GPS_PROVIDER;
            location = locationManager.getLastKnownLocation(provider);
            if (null != location) {
                Log.e(TAG, "netLoaction: gps");
            } else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        10 * 1000, 0, listener);
            }
        }
        locationManager.requestLocationUpdates(provider, 10 * 1000,
                0, listener);
    }

    private LocationListener listener = new LocationListener() {
        @SuppressLint("MissingPermission")
        @Override
        public void onLocationChanged(Location location) {
            if (null != location) {

            } else {
                Log.e(TAG, "onLocationChanged: dd");
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.e(TAG, "onStatusChanged: ");

        }

        @Override
        public void onProviderEnabled(String s) {
            Log.e(TAG, "onProviderEnabled: " + s);
        }

        @Override
        public void onProviderDisabled(String s) {
            Log.e(TAG, "onProviderDisabled: " + s);
        }
    };

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        NewsBean nb = (NewsBean) adapter.getItem(position);
        Intent intent = new Intent(fContext, WebActivity.class);
        intent.putExtra("bean", nb);
        startActivity(intent);
    }
}
