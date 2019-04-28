package com.pacia.ptms.carrier.car;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.MainPagerAdapter;
import com.pacia.ptms.bean.CarListBean;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.widget.NoScrollViewPager;

import butterknife.BindView;

/**
 * 车辆详情  main activity
 */
public class CarInfoActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    private int tabName[] = {R.string.basic_msg, R.string.car_monitor, R.string.running_record,
            R.string.security};
    private MainPagerAdapter adapter;
    private Fragment[] fragments = new Fragment[4];
    private CarListBean carBean = new CarListBean();

    @Override
    public int getLayoutId() {
        return R.layout.activity_carrier_info;
    }

    @Override
    public void initView() {
        carBean = (CarListBean) getIntent().getSerializableExtra("bean");
        setTopTitle(carBean.getPlateNo());
        fragments[0] = CarBasicFragment.getInstance(carBean.getPlateNo(), carBean.getPlateNo2());
        fragments[1] = CarOtherFragment.getInstance("monitor", carBean);
        fragments[2] = CarOtherFragment.getInstance("record", carBean);
        fragments[3] = CarOtherFragment.getInstance("security", carBean);
    }

    @Override
    public void initData() {
        ToolUtils.initViewPager(viewPager, tabLayout, getSupportFragmentManager(), fragments);
        ToolUtils.setSubTab(getLayoutInflater(), tabLayout, tabName, null, viewPager);
    }
}
