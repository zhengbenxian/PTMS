package com.pacia.ptms.activity.warning;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseFragment;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.widget.NoScrollViewPager;

import butterknife.BindView;

/**
 * 预警 main fragment
 */
public class WarningMFragment extends BaseFragment {
    private static final String TAG = "WarningMFragment";

    public static Fragment getInstance(String str) {
        WarningMFragment fragment = new WarningMFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", str);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.toolBar_sub)
    Toolbar toolBar_sub;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R.id.et_search)
    EditText et_search;
    private int tabName[] = {R.string.all_type, R.string.illegal_boarding,
            R.string.route_deviation, R.string.overtime_parking};
    private Fragment[] fragments = new Fragment[4];
    private onEditorActionListener onEditorActionListener;

    @Override
    public int getLayoutId() {
        return R.layout.activity_carrier_info;
    }

    @Override
    public void initView() {
        et_search.setVisibility(View.GONE);
        toolBar_sub.setPadding(0, ToolUtils.getStatusBarHeight(fContext),
                0, 15);
        fragments[0] = WarningTypeFragment.getInstance("");
        fragments[1] = WarningTypeFragment.getInstance("10");
        fragments[2] = WarningTypeFragment.getInstance("20");
        fragments[3] = WarningTypeFragment.getInstance("30");
    }

    @Override
    public void initData() {
        ToolUtils.initViewPager(viewPager, tabLayout, getChildFragmentManager(), fragments);
        ToolUtils.setSubTab(getLayoutInflater(), tabLayout, tabName, null, viewPager);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = v.getText().toString();
                    if (null != onEditorActionListener)
                        onEditorActionListener.onEditAction(search);
                    ToolUtils.closeSoft((Activity) fContext);
                }
                return false;
            }
        });
    }

    @Override
    public void lazyLoadData() {

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
