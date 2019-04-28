package com.pacia.ptms.activity.statistics;


import android.content.Context;
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
import com.pacia.ptms.adapter.StatisticsAdapter;
import com.pacia.ptms.bean.StatisticsBean;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.DateUtils;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.utils.http.BaseObserver;
import com.pacia.ptms.utils.http.SchedulersHelper;
import com.pacia.ptms.utils.http.ServiceFactory;
import com.pacia.ptms.widget.CustomDatePicker;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;

/**
 * 安全统计 根据承运工具
 */
public class StatisticsToolFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    public static Fragment getInstance(String msg) {
        StatisticsToolFragment fragment = new StatisticsToolFragment();
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
    @BindView(R.id.tv_time)
    TextView tv_time;
    //当前页  行数  总页数
    private int page = 1, rows = 10, pageCount = 1;
    private boolean isRefresh = false;
    private String date = "", type = "";
    private List<StatisticsBean> list_sta = new ArrayList<>();
    private StatisticsAdapter adapter;
    private CustomDatePicker picker;
    private int year, month, day;

    @Override
    public int getLayoutId() {
        return R.layout.layout_swip_recycle;
    }

    @Override
    public void initView() {
        ToolUtils.createSwipeLayout(fContext, swipLayout);
        ToolUtils.createRecycleManager(fContext, recyclerView);
        type = Constant.TRANS_ROAD;
        tv_time.setVisibility(View.VISIBLE);
        initDatePicker(fContext, picker, tv_time);
    }

    @Override
    public void initData() {
        adapter = new StatisticsAdapter(fContext, "tool", R.layout.item_rv_statis_list, list_sta);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        swipLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                getStatisticsToolList(type, date, page, rows);
            }
        });
        year = DateUtils.getYear();
        month = DateUtils.getMonth();
        day = DateUtils.getDay();
        tv_time.setText(year + "-" + month);
    }

    @Override
    public void lazyLoadData() {
        isRefresh = true;
        page = 1;
        getStatisticsToolList(type, date, page, rows);
    }

    private void getStatisticsToolList(String type, String date, int page, int rows) {
        ServiceFactory.getService(ApiService.class)
                .queryStatisticsByTool(type, date, page, rows)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            List<StatisticsBean> list = JsonUtils.getStatisticsList(json);
                            int total = JsonUtils.getTotal(json);
                            showStatisticsToolList(list, total);
                        } else {
                            onFailed("查询失败");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        onFailed(error);
                    }
                });
    }

    private void showStatisticsToolList(List<StatisticsBean> list, int total) {
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
            getStatisticsToolList(type, date, page, rows);
        } else {
            adapter.setEnableLoadMore(false);
            adapter.loadMoreComplete();
            View view = getLayoutInflater().inflate(R.layout.foot_view, null);
            adapter.addFooterView(view);
        }
        isRefresh = false;
    }

    private void onFailed(String msg) {
        adapter.getData().clear();
        adapter.notifyDataSetChanged();
        swipLayout.setVisibility(View.GONE);
        swipLayout.setRefreshing(false);
        tv_error.setText(msg);
    }

    private void initDatePicker(Context context, CustomDatePicker cdpStart, final TextView tv) {
        int year = DateUtils.getYear();
        //开始日期选择框
        cdpStart = new CustomDatePicker(false, context, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                if (!"".equals(time)) {
                    isRefresh = true;
                    page = 1;
                    String times = time.split(" ")[0];
                    String yearM[] = times.split("-");
                    date = yearM[0] + "-" + yearM[1];
                    tv.setText(date);
                    getStatisticsToolList(type, DateUtils.yearToTimeStamp(date) + "", page, rows);
                }
            }
        }, DateUtils.setDate(year - 2, 1, 1),
                DateUtils.setDate(year + 3, 12, 31));
        cdpStart.showSpecificTime(false); // 不显示时和分
        cdpStart.setIsLoop(false); // 不允许循环滚动
        final CustomDatePicker finalcdpStart = cdpStart;
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalcdpStart.show(DateUtils.getNowDate());
            }
        });
    }
}
