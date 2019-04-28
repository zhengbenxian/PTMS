package com.pacia.ptms.activity.viola;

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
import com.pacia.ptms.adapter.ViolaReguAdapter;
import com.pacia.ptms.bean.CommonBean;
import com.pacia.ptms.bean.ViolaReguInfoBean;
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
 * 首页违章违规  主界面
 */
public class ViolaReguActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.RequestLoadMoreListener {
    private static final String TAG = "ViolaReguActivity";
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipLayout;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_empty_data)
    LinearLayout ll_empty_data;
    @BindView(R.id.tv_error)
    TextView tv_error;
    @BindView(R.id.tv_check_stime)
    TextView tv_check_stime;
    @BindView(R.id.tv_check_etime)
    TextView tv_check_etime;
    @BindView(R.id.tv_check_level)
    TextView tv_check_level;
    @BindView(R.id.tv_check_status)
    TextView tv_check_status;
    private CustomDatePicker picker;
    private ViolaReguAdapter adapter;
    private List<ViolaReguInfoBean> list_viola = new ArrayList<>();
    //总页数
    private int pageCount = 1;
    //当前页
    private int page = 1;
    private int rows = 10;
    private boolean isRefresh = false;
    private String checkSTime = "", checkETime = "", statue = "", grade = "";
    private MySpinnerAdapter adapter_grade, adapter_satue;
    private List<CommonBean> list_grade = new ArrayList<>(), list_statue = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_viola_regu;
    }

    @Override
    public void initView() {
        setTopTitle("违章违规");
        ToolUtils.createSwipeLayout(this, swipLayout);
        ToolUtils.createRecycleManager(this, recyclerView);
        adapter = new ViolaReguAdapter(this, R.layout.item_rv_violarugu_list,
                list_viola);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(this);

        swipLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                queryViolaList(checkSTime, checkETime, grade, statue, page, rows);
            }
        });
        tv_check_stime.setText(DateUtils.getNowDate("yyyy-MM-dd"));
        tv_check_etime.setText(DateUtils.getNowDate("yyyy-MM-dd"));
        initDatePicker(tv_check_stime);
        initDatePicker(tv_check_etime);
    }

    @Override
    public void initData() {
        list_grade = ToolUtils.getGradeList();
        list_statue = ToolUtils.getStatueList();
        adapter_grade = new MySpinnerAdapter(this, list_grade,
                R.layout.layout_single_tv);
        adapter_satue = new MySpinnerAdapter(this, list_statue,
                R.layout.layout_single_tv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRefresh = true;
        page = 1;
        queryViolaList(checkSTime, checkETime, grade, statue, page, rows);
    }

    @OnClick({R.id.tv_check_level, R.id.tv_check_status})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_check_level:
                ListView lv_grade = ToolUtils.showPopupWin(this, tv_check_level, adapter_grade);
                lv_grade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ToolUtils.dimissPopu();
                        page = 1;
                        isRefresh = true;
                        grade = list_grade.get(position).getsGid();
                        tv_check_level.setText(list_grade.get(position).getName());
                        queryViolaList(checkSTime, checkETime, grade, statue, page, rows);
                    }
                });
                break;
            case R.id.tv_check_status:
                ListView lv_statue = ToolUtils.showPopupWin(this, tv_check_status, adapter_satue);
                lv_statue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ToolUtils.dimissPopu();
                        page = 1;
                        isRefresh = true;
                        statue = list_statue.get(position).getsGid();
                        tv_check_status.setText(list_statue.get(position).getName());
                        queryViolaList(checkSTime, checkETime, grade, statue, page, rows);
                    }
                });
                break;
        }
    }

    //查询违章违规列表
    private void queryViolaList(String checkTime, String checkTimeEnd, String wgGrade, String status,
                                int page, int rows) {
        ServiceFactory.getService(ApiService.class)
                .queryViolaReguList(checkTime, checkTimeEnd, wgGrade, status, page, rows)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            List<ViolaReguInfoBean> list_viola = JsonUtils.getViolaReguList(json);
                            int total = JsonUtils.getTotal(json);
                            showViolaReguList(list_viola, total);
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

    private void showViolaReguList(List<ViolaReguInfoBean> list, int total) {
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ViolaReguInfoBean bean = (ViolaReguInfoBean) adapter.getItem(position);
        doActivity(ViolaReguInfoActivity.class, bean.getWfgzGid(),
                bean.getWfgzPlateNo());
    }

    @Override
    public void onLoadMoreRequested() {
        swipLayout.setRefreshing(false);
        if (page < pageCount) {
            page++;
            queryViolaList(checkSTime, checkETime, grade, statue, page, rows);
        } else {
            adapter.setEnableLoadMore(false);
            adapter.loadMoreComplete();
            View view = getLayoutInflater().inflate(R.layout.foot_view, null);
            adapter.addFooterView(view);
        }
        isRefresh = false;
    }

    private void initDatePicker(final TextView tv) {
        int year = DateUtils.getYear();
        picker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                if (!"".equals(time)) {
                    tv.setText(time.split(" ")[0]);
                    switch (tv.getId()) {
                        case R.id.tv_check_stime:
                            checkSTime = tv.getText().toString();
                            break;
                        case R.id.tv_check_etime:
                            isRefresh = true;
                            page = 1;
                            checkETime = tv.getText().toString();
                            queryViolaList(checkSTime, checkETime, grade, statue, page, rows);
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
}
