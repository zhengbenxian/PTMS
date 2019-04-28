package com.pacia.ptms.activity.main;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.activity.center.CenterFragment;
import com.pacia.ptms.activity.monitor.MonitorFragment;
import com.pacia.ptms.activity.warning.WarningMFragment;
import com.pacia.ptms.adapter.MainPagerAdapter;
import com.pacia.ptms.utils.ToastUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.widget.NoScrollViewPager;

import butterknife.BindView;

/**
 * 首页activity
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.viewpager_main)
    NoScrollViewPager viewpager_main;
    @BindView(R.id.tabLayout_main)
    TabLayout tabLayout_main;

    private MainPagerAdapter adapter;
    private int TAB_TITLE[] = {R.string.main_page, R.string.monitor, R.string.warning, R.string.my};
    private int TAB_IMG[] = {R.drawable.tab_main_selector,
            R.drawable.tab_monitor_selector,
            R.drawable.tab_warning_selector,
            R.drawable.tab_my_selector
    };
    private Fragment[] fragments = new Fragment[4];
    //退出计时器
    private long time = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initFragments();
        ToolUtils.initViewPager(viewpager_main, tabLayout_main, getSupportFragmentManager(), fragments);
        initTab(tabLayout_main);
    }

    @Override
    public void initData() {
        setListener();
    }

    //初始化fragment
    private void initFragments() {
        setToolBarVisibility(View.GONE);
//        getToolBar().setPadding(0, ToolUtils.getStatusBarHeight(context) / 2,
//                0, 0);
        fragments[0] = MainFragment.getInstance("0");
        fragments[1] = MonitorFragment.getInstance("1");
        fragments[2] = WarningMFragment.getInstance("2");
        fragments[3] = CenterFragment.getInstance("3");
    }

    //底部tab 初始化
    private void initTab(TabLayout tabLayout) {
        ToolUtils.setMainTab(getLayoutInflater(), tabLayout, TAB_TITLE, TAB_IMG, viewpager_main);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setToolBarVisibility(View.GONE);
                        break;
                    case 1:
                        setToolBarVisibility(View.GONE);
//                        setBackVisibility(View.GONE);
//                        setTopTitle(getString(R.string.monitor));
                        break;
                    case 2:
                        setToolBarVisibility(View.VISIBLE);
                        setBackVisibility(View.GONE);
                        setTopTitle(getString(R.string.warning));
                        break;
                    case 3:
                        setToolBarVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //监听
    private void setListener() {
        setOnBackClickListener(new OnBackClickListen() {
            @Override
            public void onBackClick(View view) {

            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
            super.onActivityResult(requestCode, resultCode, data);
    }
}
