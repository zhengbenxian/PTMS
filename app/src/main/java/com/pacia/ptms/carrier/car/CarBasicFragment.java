package com.pacia.ptms.carrier.car;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseFragment;
import com.pacia.ptms.adapter.CarBasicAdapter;
import com.pacia.ptms.bean.CarBasicBean;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.utils.http.BaseObserver;
import com.pacia.ptms.utils.http.SchedulersHelper;
import com.pacia.ptms.utils.http.ServiceFactory;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;

/**
 * 车辆基础信息
 */
public class CarBasicFragment extends BaseFragment {

    public static Fragment getInstance(String plateNo1, String plateNo2) {
        CarBasicFragment fragment = new CarBasicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("plateNo1", plateNo1);
        bundle.putString("plateNo2", plateNo2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipLayout;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_empty_data)
    LinearLayout ll_empty_data;
    @BindView(R.id.tv_error)
    TextView tv_error;
    private String plateNo1 = "", plateNo2 = "";
    private CarBasicAdapter adapter;
    private List<CarBasicBean> list_car = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.layout_swip_recycle;
    }

    @Override
    public void initView() {
        ToolUtils.createSwipeLayout(fContext, swipLayout);
        ToolUtils.createRecycleManager(fContext, recyclerView);
        swipLayout.setEnabled(false);
        plateNo1 = getArguments().getString("plateNo1");
        plateNo2 = getArguments().getString("plateNo2");
        adapter = new CarBasicAdapter(fContext, R.layout.item_rv_car_basic, list_car,true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initData() {
        queryCarInfo(plateNo1, plateNo2);
    }

    @Override
    public void lazyLoadData() {

    }

    //查询车辆基本信息
    private void queryCarInfo(String cNo, String cNoT) {
        ServiceFactory.getService(ApiService.class)
                .queryCarInfoByGid(cNo, cNoT)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            list_car = JsonUtils.getCarBasicAptiList(json);
                            adapter.addData(list_car);
                            adapter.notifyDataSetChanged();
                        } else {
                            onFailed("查询为空");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        onFailed(error);
                    }
                });
    }

    private void onFailed(String msg) {
        tv_error.setText(msg);
        adapter.getData().clear();
        adapter.notifyDataSetChanged();
        swipLayout.setVisibility(View.GONE);
    }
}
