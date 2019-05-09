package com.pacia.ptms.oildepot.auditing;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.activity.viola.ViolaReguInfoActivity;
import com.pacia.ptms.bean.RecordBean;
import com.pacia.ptms.carrier.car.CarAptitudeActivity;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.utils.http.BaseObserver;
import com.pacia.ptms.utils.http.SchedulersHelper;
import com.pacia.ptms.utils.http.ServiceFactory;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.BindView;
import okhttp3.ResponseBody;

/**
 * 入库审核
 */
public class AuditWarehouseActivity extends BaseActivity {
    private static final String TAG = "AuditWarehouseActivity";
    @BindView(R.id.sv_audit_ware)
    ScrollView sv_audit_ware;
    @BindView(R.id.layout_error)
    LinearLayout layout_error;
    @BindView(R.id.layout_audit_1)
    LinearLayout layout_audit_1;
    @BindView(R.id.layout_audit_2)
    LinearLayout layout_audit_2;
    @BindView(R.id.tv_audit_driver_name)
    TextView tv_audit_driver_name;
    @BindView(R.id.tv_audit_driver_same)
    TextView tv_audit_driver_same;
    @BindView(R.id.tv_audit_edriver_name)
    TextView tv_audit_edriver_name;
    @BindView(R.id.tv_audit_edriver_same)
    TextView tv_audit_edriver_same;
    @BindView(R.id.tv_audit_msg_verifi)
    TextView tv_audit_msg_verifi;
    @BindView(R.id.tv_error)
    TextView tv_error;
    private TextView plate1, plate1_same, plate1_msg_verifi, plate1_have,
            plate1_safe_same, plate1_safe_check, plate1_number, plate1_go,
            plate2, plate2_same, plate2_msg_verifi, plate2_have, plate2_safe_same,
            plate2_safe_check, plate2_number, plate2_go;
    private LinearLayout layout1, layout2;
    private String plate = "";
    private RecordBean recordBean;
    private String[] qycStrsId, gcStrsId;
    private String choseId = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_audit_warehouse;
    }

    @Override
    public void initView() {
        plate = getIntent().getStringExtra("0");
        findViewId();
        onViewClickListener();
        setTopTitle("入库审核");
        setRightMsg("提交");
        setRightClickListener(new OnRightClickListen() {
            @Override
            public void onRightClick(View view) {

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

    private void setViewData(RecordBean bean) {
        plate1.setText(bean.getDyHeaderTsNo1());
        if (bean.getDyHeaderTsNo2().equals("")) {
            layout_audit_2.setVisibility(View.GONE);
        }
        plate2.setText(bean.getDyHeaderTsNo2());
        String qyc = bean.getQycNum();
        if (qyc.equals("")) {
            layout1.setVisibility(View.GONE);
        } else {
            if (qyc.contains(",")) {
                qycStrsId = qyc.split(",");
            } else {
                qycStrsId[0] = qyc;
            }
            plate1_number.setText(ToolUtils.stringFormat(context,
                    R.string.have_number_check, qycStrsId.length));
        }
        String gc = bean.getGcNum();
        if (gc.equals("")) {
            layout2.setVisibility(View.GONE);
        } else {
            if (gc.contains(",")) {
                gcStrsId = gc.split(",");
            } else {
                gcStrsId[0] = gc;
            }
            plate2_number.setText(ToolUtils.stringFormat(context,
                    R.string.have_number_check, gcStrsId.length));
        }
        tv_audit_driver_name.setText(bean.getDyHeaderDriver());
        tv_audit_edriver_name.setText(bean.getDyHeaderSrf1());
        if (!"".equals(bean.getDyHeaderSrf5())) {
            plate1_same.setVisibility(View.VISIBLE);
            plate1_same.setText(bean.getDyHeaderSrf5());
        }
        if (!"".equals(bean.getDyHeaderSrf4())) {
            plate2_same.setVisibility(View.VISIBLE);
            plate2_same.setText(bean.getDyHeaderSrf4());
        }
    }


    private void onViewClickListener() {
        plate1_msg_verifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != recordBean) {
                    Intent intent = new Intent(context, CarAptitudeActivity.class);
                    intent.putExtra("doActivity", "warehouse");
                    intent.putExtra("doType", "qyc");
                    intent.putExtra("plate", plate1.getText().toString());
                    intent.putExtra("gid", recordBean.getDyHeaderGid());
                    startActivityForResult(intent, 11111);

                }
            }
        });
        plate1_safe_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SafeCheckActivity.class);
                intent.putExtra("plate", plate1.getText().toString());
                intent.putExtra("gid", recordBean.getDyHeaderGid());
                startActivityForResult(intent, 11113);
            }
        });
        plate1_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShow(qycStrsId, plate1.getText().toString());
            }
        });
        plate2_msg_verifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != recordBean) {
                    Intent intent = new Intent(context, CarAptitudeActivity.class);
                    intent.putExtra("doActivity", "warehouse");
                    intent.putExtra("doType", "gc");
                    intent.putExtra("plate", plate2.getText().toString());
                    intent.putExtra("gid", recordBean.getDyHeaderGid());
                    startActivityForResult(intent, 11112);
                }
            }
        });
        plate2_safe_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SafeCheckActivity.class);
                intent.putExtra("plate", plate2.getText().toString());
                intent.putExtra("gid", recordBean.getDyHeaderGid());
                startActivityForResult(intent, 11114);
            }
        });
        plate2_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShow(gcStrsId, plate2.getText().toString());
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 10000) {
            String statue = data.getStringExtra("statue");
            switch (requestCode) {
                case 11111:
                    plate1_same.setText(statue);
                    break;
                case 11112:
                    plate2_same.setText(statue);
                    break;
            }
        } else if (resultCode == 10001) {
            String statue = data.getStringExtra("statue");
            switch (requestCode) {
                case 11113:
                    plate1_safe_same.setVisibility(View.VISIBLE);
                    plate1_safe_same.setText(statue);
                    break;
                case 11114:
                    plate1_safe_same.setVisibility(View.VISIBLE);
                    plate2_safe_same.setText(statue);
                    break;
            }
        }
    }

    //查找控件id
    private void findViewId() {
        plate1 = layout_audit_1.findViewById(R.id.tv_audit_plate);
        plate1_same = layout_audit_1.findViewById(R.id.tv_audit_plate_same);
        plate1_msg_verifi = layout_audit_1.findViewById(R.id.tv_audit_msg_verifi);
        plate1_have = layout_audit_1.findViewById(R.id.tv_audit_have);
        plate1_safe_same = layout_audit_1.findViewById(R.id.tv_audit_safe_same);
        plate1_safe_check = layout_audit_1.findViewById(R.id.tv_audit_safe_check);
        plate1_number = layout_audit_1.findViewById(R.id.tv_audit_number);
        plate1_go = layout_audit_1.findViewById(R.id.tv_audit_go);
        layout1 = layout_audit_1.findViewById(R.id.layout_no_complete);
        plate2 = layout_audit_2.findViewById(R.id.tv_audit_plate);
        plate2_same = layout_audit_2.findViewById(R.id.tv_audit_plate_same);
        plate2_msg_verifi = layout_audit_2.findViewById(R.id.tv_audit_msg_verifi);
        plate2_have = layout_audit_2.findViewById(R.id.tv_audit_have);
        plate2_safe_same = layout_audit_2.findViewById(R.id.tv_audit_safe_same);
        plate2_safe_check = layout_audit_2.findViewById(R.id.tv_audit_safe_check);
        plate2_number = layout_audit_2.findViewById(R.id.tv_audit_number);
        plate2_go = layout_audit_2.findViewById(R.id.tv_audit_go);
        layout2 = layout_audit_2.findViewById(R.id.layout_no_complete);
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

    private void onFailed(String msg) {
        sv_audit_ware.setVisibility(View.GONE);
        layout_error.setVisibility(View.VISIBLE);
        tv_error.setText(msg);
    }
}
