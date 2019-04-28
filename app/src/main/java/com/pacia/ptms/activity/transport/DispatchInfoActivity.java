package com.pacia.ptms.activity.transport;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.DispatchCarAdapter;
import com.pacia.ptms.adapter.DispatchPersonAdapter;
import com.pacia.ptms.bean.CarBasicBean;
import com.pacia.ptms.bean.PersonBean;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
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
 * 发班信息 车辆资质 人员资质
 */
public class DispatchInfoActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener {
    private static final String TAG = "DispatchInfoActivity";
    @BindView(R.id.tv_dispatch_type)
    TextView tv_dispatch_type;
    @BindView(R.id.rv_dispatch_plate)
    RecyclerView rv_dispatch_plate;
    @BindView(R.id.rv_dispatch_person)
    RecyclerView rv_dispatch_person;
    private String dispatch_type = "", dispatch_company = "";
    private String truckNo1 = "", truckNo2 = "", perGid1 = "", perGid2 = "";
    private DispatchCarAdapter adapter_plate;
    private DispatchPersonAdapter adapter_person;
    private List<CarBasicBean> list_plate = new ArrayList<>();
    private List<PersonBean> list_person = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_dispatch_info;
    }

    @Override
    public void initView() {
        dispatch_type = getIntent().getStringExtra("0");
        dispatch_company = getIntent().getStringExtra("1");
        truckNo1 = getIntent().getStringExtra("2");
        truckNo2 = getIntent().getStringExtra("3");
        perGid1 = getIntent().getStringExtra("4");
        perGid2 = getIntent().getStringExtra("5");
        Log.e(TAG, "initView: " + perGid1 + "  " + perGid2);
        setTopTitle("发班信息");
        ToolUtils.createRecycleManager(context, rv_dispatch_plate);
        ToolUtils.createRecycleManager(context, rv_dispatch_person);
        rv_dispatch_person.setNestedScrollingEnabled(false);
        rv_dispatch_plate.setNestedScrollingEnabled(false);
    }

    @Override
    public void initData() {
        tv_dispatch_type.setText(dispatch_type);
        queryDispatchAptitude(truckNo1, truckNo2, perGid1, perGid2);

//        adapter_person.setOnItemChildClickListener(this);

    }

    //查询车辆 人员资质
    private void queryDispatchAptitude(String truckNo1, String truckNo2,
                                       String perGid1, String perGid2) {
        ServiceFactory.getService(ApiService.class)
                .queryDispatchAptitude(truckNo1, truckNo2, perGid1, perGid2)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            List<CarBasicBean> list_truck = JsonUtils.getDispatchCar(json);
                            Log.e(TAG, "onSuccess: " + list_truck);
                            for (int i = 0; i < list_truck.size(); i++) {
                                list_truck.get(i).setCompany_name(dispatch_company);
                                list_plate.add(list_truck.get(i));
                            }
                            adapter_plate = new DispatchCarAdapter(context, R.layout.item_rv_dispatch_aptitude
                                    , list_plate);
                            rv_dispatch_plate.setAdapter(adapter_plate);
                            adapter_plate.notifyDataSetChanged();

                            list_person = JsonUtils.getDispatchPerson(json);
                            adapter_person = new DispatchPersonAdapter(context, R.layout.item_rv_dispatch_aptitude
                                    , list_person);
                            rv_dispatch_person.setAdapter(adapter_person);
                            adapter_person.notifyDataSetChanged();
                        } else {

                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//        PersonBean pb = (PersonBean) adapter.getItem(position);
//        ToolUtils.openPhone(this, pb.getRyMPhone());
    }
}
