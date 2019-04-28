package com.pacia.ptms.activity.transport;

import android.view.View;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.carrier.car.CarAptitudeActivity;
import com.pacia.ptms.carrier.person.PersonInfoActivity;
import com.pacia.ptms.utils.DateUtils;
import com.pacia.ptms.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 已入库 进度确认
 */
public class TransWarehouseActivity extends BaseActivity {
    @BindView(R.id.tv_ware_place)
    TextView tv_ware_place;
    @BindView(R.id.tv_ware_name)
    TextView tv_ware_name;
    @BindView(R.id.tv_ware_time)
    TextView tv_ware_time;
    @BindView(R.id.tv_ware_plate)
    TextView tv_ware_plate;
    @BindView(R.id.tv_ware_plate_statue)
    TextView tv_ware_plate_statue;
    @BindView(R.id.tv_ware_driver)
    TextView tv_ware_driver;
    @BindView(R.id.tv_ware_driver_statue)
    TextView tv_ware_driver_statue;
    @BindView(R.id.tv_ware_driver_e)
    TextView tv_ware_driver_e;
    @BindView(R.id.tv_ware_Edriver_statue)
    TextView tv_ware_Edriver_statue;
    private String truckNo1 = "", truckNo2 = "", perGid1 = "", perGid2 = "", perName1 = "",
            perName2 = "", time = "", place = "", isTNo1 = "", isTNo2 = "", isPer1 = "",
            isPer2 = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_trans_warehouse;
    }

    @Override
    public void initView() {
        setTopTitle("进度确认");
        truckNo1 = getIntent().getStringExtra("0");
        truckNo2 = getIntent().getStringExtra("1");
        perGid1 = getIntent().getStringExtra("2");
        perGid2 = getIntent().getStringExtra("3");
        perName1 = getIntent().getStringExtra("4");
        perName2 = getIntent().getStringExtra("5");
        time = getIntent().getStringExtra("6");
        place = getIntent().getStringExtra("7");
        isPer1 = getIntent().getStringExtra("8");
        isPer2 = getIntent().getStringExtra("9");
        isTNo1 = getIntent().getStringExtra("10");
        isTNo2 = getIntent().getStringExtra("11");
    }

    @Override
    public void initData() {
        tv_ware_time.setText(DateUtils.timeStampToDate(time));
        tv_ware_name.setText((String) SPUtils.getUserInfo(this, "user_name", ""));
        tv_ware_place.setText(place);
        String truck = truckNo1 + "\n" + truckNo2;
        tv_ware_plate.setText(truck);
        tv_ware_driver.setText(perName1);
        tv_ware_driver_e.setText(perName2);
        if (isPer1.equals("1")) {
            tv_ware_driver_statue.setText("一致");
        } else {
            tv_ware_driver_statue.setText("不一致");
        }
        if (isPer2.equals("1")) {
            tv_ware_Edriver_statue.setText("一致");
        } else {
            tv_ware_Edriver_statue.setText("不一致");
        }
        if (isTNo1.equals("1") && isTNo2.equals("1")) {
            tv_ware_plate_statue.setText("一致");
        } else {
            tv_ware_plate_statue.setText("不一致");
        }
    }

    @OnClick({R.id.layout_plate, R.id.layout_driver, R.id.layout_driver_e})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.layout_plate:
                doActivity(CarAptitudeActivity.class, truckNo1, truckNo2);
                break;
            case R.id.layout_driver:
                doActivity(PersonInfoActivity.class, perGid1);
                break;
            case R.id.layout_driver_e:
                doActivity(PersonInfoActivity.class, perGid2);
                break;
        }
    }
}
