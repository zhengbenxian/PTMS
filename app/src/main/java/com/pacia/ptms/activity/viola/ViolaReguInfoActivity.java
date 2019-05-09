package com.pacia.ptms.activity.viola;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.BannerImageLoader;
import com.pacia.ptms.adapter.ViolaReformAdapter;
import com.pacia.ptms.bean.ViolaReformBean;
import com.pacia.ptms.bean.ViolaReguInfoBean;
import com.pacia.ptms.bean.ViolatCheckBean;
import com.pacia.ptms.carrier.reform.ReformActivity;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.DateUtils;
import com.pacia.ptms.utils.DialogUtils;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.SPUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.utils.http.BaseObserver;
import com.pacia.ptms.utils.http.SchedulersHelper;
import com.pacia.ptms.utils.http.ServiceFactory;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;

/**
 * 违章违规详情
 */
public class ViolaReguInfoActivity extends BaseActivity {
    @BindView(R.id.banner_viola)
    Banner banner_viola;
    @BindView(R.id.tv_viola_plate)
    TextView tv_viola_plate;
    @BindView(R.id.tv_viola_company)
    TextView tv_viola_company;
    @BindView(R.id.tv_viola_per_name)
    TextView tv_viola_per_name;
    @BindView(R.id.tv_viola_tel)
    TextView tv_viola_tel;
    @BindView(R.id.tv_viola_grade)
    TextView tv_viola_grade;
    @BindView(R.id.tv_viola_qualify)
    TextView tv_viola_qualify;
    @BindView(R.id.tv_viola_oil)
    TextView tv_viola_oil;
    @BindView(R.id.tv_viola_time)
    TextView tv_viola_time;
    @BindView(R.id.tv_viola_peroil_name)
    TextView tv_viola_peroil_name;
    @BindView(R.id.tv_viola_peroil_tel)
    TextView tv_viola_peroil_tel;
    @BindView(R.id.tv_viola_content)
    TextView tv_viola_content;
    @BindView(R.id.tv_viola_statue)
    TextView tv_viola_statue;
    @BindView(R.id.tv_viola_statue_oil)
    TextView tv_viola_statue_oil;
    @BindView(R.id.tv_viola_statue_time)
    TextView tv_viola_statue_time;
    @BindView(R.id.tv_viola_statue_name)
    TextView tv_viola_statue_name;
    @BindView(R.id.tv_viola_statue_tel)
    TextView tv_viola_statue_tel;
    @BindView(R.id.layout_viola)
    LinearLayout layout_viola;
    @BindView(R.id.layout_statue)
    LinearLayout layout_statue;
    @BindView(R.id.rv_viola)
    RecyclerView rv_viola;
    @BindView(R.id.layout_viola_info)
    LinearLayout layout_viola_info;
    @BindView(R.id.layout_error)
    LinearLayout layout_error;
    @BindView(R.id.tv_error)
    TextView tv_error;
    private String gid = "", plate = "";
    //    private SecurityBean bean = new SecurityBean();
    private ViolaReguInfoBean vrBean = new ViolaReguInfoBean();
    private List<Integer> list_imgUrls = new ArrayList<>();
    private ViolaReformAdapter adapter;
    private List<ViolaReformBean> list_reform = new ArrayList<>();
    private DialogUtils dialogUtils;
    private String strStatue = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_viola_regu_info;
    }

    @Override
    public void initView() {
        dialogUtils = new DialogUtils();
        gid = getIntent().getStringExtra("0");
        plate = getIntent().getStringExtra("1");
        setTopTitle(plate);
        ToolUtils.createRecycleManager(context, rv_viola);
        rv_viola.setNestedScrollingEnabled(false);
        if (SPUtils.getRoleType(this).equals(Constant.ROLE_OIL_WAREHOUSE) ||
                SPUtils.getRoleType(this).equals(Constant.ROLE_CARRIER)) {
            setRightMsg("", R.drawable.ic_inspec_edit);
        }
        setRightClickListener(new OnRightClickListen() {
            @Override
            public void onRightClick(View view) {
                if (SPUtils.getRoleType(context).equals(Constant.ROLE_OIL_WAREHOUSE)) {
                    dialogUtils.createAlertDialog(ViolaReguInfoActivity.this, "提示", "是否确认审核", true,
                            "通过", "不通过");
                    dialogUtils.setOnSureClickListener(new DialogUtils.onSureClickListener() {
                        @Override
                        public void onClick(View view) {
                            //合格
                            commitViola(gid, "30");
                            strStatue = "已合格";
                        }
                    });
                    dialogUtils.setOnCancelClickListener(new DialogUtils.onCancelClickListener() {
                        @Override
                        public void onClick(View view) {
                            //不合格
                            commitViola(gid, "20");
                            strStatue = "未合格";
                        }
                    });
//                    doActivity(CreateViolaActivity.class,gid,plate);
                } else if (SPUtils.getRoleType(context).equals(Constant.ROLE_CARRIER)) {
                    doActivity(ReformActivity.class);
                }
            }
        });
    }

    @Override
    public void initData() {
        queryViolaInfo(gid);
    }

    private void setViewData(ViolaReguInfoBean bean) {
        String imgUrls = bean.getWfgzImgUrl();
        list_imgUrls.clear();
        if (imgUrls.contains(",")) {
            String urls[] = imgUrls.split(",");
//            list_imgUrls = Arrays.asList(urls);
        } else {
            if ("".equals(imgUrls)) {
                list_imgUrls.add(R.mipmap.wg);
            } else {
//                list_imgUrls.add(imgUrls);
            }
        }
        banner_viola.setImageLoader(new BannerImageLoader())
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImages(list_imgUrls)
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();
        banner_viola.setFocusable(true);
        banner_viola.setFocusableInTouchMode(true);
        banner_viola.requestFocus();
        tv_viola_plate.setText(bean.getWfgzPlateNo());
        tv_viola_grade.setText(bean.getWfgzGrade());
        tv_viola_qualify.setText(bean.getWfgzStatus());
        tv_viola_company.setText(bean.getCarrierName());
        tv_viola_content.setText(bean.getWfgzDescribe());
        tv_viola_tel.setText(bean.getCarrierContractTel());
        ToolUtils.setTvUnderLine(this, tv_viola_tel);
        tv_viola_oil.setText(bean.getWfgzCheckPlace());
        tv_viola_time.setText(DateUtils.timeStampToDate(bean.getWfgzCheckTime()));
        tv_viola_peroil_name.setText(bean.getWfgzPCheck());
        tv_viola_peroil_tel.setText(bean.getWfgzPCheckPhone());
        ToolUtils.setTvUnderLine(this, tv_viola_peroil_tel);
        if ("已整改".equals(bean.getWfgzStatus())) {
            layout_statue.setVisibility(View.VISIBLE);
            tv_viola_statue.setText("审核通过");
            tv_viola_statue_name.setText(bean.getWfgzPCheck());
            tv_viola_statue_time.setText(DateUtils.timeStampToDate(bean.getWfgzCheckTime()));
            tv_viola_statue_oil.setText(bean.getWfgzCheckPlace());
            tv_viola_statue_tel.setText(bean.getWfgzPCheckPhone());
            ToolUtils.setTvUnderLine(this, tv_viola_statue_tel);
        } else {
            layout_statue.setVisibility(View.GONE);
        }
    }

    private void setCheckData(List<ViolatCheckBean> list) {
        for (int i = 0; i < list.size(); i++) {
            ViolatCheckBean bean = list.get(i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            final View view = View.inflate(context, R.layout.layout_single_tv, null);
            view.setLayoutParams(params);
            TextView tv = view.findViewById(R.id.item_spinner_tv);
            tv.setText(bean.getRuleName() + "   " + bean.getRuleHaveNo());
            layout_viola.addView(view);
        }
    }

    //查询详情
    private void queryViolaInfo(String gid) {
        ServiceFactory.getService(ApiService.class)
                .querySecurityInfo(gid)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            vrBean = JsonUtils.getViolaRegu(json);
                            list_reform = JsonUtils.getViolaReformList(json);
                            List<ViolatCheckBean> list_check = JsonUtils.getViolaCheckList(json);
                            if (list_check.size() > 0) {
                                setCheckData(list_check);
                            }
                            if (list_reform.size() > 0) {
                                adapter = new ViolaReformAdapter(context, R.layout.item_rv_viola_content, list_reform);
                                rv_viola.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                            setViewData(vrBean);
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

    //油库审核提交
    private void commitViola(String gid, final String statue) {
        ServiceFactory.getService(ApiService.class)
                .oilWareHouseViola(SPUtils.getUserGid(this), gid, statue)
                .compose(SchedulersHelper.<ResponseBody>io_main(context, true,"提交中..."))
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            tv_viola_qualify.setText(strStatue);
                        } else {

                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    private void onFailed(String msg) {
        layout_viola_info.setVisibility(View.GONE);
        layout_error.setVisibility(View.VISIBLE);
        tv_error.setText(msg);
    }
}
