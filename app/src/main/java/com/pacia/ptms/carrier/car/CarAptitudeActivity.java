package com.pacia.ptms.carrier.car;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.CarBasicAdapter;
import com.pacia.ptms.bean.CarBasicBean;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.DialogUtils;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.SPUtils;
import com.pacia.ptms.utils.ToastUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.utils.http.BaseObserver;
import com.pacia.ptms.utils.http.SchedulersHelper;
import com.pacia.ptms.utils.http.ServiceFactory;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class CarAptitudeActivity extends BaseActivity {
    private static final String TAG = "CarAptitudeActivity";
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_empty_data)
    LinearLayout ll_empty_data;
    @BindView(R.id.layout_aptitude_check)
    LinearLayout layout_aptitude_check;
    @BindView(R.id.tv_error)
    TextView tv_error;
    private String plateNo1 = "", plateNo2 = "", type = "", headGid = "",
            doType = "";
    private CarBasicAdapter adapter;
    private List<CarBasicBean> list_car = new ArrayList<>();
    private DialogUtils dialogUtils;

    @Override
    public int getLayoutId() {
        return R.layout.activity_aptitude_check;
    }

    @Override
    public void initView() {
        dialogUtils = new DialogUtils();
        ToolUtils.createRecycleManager(context, recyclerView);
        type = getIntent().getStringExtra("doActivity");
        if ("warehouse".equals(type)) {
            doType = getIntent().getStringExtra("doType");
            plateNo1 = getIntent().getStringExtra("plate");
            headGid = getIntent().getStringExtra("gid");
        } else {
            plateNo1 = getIntent().getStringExtra("0");
            plateNo2 = getIntent().getStringExtra("1");
        }
        setTopTitle("车辆资质信息");
        if (SPUtils.getRoleType(this).equals(Constant.ROLE_OIL_WAREHOUSE)) {
            layout_aptitude_check.setVisibility(View.VISIBLE);
            adapter = new CarBasicAdapter(context, R.layout.item_rv_car_basic, list_car, false);
        } else {
            layout_aptitude_check.setVisibility(View.GONE);
            adapter = new CarBasicAdapter(context, R.layout.item_rv_car_basic, list_car, true);
        }
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initData() {
        queryCarInfo(plateNo1, plateNo2);
    }

    @OnClick({R.id.tv_not_same, R.id.tv_same})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_not_same:
                if ("qyc".equals(doType)) {
                    makeSureCommit(headGid, "", "不一致",
                            "", "");
                } else if ("gc".equals(doType)) {
                    makeSureCommit(headGid, "不一致", "",
                            "", "");
                }
                break;
            case R.id.tv_same:
                if ("qyc".equals(doType)) {
                    makeSureCommit(headGid, "", "一致",
                            "", "");
                } else if ("gc".equals(doType)) {
                    makeSureCommit(headGid, "一致", "",
                            "", "");
                }
                break;
        }
    }

    //查询车辆基本信息
    private void queryCarInfo(String cNo, String cNoT) {
        ServiceFactory.getService(ApiService.class)
                .queryCarInfoByGid(cNo, cNoT)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
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

    private void makeSureCommit(final String gid, final String gcStatus, final String qycStatus, final String siStatus,
                                final String syStatus) {
        dialogUtils.createAlertDialog(this, "提示", "确认提交", true);
        dialogUtils.setOnSureClickListener(new DialogUtils.onSureClickListener() {
            @Override
            public void onClick(View view) {
                updateData(gid, gcStatus, qycStatus, siStatus, syStatus);
            }
        });
    }

    //一致不一致上传
    private void updateData(String gid, final String gcStatus, final String qycStatus, String siStatus,
                            String syStatus) {
        ServiceFactory.getService(ApiService.class)
                .updateByExampleSelective(SPUtils.getUserGid(context), gid,
                        gcStatus, qycStatus, siStatus, syStatus)
                .compose(SchedulersHelper.<ResponseBody>io_main(context, true,"提交中..."))
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            if ("qyc".equals(doType)) {
                                updateOnSuccess(qycStatus);
                            } else if ("gc".equals(doType)) {
                                updateOnSuccess(gcStatus);
                            }
                        } else {
                            ToastUtils.showShort("上传失败，请重新提交");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        ToastUtils.showShort("上传失败，请稍后再试。" + error);
                    }
                });
    }

    private void updateOnSuccess(String statue) {
        Intent intent = new Intent();
        intent.putExtra("statue", statue);
        setResult(10000, intent);
        finish();
    }

    private void onFailed(String msg) {
        ll_empty_data.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        layout_aptitude_check.setVisibility(View.GONE);
        tv_error.setText(msg);
        adapter.getData().clear();
        adapter.notifyDataSetChanged();
    }
}
