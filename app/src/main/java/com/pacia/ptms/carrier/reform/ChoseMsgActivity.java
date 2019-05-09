package com.pacia.ptms.carrier.reform;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.activity.viola.ViolaReguInfoActivity;
import com.pacia.ptms.bean.RecordBean;
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

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * 承运商选择人员
 */
public class ChoseMsgActivity extends BaseActivity {
    private static final String TAG = "ChoseMsgActivity";
    @BindView(R.id.tv_audit_plate1)
    TextView tv_audit_plate1;
    @BindView(R.id.layout_no_complete1)
    LinearLayout layout_no_complete1;
    @BindView(R.id.tv_audit_number1)
    TextView tv_audit_number1;
    @BindView(R.id.layout_plate2)
    LinearLayout layout_plate2;
    @BindView(R.id.tv_audit_plate2)
    TextView tv_audit_plate2;
    @BindView(R.id.layout_no_complete2)
    LinearLayout layout_no_complete2;
    @BindView(R.id.tv_audit_number2)
    TextView tv_audit_number2;
    @BindView(R.id.tv_audit_driver_name)
    TextView tv_audit_driver_name;
    @BindView(R.id.tv_audit_edriver_name)
    TextView tv_audit_edriver_name;
    @BindView(R.id.tv_error)
    TextView tv_error;
    @BindView(R.id.sv_audit_ware)
    ScrollView sv_audit_ware;
    @BindView(R.id.layout_error)
    LinearLayout layout_error;
    private String plate = "", choseId = "", driverId = "", eDriverId = "",
            gid = "";
    private RecordBean recordBean;
    private String[] qycStrsId, gcStrsId;
    private DialogUtils dialogUtils;

    @Override
    public int getLayoutId() {
        return R.layout.activity_chose_msg;
    }

    @Override
    public void initView() {
        plate = getIntent().getStringExtra("0");
        gid = getIntent().getStringExtra("1");
        dialogUtils = new DialogUtils();
        setTopTitle("填写信息");
        setRightMsg("提交");
        setRightClickListener(new OnRightClickListen() {
            @Override
            public void onRightClick(View view) {
                dialogUtils.createAlertDialog((Activity) context, "提示",
                        "确认提交", true);
                dialogUtils.setOnSureClickListener(new DialogUtils.onSureClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e(TAG, "onClick: " + driverId + "  " + eDriverId);
                        commitChosePerson(gid, driverId, eDriverId);
                    }
                });
            }
        });
    }

    @Override
    public void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        queryCheckMsgByPlate(plate);
    }

    @OnClick({R.id.tv_audit_go1, R.id.tv_audit_go2, R.id.tv_audit_driver_go,
            R.id.tv_audit_edriver_go})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_audit_go1:
                dialogShow(qycStrsId, tv_audit_plate1.getText().toString());
                break;
            case R.id.tv_audit_go2:
                dialogShow(gcStrsId, tv_audit_plate2.getText().toString());
                break;
            case R.id.tv_audit_driver_go:
                Intent intent = new Intent(context, ChosePersonActivity.class);
                intent.putExtra("type", "driver");
                startActivityForResult(intent, 11100);
                break;
            case R.id.tv_audit_edriver_go:
                Intent intent1 = new Intent(context, ChosePersonActivity.class);
                intent1.putExtra("type", "edriver");
                startActivityForResult(intent1, 11101);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 11111) {
            String gid = data.getStringExtra("gid");
            String name = data.getStringExtra("name");
            switch (requestCode) {
                case 11100:
                    tv_audit_driver_name.setText(name);
                    driverId = gid;
                    break;
                case 11101:
                    eDriverId = gid;
                    tv_audit_edriver_name.setText(name);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //根据车牌号查询审核信息
    private void queryCheckMsgByPlate(String plate) {
        ServiceFactory.getService(ApiService.class)
                .queryCheckMsgByPlate(plate)
                .compose(SchedulersHelper.<ResponseBody>io_main(context, true,"访问中..."))
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            recordBean = JsonUtils.getTransRecordInfo(json);
                            setViewData(recordBean);
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

    private void setViewData(RecordBean bean) {
        tv_audit_plate1.setText(bean.getDyHeaderTsNo1());
        if (bean.getDyHeaderTsNo2().equals("")) {
            layout_plate2.setVisibility(View.GONE);
        }
        tv_audit_plate2.setText(bean.getDyHeaderTsNo2());
        String qyc = bean.getQycNum();
        if (qyc.equals("")) {
            layout_no_complete1.setVisibility(View.GONE);
        } else {
            if (qyc.contains(",")) {
                qycStrsId = qyc.split(",");
            } else {
                qycStrsId[0] = qyc;
            }
            tv_audit_number1.setText(ToolUtils.stringFormat(context,
                    R.string.have_number_check, qycStrsId.length));
        }
        String gc = bean.getGcNum();
        if (gc.equals("")) {
            layout_no_complete2.setVisibility(View.GONE);
        } else {
            if (gc.contains(",")) {
                gcStrsId = gc.split(",");
            } else {
                gcStrsId[0] = gc;
            }
            tv_audit_number2.setText(ToolUtils.stringFormat(context,
                    R.string.have_number_check, gcStrsId.length));
        }
    }

    private void onFailed(String msg) {
        sv_audit_ware.setVisibility(View.GONE);
        layout_error.setVisibility(View.VISIBLE);
        tv_error.setText(msg);
    }

    private void dialogShow(final String[] array, final String plate) {
        choseId = array[0];
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("请选择违规单")
                .setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choseId = array[which];
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doActivity(ViolaReguInfoActivity.class, choseId, plate);
                    }
                }).create();
        dialog.show();
    }

    //选择人员提交
    private void commitChosePerson(String dyHeadGid, String driver, String escort) {
        ServiceFactory.getService(ApiService.class)
                .chosePerson(dyHeadGid, driver, escort, SPUtils.getUserGid(context))
                .compose(SchedulersHelper.<ResponseBody>io_main(context, true,"提交中..."))
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {

                        } else {
                            ToastUtils.showShort("提交失败，请稍后再试");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        ToastUtils.showShort("提交失败，请稍后再试" + error);
                    }
                });
    }
}
