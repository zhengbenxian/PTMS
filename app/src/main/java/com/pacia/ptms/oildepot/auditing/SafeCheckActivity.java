package com.pacia.ptms.oildepot.auditing;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.SafeCheckAdapter;
import com.pacia.ptms.bean.SafeCheckBean;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.DialogUtils;
import com.pacia.ptms.utils.JsonUtils;
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
 * 油库安全检查
 */
public class SafeCheckActivity extends BaseActivity {
    private static final String TAG = "SafeCheckActivity";
    @BindView(R.id.include_card)
    LinearLayout include_card;
    @BindView(R.id.include_car)
    LinearLayout include_car;
    @BindView(R.id.include_goods)
    LinearLayout include_goods;
    @BindView(R.id.include_person)
    LinearLayout include_person;
    private List<SafeCheckBean> list_card = new ArrayList<>(),
            list_car = new ArrayList<>(), list_goods = new ArrayList<>(),
            list_person = new ArrayList<>();
    private TextView tv_card, tv_car, tv_goods, tv_person;
    private RecyclerView rv_card, rv_car, rv_goods, rv_person;
    private SafeCheckAdapter adapter_card, adapter_car, adapter_goods, adapter_person;
    private SparseArray<String> choseCard = new SparseArray<>(), choseCar = new SparseArray<>(),
            choseGoods = new SparseArray<>(), chosePerson = new SparseArray<>();
    private String plate = "", gid = "";
    private DialogUtils dialogUtils;

    //0 ：有   1 无
    @Override
    public int getLayoutId() {
        return R.layout.activity_safe_check;
    }

    @Override
    public void initView() {
        findById();
        initViewData();
        dialogUtils = new DialogUtils();
        plate = getIntent().getStringExtra("plate");
        gid = getIntent().getStringExtra("gid");
        setTopTitle(plate);
    }

    @Override
    public void initData() {
        queryCheckTypeList();
    }

    @OnClick({R.id.tv_safe_check_no, R.id.tv_safe_check})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_safe_check_no:
                dialogUtils.createAlertDialog(this, "提示", "", false,
                        "写违规单", "重新检查");
                dialogUtils.setOnSureClickListener(new DialogUtils.onSureClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCheckComplete("不通过");
                        doActivity(CreateViolaActivity.class, gid, plate);
                    }
                });
                break;
            case R.id.tv_safe_check:
                onCheckComplete("通过");
                break;
        }
    }

    private void onCheckComplete(String statue) {
        Intent intent = new Intent();
        intent.putExtra("statue", statue);
        setResult(10001, intent);
        finish();
    }

    private void queryCheckTypeList() {
        ServiceFactory.getService(ApiService.class)
                .querySafeCheckType()
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            List<SafeCheckBean> list = JsonUtils.getSafeCheckList(json);
                            list_card = getSafeTypeList(list, "10", choseCard);
                            list_car = getSafeTypeList(list, "30", choseCar);
                            list_goods = getSafeTypeList(list, "20", choseGoods);
                            list_person = getSafeTypeList(list, "40", chosePerson);

                            initAdapter(rv_card, list_card, choseCard);
                            initAdapter(rv_car, list_car, choseCar);
                            initAdapter(rv_goods, list_goods, choseGoods);
                            initAdapter(rv_person, list_person, chosePerson);
                        } else {

                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    private List<SafeCheckBean> getSafeTypeList(List<SafeCheckBean> list, String type, SparseArray<String> choseArray) {
        List<SafeCheckBean> list_safe = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SafeCheckBean bean = list.get(i);
            if (bean.getJcgzCategory().equals(type)) {
                list_safe.add(bean);
                if (bean.getJcgzHaveNo().equals("有")) {
                    choseArray.put(i, "有");
                } else {
                    choseArray.put(i, "无");
                }
            }
        }
        return list_safe;
    }

    private void findById() {
        tv_card = include_card.findViewById(R.id.tv_check_title);
        tv_car = include_car.findViewById(R.id.tv_check_title);
        tv_person = include_person.findViewById(R.id.tv_check_title);
        tv_goods = include_goods.findViewById(R.id.tv_check_title);
        rv_card = include_card.findViewById(R.id.rv_check);
        rv_car = include_car.findViewById(R.id.rv_check);
        rv_person = include_person.findViewById(R.id.rv_check);
        rv_goods = include_goods.findViewById(R.id.rv_check);
    }

    private void initViewData() {
        ToolUtils.createRecycleManager(this, rv_card);
        ToolUtils.createRecycleManager(this, rv_car);
        ToolUtils.createRecycleManager(this, rv_goods);
        ToolUtils.createRecycleManager(this, rv_person);
        rv_card.setNestedScrollingEnabled(false);
        rv_car.setNestedScrollingEnabled(false);
        rv_goods.setNestedScrollingEnabled(false);
        rv_person.setNestedScrollingEnabled(false);
        tv_card.setText("证照");
        tv_car.setText("车辆");
        tv_goods.setText("入库违禁物品");
        tv_person.setText("人员");
    }

    private void initAdapter(RecyclerView rv, List<SafeCheckBean> list,
                             SparseArray<String> choseArray) {
        SafeCheckAdapter adapter = new SafeCheckAdapter(
                this, R.layout.item_rv_safe_check, list, choseArray);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
