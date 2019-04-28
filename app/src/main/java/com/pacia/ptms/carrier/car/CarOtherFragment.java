package com.pacia.ptms.carrier.car;


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
import com.pacia.ptms.activity.transport.TransInfoActivity;
import com.pacia.ptms.activity.viola.ViolaReguInfoActivity;
import com.pacia.ptms.adapter.MonitorAdapter;
import com.pacia.ptms.adapter.RecordAdapter;
import com.pacia.ptms.adapter.SecurityAdapter;
import com.pacia.ptms.bean.CarListBean;
import com.pacia.ptms.bean.MonitorBean;
import com.pacia.ptms.bean.RecordBean;
import com.pacia.ptms.bean.SecurityBean;
import com.pacia.ptms.utils.ToolUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 车辆详情的车载监控  运行记录 安全违规  列表
 */
public class CarOtherFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener,
        CarOtherInterface.View, BaseQuickAdapter.RequestLoadMoreListener {
    private static final String TAG = "CarOtherFragment";

    public static Fragment getInstance(String type, CarListBean cb) {
        CarOtherFragment fragment = new CarOtherFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putSerializable("bean", cb);
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
    private String type = "", plate1 = "", plate2 = "";
    //总页数
    private int pageCount = 1;
    //当前页
    private int page = 1;
    private int rows = 10;
    private boolean isRefresh = false;
    private List<MonitorBean> list_monitor = new ArrayList<>();
    private List<RecordBean> list_record = new ArrayList<>();
    private List<SecurityBean> list_security = new ArrayList<>();
    private MonitorAdapter adapter_monitor;
    private RecordAdapter adapter_record;
    private SecurityAdapter adapter_security;
    private CarListBean carBean = new CarListBean();
    private CarOtherPresenter presenter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_car_other;
    }

    @Override
    public void initView() {
        ToolUtils.createSwipeLayout(fContext, swipLayout);
        ToolUtils.createRecycleManager(fContext, recyclerView);
        presenter = new CarOtherPresenter(this, this);
        type = getArguments().getString("type");
        carBean = (CarListBean) getArguments().getSerializable("bean");
        plate1 = carBean.getPlateNo();
        plate2 = carBean.getPlateNo2();

        swipLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefresh = true;
                if ("monitor".equals(type)) {
                    presenter.getMonitorList(plate1, plate2);
                } else if ("record".equals(type)) {
                    presenter.getRecordList(plate1, plate2, page, rows);
                } else if ("security".equals(type)) {
                    presenter.getSecurityList(plate1, plate2, page, rows);
                }
            }
        });
    }

    @Override
    public void initData() {
        if ("monitor".equals(type)) {
            adapter_monitor = new MonitorAdapter(fContext, "", R.layout.item_rv_car_monitor, list_monitor);
            recyclerView.setAdapter(adapter_monitor);
            adapter_monitor.notifyDataSetChanged();
            adapter_monitor.setOnItemClickListener(this);
        } else if ("record".equals(type)) {
            adapter_record = new RecordAdapter(R.layout.item_rv_car_record, list_record);
            recyclerView.setAdapter(adapter_record);
            adapter_record.notifyDataSetChanged();
            adapter_record.setOnItemClickListener(this);
        } else if ("security".equals(type)) {
            adapter_security = new SecurityAdapter(R.layout.item_rv_car_record, list_security);
            recyclerView.setAdapter(adapter_security);
            adapter_security.notifyDataSetChanged();
            adapter_security.setOnItemClickListener(this);
        }
    }

    @Override
    public void lazyLoadData() {
        isRefresh = true;
        page = 1;
        if ("monitor".equals(type)) {
            presenter.getMonitorList(plate1, plate2);
        } else if ("record".equals(type)) {
            presenter.getRecordList(plate1, plate2, page, rows);
        } else if ("security".equals(type)) {
            presenter.getSecurityList(plate1, plate2, page, rows);
        }
    }

    @Override
    public void showMonitorList(List<MonitorBean> list) {
        swipLayout.setVisibility(View.VISIBLE);
        pageCount = list.size();
        adapter_monitor.removeAllFooterView();
        if (isRefresh) {
            adapter_monitor.getData().clear();
        }
        adapter_monitor.addData(list);
        adapter_monitor.notifyDataSetChanged();
        adapter_monitor.loadMoreComplete();
        swipLayout.setRefreshing(false);
    }

    @Override
    public void showRecordList(List<RecordBean> list, int total) {
        swipLayout.setVisibility(View.VISIBLE);
        adapter_record.removeAllFooterView();
        //下拉加载更多
        adapter_record.setOnLoadMoreListener(this, recyclerView);
        pageCount = (total + rows - 1) / rows;
        if (isRefresh) {
            adapter_record.getData().clear();
        }
        adapter_record.addData(list);
        adapter_record.notifyDataSetChanged();
        adapter_record.loadMoreComplete();
        swipLayout.setRefreshing(false);
    }

    @Override
    public void showSecurityList(List<SecurityBean> list, int total) {
        swipLayout.setVisibility(View.VISIBLE);
        adapter_security.removeAllFooterView();
        //下拉加载更多
        adapter_security.setOnLoadMoreListener(this, recyclerView);
        pageCount = (total + rows - 1) / rows;
        if (isRefresh) {
            adapter_security.getData().clear();
        }
        adapter_security.addData(list);
        adapter_security.notifyDataSetChanged();
        adapter_security.loadMoreComplete();
        swipLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if ("monitor".equals(type)) {
            MonitorBean mb = (MonitorBean) adapter.getItem(position);
        } else if ("record".equals(type)) {
            RecordBean rb = (RecordBean) adapter.getItem(position);
            doActivity(TransInfoActivity.class,rb.getDyHeaderGid());
        } else if ("security".equals(type)) {
            SecurityBean sb = (SecurityBean) adapter.getItem(position);
            doActivity(ViolaReguInfoActivity.class, sb.getWfgzGid(),
                    sb.getWfgzPlateNo());
        }
    }

    @Override
    public void onFailed(String msg) {
        tv_error.setText(msg);
        swipLayout.setVisibility(View.GONE);
    }

    @Override
    public void onLoadMoreRequested() {
        swipLayout.setRefreshing(false);
        if (page < pageCount) {
            page++;
            if ("monitor".equals(type)) {
                presenter.getMonitorList(plate1, plate2);
            } else if ("record".equals(type)) {
                presenter.getRecordList(plate1, plate2, page, rows);
            } else if ("security".equals(type)) {
                presenter.getSecurityList(plate1, plate2, page, rows);
            }
        } else {
            View view = getLayoutInflater().inflate(R.layout.foot_view, null);
            if ("monitor".equals(type)) {
                adapter_monitor.setEnableLoadMore(false);
                adapter_monitor.loadMoreComplete();
                adapter_monitor.addFooterView(view);
            } else if ("record".equals(type)) {
                adapter_record.setEnableLoadMore(false);
                adapter_record.loadMoreComplete();
                adapter_record.addFooterView(view);
            } else if ("security".equals(type)) {
                adapter_security.setEnableLoadMore(false);
                adapter_security.loadMoreComplete();
                adapter_security.addFooterView(view);
            }
        }
        isRefresh = false;
    }
}
