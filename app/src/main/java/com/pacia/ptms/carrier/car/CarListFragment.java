package com.pacia.ptms.carrier.car;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseFragment;
import com.pacia.ptms.adapter.CarListAdapter;
import com.pacia.ptms.bean.CarListBean;
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
 * 承运商车辆信息列表
 */
public class CarListFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    public static Fragment getInstance(String msg) {
        CarListFragment fragment = new CarListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("msg", msg);
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
    @BindView(R.id.tv_car_number)
    TextView tv_car_number;
    private CarListAdapter adapter;
    private List<CarListBean> list_car = new ArrayList<>();
    //总页数
    private int pageCount = 1;
    //当前页
    private int page = 1;
    private int rows = 10;
    private boolean isRefresh = false;
    private String carrierGid = "";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_car_list;
    }

    @Override
    public void initView() {
        ToolUtils.createSwipeLayout(fContext, swipLayout);
        ToolUtils.createRecycleManager(fContext, recyclerView);
        carrierGid = getArguments().getString("msg");

        adapter = new CarListAdapter(R.layout.item_rv_car_list, list_car);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(this);

        swipLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                queryCarList(carrierGid, page, rows);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void lazyLoadData() {
        isRefresh = true;
        page = 1;
        queryCarList(carrierGid, page, rows);
    }

    @Override
    public void onLoadMoreRequested() {
        swipLayout.setRefreshing(false);
        if (page < pageCount) {
            page++;
            queryCarList(carrierGid, page, rows);
        } else {
            adapter.setEnableLoadMore(false);
            adapter.loadMoreComplete();
            View view = getLayoutInflater().inflate(R.layout.foot_view, null);
            adapter.addFooterView(view);
        }
        isRefresh = false;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CarListBean carBean = (CarListBean) adapter.getItem(position);
        Intent intent = new Intent(fContext, CarInfoActivity.class);
        intent.putExtra("bean", carBean);
        startActivity(intent);
    }

    //查询车辆列表
    private void queryCarList(String gid, int page, int rows) {
        ServiceFactory.getService(ApiService.class)
                .queryCarListByCarr(gid, page, rows)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            List<CarListBean> list_car = JsonUtils.getCarList(json);
                            int total = JsonUtils.getTotal(json);
                            tv_car_number.setText("车辆数 " + total);
                            showCarList(list_car, total);
                        } else {
                            onFailed("没有查询到数据");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        onFailed(error);
                    }
                });
    }

    private void showCarList(List<CarListBean> list, int total) {
        tv_car_number.setVisibility(View.VISIBLE);
        swipLayout.setVisibility(View.VISIBLE);
        adapter.removeAllFooterView();
        //下拉加载更多
        adapter.setOnLoadMoreListener(this, recyclerView);
        pageCount = (total + rows - 1) / rows;
        if (isRefresh) {
            adapter.getData().clear();
        }
        adapter.addData(list);
        adapter.notifyDataSetChanged();
        adapter.loadMoreComplete();
        swipLayout.setRefreshing(false);
    }

    private void onFailed(String msg) {
        tv_car_number.setVisibility(View.GONE);
        tv_error.setText(msg);
        adapter.getData().clear();
        adapter.notifyDataSetChanged();
        swipLayout.setVisibility(View.GONE);
    }
}
