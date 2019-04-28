package com.pacia.ptms.activity.warning;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.RoteAdapter;
import com.pacia.ptms.bean.RoteBean;
import com.pacia.ptms.bean.WarningBean;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.DateUtils;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.utils.http.BaseObserver;
import com.pacia.ptms.utils.http.SchedulersHelper;
import com.pacia.ptms.utils.http.ServiceFactory;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;

/**
 * 路线偏离和超时停车详情
 */
public class WarningRoteActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    private static final String TAG = "WarningRoteActivity";
    @BindView(R.id.layout_rote)
    LinearLayout layout_rote;
    @BindView(R.id.rv_rote)
    RecyclerView rv_rote;
    @BindView(R.id.tv_illegal_plate)
    TextView tv_illegal_plate;
    @BindView(R.id.tv_illegal_time)
    TextView tv_illegal_time;
    @BindView(R.id.tv_illegal_type)
    TextView tv_illegal_type;
    @BindView(R.id.tv_illegal_driver)
    TextView tv_illegal_driver;
    @BindView(R.id.tv_illegal_driver_tel)
    TextView tv_illegal_driver_tel;
    @BindView(R.id.tv_illegal_edriver)
    TextView tv_illegal_edriver;
    @BindView(R.id.tv_illegal_edriver_tel)
    TextView tv_illegal_edriver_tel;
    @BindView(R.id.tv_error)
    TextView tv_error;
    private String gid = "", type = "";
    private RoteAdapter adapter;
    //路线偏离
    private List<RoteBean> list_rote = new ArrayList<>(), list_overtime = new ArrayList<>();
    //超时停车
    private List<WarningBean> list_warning = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_warning_rote;
    }

    @Override
    public void initView() {
        type = getIntent().getStringExtra("0");
        gid = getIntent().getStringExtra("1");
        if ("rote".equals(type)) {
            setTopTitle(getString(R.string.route_deviation));
        } else if ("overtime".equals(type)) {
            setTopTitle(getString(R.string.overtime_parking));
        }
        ToolUtils.createRecycleManager(this, rv_rote);
        rv_rote.setNestedScrollingEnabled(false);

    }

    @Override
    public void initData() {
        if ("rote".equals(type)) {
            queryRoteInfo(gid);
        } else if ("overtime".equals(type)) {
            queryOverTimeInfo(gid);
        }
    }

    private void setViewData(WarningBean bean) {
        tv_illegal_plate.setText(bean.getTsNo());
        tv_illegal_time.setText(DateUtils.timeStampToDate(bean.getWarningDate()));
        tv_illegal_type.setText(bean.getWarningTypeName());
        tv_illegal_driver.setText(bean.getStaffName());
        tv_illegal_driver_tel.setText(bean.getStaffPhone());
        ToolUtils.setTvUnderLine(this, tv_illegal_driver_tel);
        tv_illegal_edriver.setText(bean.getStaffName1());
        tv_illegal_edriver_tel.setText(bean.getStaffPhone1());
        ToolUtils.setTvUnderLine(this, tv_illegal_edriver_tel);
        if ("rote".equals(type)) {
            tv_illegal_type.setText(getString(R.string.route_deviation));
        } else if ("overtime".equals(type)) {
            tv_illegal_type.setText(getString(R.string.overtime_parking));
        }
    }

    //查询路线偏离
    private void queryRoteInfo(String gid) {
        ServiceFactory.getService(ApiService.class)
                .queryWarningRote(gid)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            WarningBean bean = JsonUtils.getWarningIllegalInfo(json);
                            setViewData(bean);
                            list_rote = JsonUtils.getWarningRote(json);
                            adapter = new RoteAdapter(context, "rote", R.layout.item_rv_warning_rote, list_rote);
                            adapter.setOnItemClickListener((BaseQuickAdapter.OnItemClickListener) context);
                            rv_rote.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
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

    //查询超时停车
    private void queryOverTimeInfo(String gid) {
        ServiceFactory.getService(ApiService.class)
                .queryWarningOverTime(gid)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            list_warning = JsonUtils.getWarningList(json);
                            if (list_warning.size() > 0) {
                                setViewData(list_warning.get(0));
                                list_overtime = getRoteListFromWarning(list_warning);
                                adapter = new RoteAdapter(context, "overtime",
                                        R.layout.item_rv_warning_rote, list_overtime);
                                rv_rote.setAdapter(adapter);
                                adapter.setOnItemClickListener((BaseQuickAdapter.OnItemClickListener) context);
                                adapter.notifyDataSetChanged();
                            }
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

    private List<RoteBean> getRoteListFromWarning(List<WarningBean> list) {
        List<RoteBean> list_rote = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            WarningBean bean = list.get(i);
            RoteBean roteBean = new RoteBean();
            roteBean.setPositionLat(bean.getPositionLat());
            roteBean.setPositionLong(bean.getPositionLong());
            roteBean.setInsertDate(bean.getSrf2());
            list_rote.add(roteBean);
        }
        return list_rote;
    }

    private void onFailed(String msg) {
        rv_rote.setVisibility(View.GONE);
        layout_rote.setVisibility(View.GONE);
        tv_error.setVisibility(View.VISIBLE);
        tv_error.setText(msg);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        RoteBean roteBean = (RoteBean) adapter.getItem(position);
        if ("rote".equals(type)) {
            Log.e(TAG, "onItemClick: ");
        } else if ("overtime".equals(type)) {
            Log.e(TAG, "onItemClick: " + type);
        }
    }
}
