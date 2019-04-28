package com.pacia.ptms.activity.transport;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.TransProgressAdapter;
import com.pacia.ptms.bean.RecordBean;
import com.pacia.ptms.bean.TransProgressBean;
import com.pacia.ptms.oildepot.auditing.AuditWarehouseActivity;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.DateUtils;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.SPUtils;
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

/**
 * 运输详情
 */
public class TransInfoActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    private static final String TAG = "TransInfoActivity";
    @BindView(R.id.tv_trans_info_state)
    TextView tv_trans_info_state;
    @BindView(R.id.tv_transinfo_out_company)
    TextView tv_trans_out_company;
    @BindView(R.id.tv_trans_info_oils)
    TextView tv_trans_info_oils;
    @BindView(R.id.tv_trans_info_date)
    TextView tv_trans_info_date;
    @BindView(R.id.tv_trans_info_type)
    TextView tv_trans_info_type;
    @BindView(R.id.tv_trans_info_No)
    TextView tv_trans_info_No;
    @BindView(R.id.tv_trans_info_driver)
    TextView tv_trans_info_driver;
    @BindView(R.id.rv_trans_progress)
    RecyclerView rv_trans_progress;
    //    private RecordBean recordBean = new RecordBean();
    private List<TransProgressBean> list_pro = new ArrayList<>();
    private TransProgressAdapter adapter;
    private String trans_type = "", trans_company = "", gid = "";
    private String truckNo1 = "", truckNo2 = "", perGid1 = "", perGid2 = "",
            perName1 = "", perName2 = "", isTNo1 = "", isTNo2 = "", isPer1 = "", isPer2 = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_trans_info;
    }

    @Override
    public void initView() {
        setTopTitle(getString(R.string.trans_info));
        if (SPUtils.getRoleType(this).equals(Constant.ROLE_OIL_WAREHOUSE)) {
            setRightMsg("", R.drawable.ic_inspec_edit);
        }
        gid = getIntent().getStringExtra("0");
        ToolUtils.createRecycleManager(context, rv_trans_progress);
        rv_trans_progress.setNestedScrollingEnabled(false);
        adapter = new TransProgressAdapter(context, R.layout.item_rv_trans_progress, list_pro);
        rv_trans_progress.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();
        setRightClickListener(new OnRightClickListen() {
            @Override
            public void onRightClick(View view) {
                doActivity(AuditWarehouseActivity.class, truckNo1);
            }
        });
    }

    @Override
    public void initData() {
        queryTransInfo(gid);
    }

    @OnClick({R.id.layout_trans_info})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.layout_trans_info:
                Log.e(TAG, "onViewClick: " + perGid1);
                doActivity(DispatchInfoActivity.class, trans_type, trans_company,
                        truckNo1, truckNo2, perGid1, perGid2);
                break;
        }
    }

    //查询运输详细信息
    private void queryTransInfo(String gid) {
        ServiceFactory.getService(ApiService.class)
                .queryTransRecordInfo(gid)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            RecordBean recordBean = JsonUtils.getTransRecordInfo(json);
                            setViewData(recordBean);
                            list_pro = recordBean.getProgressList();
                            list_pro = getTransProgress(recordBean, list_pro);
                            adapter.addData(list_pro);
                            adapter.notifyDataSetChanged();
                        } else {

                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    private void setViewData(RecordBean rb) {
        tv_trans_info_state.setText(rb.getDyjdStatus());
        tv_trans_out_company.setText(rb.getDyHeaderOutWarehouseName());
        String gasStations = rb.getDyDetailGasStation();
        if (gasStations.contains(",")) {
            String gas[] = gasStations.split(",");
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < gas.length; i++) {
                if (i == gas.length - 1) {
                    sb.append(gas[i]);
                } else {
                    sb.append(gas[i] + "\n");
                }
            }
            tv_trans_info_oils.setText(sb);
        } else {
            tv_trans_info_oils.setText(gasStations);
        }
        tv_trans_info_date.setText(DateUtils.timeStampToDate(rb.getDyHeaderBDate()) + " - " +
                DateUtils.timeStampToDate(rb.getDyHeaderSrf9()));
        trans_type = rb.getDyHeaderTransportType();
        tv_trans_info_type.setText(rb.getDyHeaderTransportType());
        tv_trans_info_No.setText(rb.getDyHeaderTsNo1() + "  " + rb.getDyHeaderTsNo2());
        truckNo1 = rb.getDyHeaderTsNo1();
        truckNo2 = rb.getDyHeaderTsNo2();
        tv_trans_info_driver.setText(rb.getDyHeaderDriver() + "  " + rb.getDyHeaderSrf1());
        perGid1 = rb.getDyHeaderDriverGid();
        perGid2 = rb.getDyHeaderSrf1Gid();
        perName1 = rb.getDyHeaderDriver();
        perName2 = rb.getDyHeaderSrf1();
        isPer1 = rb.getDyHeaderSrf2();
        isPer2 = rb.getDyHeaderSrf3();
        isTNo1 = rb.getDyHeaderSrf4();
        isTNo2 = rb.getDyHeaderSrf5();
    }

    //当进度信息里含已发班 加上司机等信息
    private List<TransProgressBean> getTransProgress(RecordBean rb, List<TransProgressBean> list) {
        List<TransProgressBean> listNew = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            TransProgressBean bean = list.get(i);
            if (bean.getDyjdStatus().equals(Constant.TRANS_PRO_ISSUED_CLASS)) {
                bean.setCarNo(rb.getDyHeaderTsNo1() + "  " + rb.getDyHeaderTsNo2());
                bean.setDriver(rb.getDyHeaderDriver());
                bean.setDriver_e(rb.getDyHeaderSrf1());
                trans_company = bean.getDyjdConfirmBy();
            }
            listNew.add(bean);
        }
        return listNew;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        TransProgressBean bean = (TransProgressBean) adapter.getItem(position);
        if (bean.getDyjdStatus().equals(Constant.TRANS_PRO_ISSUED_CLASS)) {
            doActivity(DispatchInfoActivity.class, trans_type, trans_company,
                    truckNo1, truckNo2, perGid1, perGid2);
        } else if (bean.getDyjdStatus().equals(Constant.TRANS_PRO_WAREHOUSING)) {
            doActivity(TransWarehouseActivity.class, truckNo1, truckNo2, perGid1, perGid2, perName1,
                    perName2, bean.getDyjdConfirmTime(), bean.getDyjdConfirmBy(),
                    isPer1, isPer2, isTNo1, isTNo2);
        } else {
            doActivity(TransProDetailActivity.class, bean.getDyjdStatus(),
                    bean.getDyjdConfirmBy(), bean.getDyjdConfirmTime());
        }
    }
}
