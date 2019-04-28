package com.pacia.ptms.activity.transport;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.MySpinnerAdapter;
import com.pacia.ptms.adapter.TransSuperAdapter;
import com.pacia.ptms.bean.CommonBean;
import com.pacia.ptms.bean.RecordBean;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.DateUtils;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.utils.http.BaseObserver;
import com.pacia.ptms.utils.http.SchedulersHelper;
import com.pacia.ptms.utils.http.ServiceFactory;
import com.pacia.ptms.widget.CustomDatePicker;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * 运输监管
 */
public class TransSuperActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener,
        BaseQuickAdapter.OnItemClickListener {
    private static final String TAG = "TransSuperActivity";

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipLayout;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_empty_data)
    LinearLayout ll_empty_data;
    @BindView(R.id.tv_error)
    TextView tv_error;
    @BindView(R.id.tv_trans_state)
    TextView tv_trans_state;
    @BindView(R.id.tv_trans_sdate)
    TextView tv_trans_sdate;
    @BindView(R.id.tv_trans_edate)
    TextView tv_trans_edate;
    private CustomDatePicker picker;
    private TransSuperAdapter adapter;
    private List<RecordBean> list_ts = new ArrayList<>();
    //总页数
    private int pageCount = 1;
    //当前页
    private int page = 1;
    private int rows = 10;
    private boolean isRefresh = false;
    private String checkSTime = "", checkETime = "", statue = "";
    private MySpinnerAdapter adapter_spinner;
    private List<CommonBean> list_statue = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_trans_super;
    }

    @Override
    public void initView() {
        setTopTitle("运输监管");
        ToolUtils.createSwipeLayout(this, swipLayout);
        ToolUtils.createRecycleManager(this, recyclerView);
        tv_trans_sdate.setText(DateUtils.getNowDate());
        tv_trans_edate.setText(DateUtils.getNowDate());
        initDatePicker(tv_trans_sdate);
        initDatePicker(tv_trans_edate);

        swipLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                queryTransSuperList(checkSTime, checkETime, statue, page, rows);
            }
        });
    }

    @Override
    public void initData() {
        list_statue = ToolUtils.getTransStatue();
        adapter_spinner = new MySpinnerAdapter(this, list_statue,
                R.layout.layout_single_tv);
        adapter = new TransSuperAdapter(this, R.layout.item_rv_trans_super, list_ts);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(this);
        queryTransSuperList(checkSTime, checkETime, statue, page, rows);
    }

    @OnClick({R.id.tv_trans_state})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_trans_state:
                ListView lv = ToolUtils.showPopupWin(this, tv_trans_state, adapter_spinner);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ToolUtils.dimissPopu();
                        isRefresh = true;
                        page = 1;
                        tv_trans_state.setText(list_statue.get(position).getName());
                        statue = list_statue.get(position).getsGid();
                        queryTransSuperList(checkSTime, checkETime, statue, page, rows);
                    }
                });
                break;
        }
    }

    private void queryTransSuperList(String date, String bDateEnd, String statue, int page, int row) {
        ServiceFactory.getService(ApiService.class)
                .queryTransSuperList(date, bDateEnd, statue, page, rows)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            List<RecordBean> list_ts = JsonUtils.getCarRecordList(json);
                            int total = JsonUtils.getTotal(json);
                            showTransSuperList(list_ts, total);
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

    private void showTransSuperList(List<RecordBean> list, int total) {
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
        tv_error.setText(msg);
        adapter.getData().clear();
        adapter.notifyDataSetChanged();
        swipLayout.setVisibility(View.GONE);
    }

    private void initDatePicker(final TextView tv) {
        int year = DateUtils.getYear();
        picker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                if (!"".equals(time)) {
                    tv.setText(time.split(" ")[0]);
                    switch (tv.getId()) {
                        case R.id.tv_trans_sdate:
                            checkSTime = tv.getText().toString();
                            break;
                        case R.id.tv_trans_edate:
                            isRefresh = true;
                            page = 1;
                            checkETime = tv.getText().toString();
                            queryTransSuperList(checkSTime, checkETime, statue, page, rows);
                            break;
                    }
                }
            }
        }, DateUtils.setDate(year - 3, 1, 1),
                DateUtils.setDate(year + 5, 12, 31));
        picker.showSpecificTime(false);
        picker.setIsLoop(false);
        final CustomDatePicker finalcdpStart = picker;
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalcdpStart.show(tv.getText().toString());
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        swipLayout.setRefreshing(false);
        if (page < pageCount) {
            page++;
            queryTransSuperList(checkSTime, checkETime, statue, page, rows);
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
        RecordBean bean = (RecordBean) adapter.getItem(position);
        doActivity(TransInfoActivity.class, bean.getDyHeaderGid());
    }
}
