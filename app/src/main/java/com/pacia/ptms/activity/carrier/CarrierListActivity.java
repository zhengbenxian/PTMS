package com.pacia.ptms.activity.carrier;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.widget.NoScrollViewPager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 承运管理 主界面
 */
public class CarrierListActivity extends BaseActivity {
    private static final String TAG = "CarrierListActivity";
    @BindView(R.id.toolBar_sub)
    Toolbar toolBar_sub;
    @BindView(R.id.tv_sub_top_back)
    TextView tv_sub_top_back;
    @BindView(R.id.et_top_search)
    EditText et_top_search;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;

    private int tabName[] = {R.string.all_type, R.string.road_trans, R.string.water_trans};
    private int tabIcons[] = {R.mipmap.carrier_all, R.mipmap.carrier_road, R.mipmap.carrier_water};
    private Fragment[] fragments = new Fragment[3];
    private onEditorActionListener onEditorActionListener;

    @Override
    public int getLayoutId() {
        return R.layout.activity_carrier_info;
    }

    @Override
    public void initView() {
//        setToolBarVisibility(View.GONE);
//        toolBar_sub.setPadding(0, ToolUtils.getStatusBarHeight(context),
//                0, 15);
//        toolBar_sub.setVisibility(View.VISIBLE);
//        tv_sub_top_back.setVisibility(View.VISIBLE);
        setTopTitle("承运商");
        fragments[0] = CarrierListFragment.getInstance(Constant.TRANS_ALL);
        fragments[1] = CarrierListFragment.getInstance(Constant.TRANS_ROAD);
        fragments[2] = CarrierListFragment.getInstance(Constant.TRANS_WATER);
    }

    @Override
    public void initData() {
        ToolUtils.initViewPager(viewPager, tabLayout, getSupportFragmentManager(), fragments);
        ToolUtils.setSubTab(getLayoutInflater(), tabLayout, tabName, tabIcons, viewPager);
        et_top_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = v.getText().toString();
                    if (null != onEditorActionListener)
                        onEditorActionListener.onEditAction(search);
                    ToolUtils.closeSoft((Activity) context);
                }
                return false;
            }
        });
    }

    @OnClick({R.id.tv_sub_top_back})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sub_top_back:
                finish();
                break;
        }
    }

    public interface onEditorActionListener {
        void onEditAction(String search);
    }

    public void setOnEditorActionListener(onEditorActionListener onEditorActionListener) {
        if (null != onEditorActionListener) {
            this.onEditorActionListener = onEditorActionListener;
        }
    }
}
