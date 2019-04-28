package com.pacia.ptms.activity.warning;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.MonitorAdapter;
import com.pacia.ptms.bean.MonitorBean;
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
 * 非法登车详情
 */
public class IllegalBoardActivity extends BaseActivity {
    @BindView(R.id.layout_illegal)
    LinearLayout layout_illegal;
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
    @BindView(R.id.rv_illegal)
    RecyclerView rv_illegal;
    private String gid = "";
    private MonitorAdapter adapter;
    private List<MonitorBean> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_illegal_board;
    }

    @Override
    public void initView() {
        setTopTitle(getString(R.string.illegal_boarding));
        gid = getIntent().getStringExtra("0");
        ToolUtils.createRecycleManager(this, rv_illegal);
        adapter = new MonitorAdapter(context, "illegal", R.layout.item_rv_car_monitor, list);
        rv_illegal.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initData() {
        queryIllegalInfo(gid);
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
        tv_illegal_type.setText(getString(R.string.illegal_boarding));
    }

    private void queryIllegalInfo(String gid) {
        ServiceFactory.getService(ApiService.class)
                .queryWarningIllegal(gid)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            WarningBean bean = JsonUtils.getWarningIllegalInfo(json);
                            setViewData(bean);
                            list = JsonUtils.getIllegalMonitor(json);
                            adapter.addData(list);
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

    private void onFailed(String msg) {
        layout_illegal.setVisibility(View.GONE);
        tv_error.setVisibility(View.VISIBLE);
        tv_error.setText(msg);
    }
}
