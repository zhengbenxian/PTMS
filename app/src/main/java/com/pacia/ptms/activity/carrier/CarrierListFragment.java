package com.pacia.ptms.activity.carrier;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseFragment;
import com.pacia.ptms.bean.CarrierBean;
import com.pacia.ptms.carrier.info.CarrierInfoActivity;
import com.pacia.ptms.utils.ToolUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 承运商列表
 */
public class CarrierListFragment extends BaseFragment implements CarrierListInterface.View,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemChildClickListener {
    private static final String TAG = "CarrierListFragment";

    public static Fragment getInstance(String msg) {
        CarrierListFragment fragment = new CarrierListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", msg);
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
    private CarrierListActivity activity;
    private CarrierListPresenter presenter;
    //类型   搜索字符
    private String type = "", search_str = "";
    //当前页  行数  总页数
    private int page = 1, rows = 10, pageCount = 1;
    private boolean isRefresh = false;
    private List<CarrierBean> list_carrier = new ArrayList<>();
    private CarrierListAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.layout_swip_recycle;
    }

    @Override
    public void initView() {
        ToolUtils.createSwipeLayout(fContext, swipLayout);
        ToolUtils.createRecycleManager(fContext, recyclerView);
        activity = (CarrierListActivity) getActivity();
        presenter = new CarrierListPresenter(this, this);
        type = getArguments().getString("key");
    }

    @Override
    public void initData() {
        adapter = new CarrierListAdapter(fContext, R.layout.item_rv_carrier_list, list_carrier);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
//        adapter.setOnItemChildClickListener(this);
        swipLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                presenter.getCarrierList(type, page, rows);
            }
        });
    }

    @Override
    public void lazyLoadData() {
        isRefresh = true;
        page = 1;
        presenter.getCarrierList(type, page, rows);
        activity.setOnEditorActionListener(new CarrierListActivity.onEditorActionListener() {
            @Override
            public void onEditAction(String search) {
                search_str = search;
                Log.e(TAG, "onEditAction: " + type + "  " + search_str);
            }
        });
    }

    @Override
    public void showCarrierList(List<CarrierBean> list, int total) {
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

    @Override
    public void onLoadMoreRequested() {
        swipLayout.setRefreshing(false);
        if (page < pageCount) {
            page++;
            presenter.getCarrierList(type, page, rows);
        } else {
            adapter.setEnableLoadMore(false);
            adapter.loadMoreComplete();
            View view = getLayoutInflater().inflate(R.layout.foot_view, null);
            adapter.addFooterView(view);
        }
        isRefresh = false;
    }

    @Override
    public void onFailed(String msg) {
        adapter.getData().clear();
        adapter.notifyDataSetChanged();
        swipLayout.setVisibility(View.GONE);
        swipLayout.setRefreshing(false);
        tv_error.setText(msg);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CarrierBean cb = (CarrierBean) adapter.getItem(position);
        Intent intent = new Intent(fContext, CarrierInfoActivity.class);
        intent.putExtra("gid", cb.getCysGid());
        intent.putExtra("name", cb.getCysName());
        startActivity(intent);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//        CarrierBean cb = (CarrierBean) adapter.getItem(position);
//        ToolUtils.openPhone(getActivity(), cb.getCysContractTel());
    }
}
