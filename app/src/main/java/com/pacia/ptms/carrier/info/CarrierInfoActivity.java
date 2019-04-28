package com.pacia.ptms.carrier.info;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.MainPagerAdapter;
import com.pacia.ptms.carrier.BoatFragment;
import com.pacia.ptms.carrier.car.CarListFragment;
import com.pacia.ptms.carrier.person.PersonListFragment;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.widget.NoScrollViewPager;

import butterknife.BindView;

/**
 * 承运商详细信息 main
 */
public class CarrierInfoActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    private int tabName[] = {R.string.basic_msg, R.string.person_msg,
            R.string.car_msg, R.string.boat_msg};
    private MainPagerAdapter adapter;
    private Fragment[] fragments = new Fragment[4];
    private String carrierGid = "", carrierName = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_carrier_info;
    }

    @Override
    public void initView() {
        carrierGid = getIntent().getStringExtra("gid");
        carrierName = getIntent().getStringExtra("name");
        setTopTitle(carrierName);
        fragments[0] = BasicFragment.getInstance(carrierGid);
        fragments[1] = PersonListFragment.getInstance(carrierGid, carrierName);
        fragments[2] = CarListFragment.getInstance(carrierGid);
        fragments[3] = BoatFragment.getInstance(carrierGid);
    }

    @Override
    public void initData() {
        ToolUtils.initViewPager(viewPager, tabLayout, getSupportFragmentManager(), fragments);
        ToolUtils.setSubTab(getLayoutInflater(), tabLayout, tabName, null, viewPager);
    }
}
