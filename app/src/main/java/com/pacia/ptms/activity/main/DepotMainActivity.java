package com.pacia.ptms.activity.main;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.activity.center.CenterFragment;
import com.pacia.ptms.activity.monitor.MonitorFragment;
import com.pacia.ptms.activity.warning.WarningMFragment;
import com.pacia.ptms.adapter.MainPagerAdapter;
import com.pacia.ptms.oildepot.auditing.AuditingActivity;
import com.pacia.ptms.utils.ToastUtils;
import com.pacia.ptms.widget.NoScrollViewPager;

import butterknife.BindView;

/**
 * 油库首页
 */
public class DepotMainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener {
    @BindView(R.id.main_viewPager)
    NoScrollViewPager main_viewPager;
    @BindView(R.id.rg_tab)
    RadioGroup rg_tab;
    @BindView(R.id.rb_main)
    RadioButton rb_main;
    @BindView(R.id.rb_monitor)
    RadioButton rb_monitor;
    @BindView(R.id.rb_warning)
    RadioButton rb_warning;
    @BindView(R.id.rb_my)
    RadioButton rb_my;
    @BindView(R.id.layout_main_check)
    RelativeLayout layout_main_check;
    private MainPagerAdapter adapter;
    private Fragment[] fragments = new Fragment[4];
    //退出计时器
    private long time = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_depot;
    }

    @Override
    public void initView() {
        setToolBarVisibility(View.GONE);
        initFragments();
        initViewPager(main_viewPager);
    }

    @Override
    public void initData() {
        rg_tab.setOnCheckedChangeListener(this);
        rb_main.setChecked(true);
        setListener();
    }

    //初始化fragment
    private void initFragments() {
        setToolBarVisibility(View.GONE);
        fragments[0] = MainFragment.getInstance("0");
        fragments[1] = MonitorFragment.getInstance("1");
        fragments[2] = WarningMFragment.getInstance("2");
        fragments[3] = CenterFragment.getInstance("3");
    }

    private void initViewPager(NoScrollViewPager viewPager) {
        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setScroll(false);
    }


    //监听
    private void setListener() {
        setOnBackClickListener(new OnBackClickListen() {
            @Override
            public void onBackClick(View view) {

            }
        });
        layout_main_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doActivity(AuditingActivity.class);
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                rb_main.setChecked(true);
                break;
            case 1:
                rb_monitor.setChecked(true);
                break;
            case 2:
                rb_warning.setChecked(true);
                break;
            case 3:
                rb_my.setChecked(true);
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_main:
                setToolBarVisibility(View.GONE);
                main_viewPager.setCurrentItem(0);
                break;
            case R.id.rb_monitor:
                setToolBarVisibility(View.GONE);
                main_viewPager.setCurrentItem(1);
                break;
            case R.id.rb_warning:
                main_viewPager.setCurrentItem(2);
                setToolBarVisibility(View.VISIBLE);
                setBackVisibility(View.GONE);
                setTopTitle(getString(R.string.warning));
                break;
            case R.id.rb_my:
                setToolBarVisibility(View.GONE);
                main_viewPager.setCurrentItem(3);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > 2000) {
                ToastUtils.showShort("再按一次退出程序");
                time = System.currentTimeMillis();
            } else {
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
