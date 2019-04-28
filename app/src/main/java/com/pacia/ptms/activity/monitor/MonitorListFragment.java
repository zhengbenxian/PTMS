package com.pacia.ptms.activity.monitor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseFragment;

/**
 * 主页监控 列表
 * A simple {@link Fragment} subclass.
 */
public class MonitorListFragment extends BaseFragment {
    private static final String TAG = "MonitorListFragment";

    public static Fragment getInstance(String str) {
        MonitorListFragment fragment = new MonitorListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", str);
        fragment.setArguments(bundle);
        return fragment;
    }

    private MonitorFragment monitorFragment;
    private String search_str = "", type = "";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_warning_type;
    }

    @Override
    public void initView() {
        type = getArguments().getString("key");
        monitorFragment = (MonitorFragment) getParentFragment();
    }

    @Override
    public void initData() {

    }

    @Override
    public void lazyLoadData() {
        monitorFragment.setOnEditorActionListener(new MonitorFragment.onEditorActionListener() {
            @Override
            public void onEditAction(String search) {
                search_str = search;
                Log.e(TAG, "lazyLoadData: " + type + "  " + search_str);
            }
        });
    }
}
