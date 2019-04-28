package com.pacia.ptms.carrier.person;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.AptitudeAdapter;
import com.pacia.ptms.bean.AptitudeBean;
import com.pacia.ptms.bean.PersonBean;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.ToastUtils;
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
 * 人员详细信息
 */
public class PersonInfoActivity extends BaseActivity {
    @BindView(R.id.img_p_icon)
    ImageView img_p_icon;
    @BindView(R.id.tv_p_name)
    TextView tv_p_name;
    @BindView(R.id.tv_p_tel)
    TextView tv_p_tel;
    @BindView(R.id.tv_p_staff)
    TextView tv_p_staff;
    @BindView(R.id.tv_p_company)
    TextView tv_p_company;
    @BindView(R.id.tv_p_test)
    TextView tv_p_test;
    @BindView(R.id.rv_p_aptitude)
    RecyclerView rv_p_aptitude;

    private String gid;
    private PersonBean personBean = new PersonBean();
    private AptitudeAdapter adapter;
    private List<AptitudeBean> list_apti = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_person_info;
    }

    @Override
    public void initView() {
        gid = getIntent().getStringExtra("0");
        ToolUtils.createRecycleManager(this, rv_p_aptitude);
        adapter = new AptitudeAdapter(context, R.layout.item_rv_basic_apti, list_apti);
        rv_p_aptitude.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initData() {
        queryPersonInfo(gid);
    }

//    @OnClick({R.id.tv_p_tel})
//    public void onViewClick(View view) {
//        switch (view.getId()) {
//            case R.id.tv_p_tel:
//                ToolUtils.openPhone(this, tv_p_tel.getText().toString());
//                break;
//        }
//    }

    //查询人员详细信息
    private void queryPersonInfo(String gid) {
        ServiceFactory.getService(ApiService.class)
                .queryPersonInfoByGid(gid)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            List<PersonBean> pb = JsonUtils.getPersonInfo(json);
                            if (pb.size() > 0) {
                                personBean = pb.get(0);
                                setPersonData(personBean);
                                adapter.addData(getPersonIdCard(personBean));
                                list_apti = JsonUtils.getPersonAptitudeList(json);
                                adapter.addData(list_apti);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            ToastUtils.showShort("访问失败");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        ToastUtils.showShort(error);
                    }
                });
    }

    //设置人员信息
    private void setPersonData(PersonBean pb) {
        setTopTitle(pb.getRyName());
        tv_p_name.setText(pb.getRyName());
        tv_p_tel.setText(pb.getRyMPhone());
        ToolUtils.setTvUnderLine(this,tv_p_tel);
        tv_p_staff.setText(pb.getRyStaffType());
        tv_p_test.setText("合格");
        tv_p_company.setText(pb.getRyHName());
    }

    //把人员身份信息设置为资质信息
    private AptitudeBean getPersonIdCard(PersonBean pb) {
        AptitudeBean ab = new AptitudeBean();
        ab.setZzName("身份证号码");
        ab.setZzCertid(pb.getRyIdCardNum());
        ab.setCertSDate(pb.getRyQValidSTime());
        ab.setZzCertEDate(pb.getRyQValidETime());
        ab.setNativeUrl(R.mipmap.ry);
        return ab;
    }
}
