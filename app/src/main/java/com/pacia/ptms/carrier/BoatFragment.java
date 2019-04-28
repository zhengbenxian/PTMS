package com.pacia.ptms.carrier;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseFragment;

import butterknife.BindView;

/**
 * 预留船舶
 */
public class BoatFragment extends BaseFragment {

    public static Fragment getInstance(String gid) {
        BoatFragment fragment = new BoatFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gid", gid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipLayout;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_empty_data)
    LinearLayout ll_empty_data;
    @BindView(R.id.tv_error)
    TextView tv_error;

    @Override
    public int getLayoutId() {
        return R.layout.layout_swip_recycle;
    }

    @Override
    public void initView() {
        swipLayout.setVisibility(View.GONE);

    }

    @Override
    public void initData() {

    }

    @Override
    public void lazyLoadData() {

    }

}
