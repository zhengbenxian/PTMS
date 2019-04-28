package com.pacia.ptms.activity.statistics;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.widget.NoScrollViewPager;

import butterknife.BindView;

/**
 * 安全统计主页
 */
public class StatisticsActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    private int tabName[] = {R.string.carrier, R.string.trans_tool};
    private Fragment[] fragments = new Fragment[2];

    @Override
    public int getLayoutId() {
        return R.layout.activity_carrier_info;
    }

    @Override
    public void initView() {
        setTopTitle(getString(R.string.safety_statistic));
        fragments[0] = StatisticsCarrierFragment.getInstance("");
        fragments[1] = StatisticsToolFragment.getInstance("");
    }

    @Override
    public void initData() {
        ToolUtils.initViewPager(viewPager, tabLayout, getSupportFragmentManager(), fragments);
        ToolUtils.setSubTab(getLayoutInflater(), tabLayout, tabName, null, viewPager);
    }
}
