package com.pacia.ptms.carrier.person;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseFragment;
import com.pacia.ptms.adapter.PersonHeadAdapter;
import com.pacia.ptms.bean.PersonBean;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.http.BaseObserver;
import com.pacia.ptms.utils.http.SchedulersHelper;
import com.pacia.ptms.utils.http.ServiceFactory;
import com.pacia.ptms.widget.MyGridView;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * 承运商人员信息列表
 */
public class PersonListFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "PersonListFragment";

    public static Fragment getInstance(String gid, String name) {
        PersonListFragment fragment = new PersonListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gid", gid);
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.gv_driver)
    MyGridView gv_driver;
    @BindView(R.id.gv_driver_escort)
    MyGridView gv_d_escort;
    @BindView(R.id.tv_driver_detail)
    TextView tv_driver_detail;
    @BindView(R.id.tv_d_escort_detail)
    TextView tv_d_escort_detail;
    @BindView(R.id.layout_person_list)
    LinearLayout layout_person_list;
    @BindView(R.id.ll_empty_data)
    LinearLayout ll_empty_data;
    @BindView(R.id.tv_error)
    TextView tv_error;
    private PersonHeadAdapter adapter_driver, adapter_d_escort;
    private List<PersonBean> list_driver = new ArrayList<>();
    private List<PersonBean> list_d_escort = new ArrayList<>();
    private String carrierGid = "", carrierName = "";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_person_list;
    }

    @Override
    public void initView() {
        carrierGid = getArguments().getString("gid");
        carrierName = getArguments().getString("name");
    }

    @Override
    public void initData() {
        gv_driver.setOnItemClickListener(this);
        gv_d_escort.setOnItemClickListener(this);
    }

    @Override
    public void lazyLoadData() {
        queryPersonListByGid(carrierGid);
    }

    @OnClick({R.id.tv_driver_detail, R.id.tv_d_escort_detail})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_driver_detail:
                if (list_driver.size() > 0) {
                    Intent i1 = new Intent(fContext, PersonListActivity.class);
                    i1.putExtra("type", Constant.PERSON_DRIVER);
                    i1.putExtra("title", carrierName);
                    i1.putExtra("list", (Serializable) list_driver);
                    startActivity(i1);
                }
                break;
            case R.id.tv_d_escort_detail:
                if (list_d_escort.size() > 0) {
                    Intent i2 = new Intent(fContext, PersonListActivity.class);
                    i2.putExtra("type", Constant.PERSON_D_ESCORT);
                    i2.putExtra("title", carrierName);
                    i2.putExtra("list", (Serializable) list_d_escort);
                    startActivity(i2);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.gv_driver:
                PersonBean pb = (PersonBean) parent.getItemAtPosition(position);
                doActivity(PersonInfoActivity.class, pb.getGid());
                break;
            case R.id.gv_driver_escort:
                PersonBean pb_e = (PersonBean) parent.getItemAtPosition(position);
                doActivity(PersonInfoActivity.class, pb_e.getGid());
                break;
        }
    }

    //查询人员列表
    private void queryPersonListByGid(String gid) {
        ServiceFactory.getService(ApiService.class)
                .queryPersonListByPri(gid)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            list_driver = JsonUtils.getPersonList(json, "bSjList");
                            if (list_driver.size() > 0) {
                                adapter_driver = new PersonHeadAdapter(fContext, list_driver,
                                        false, 8);
                                gv_driver.setAdapter(adapter_driver);
                                adapter_driver.notifyDataSetChanged();
                            } else {
                                gv_driver.setVisibility(View.GONE);
                            }
                            list_d_escort = JsonUtils.getPersonList(json, "bSyList");
                            if (list_d_escort.size() > 0) {
                                adapter_d_escort = new PersonHeadAdapter(fContext, list_d_escort,
                                        false, 8);
                                gv_d_escort.setAdapter(adapter_d_escort);
                                adapter_d_escort.notifyDataSetChanged();
                            } else {
                                gv_d_escort.setVisibility(View.GONE);
                            }
                        } else {
                            onFailed("查询人员为空");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        onFailed(error);
                    }
                });
    }

    private void onFailed(String msg) {
        layout_person_list.setVisibility(View.GONE);
        ll_empty_data.setVisibility(View.VISIBLE);
        tv_error.setText(msg);
    }
}
