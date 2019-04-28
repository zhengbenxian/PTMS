package com.pacia.ptms.carrier.info;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseFragment;
import com.pacia.ptms.adapter.AptitudeAdapter;
import com.pacia.ptms.bean.AptitudeBean;
import com.pacia.ptms.bean.CarrierBean;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.utils.http.BaseObserver;
import com.pacia.ptms.utils.http.SchedulersHelper;
import com.pacia.ptms.utils.http.ServiceFactory;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;

/**
 * 承运商基础信息
 */
public class BasicFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener {
    private static final String TAG = "BasicFragment";

    public static Fragment getInstance(String gid) {
        BasicFragment fragment = new BasicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gid", gid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.tv_company_type)
    TextView tv_company_type;
    @BindView(R.id.tv_legal_person)
    TextView tv_legal_person;
    @BindView(R.id.tv_company_tel)
    TextView tv_company_tel;
    @BindView(R.id.rv_basic_apti)
    RecyclerView rv_basic_apti;
    private AptitudeAdapter adapter;
    private List<AptitudeBean> list_apti = new ArrayList<>();
    private CarrierBean cb = new CarrierBean();
    private String carrierGid = "";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_basic;
    }

    @Override
    public void initView() {
        carrierGid = getArguments().getString("gid");
        ToolUtils.setTvUnderLine((Activity) fContext, tv_company_tel);
        ToolUtils.createRecycleManager(fContext, rv_basic_apti);

        adapter = new AptitudeAdapter(fContext, R.layout.item_rv_basic_apti, list_apti);
        rv_basic_apti.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemChildClickListener(this);
    }

    @Override
    public void initData() {
        queryBasic(carrierGid);
    }

    @Override
    public void lazyLoadData() {

    }

    //查询基本信息
    private void queryBasic(String gid) {
        ServiceFactory.getService(ApiService.class)
                .queryCarrierByPri(gid)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            cb = JsonUtils.getCarrierInfo(json);
                            setViewData(cb);
                            list_apti.clear();
                            list_apti = JsonUtils.getAptitudeList(json);
                            adapter.addData(list_apti);
                            adapter.notifyDataSetChanged();
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void setViewData(CarrierBean cb) {
        tv_company_name.setText(cb.getCysName());
        tv_company_tel.setText(cb.getCysContractTel());
        tv_company_type.setText(cb.getCysType());
        tv_legal_person.setText(cb.getCysContract());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        AptitudeBean bean = (AptitudeBean) adapter.getItem(position);

    }
}
