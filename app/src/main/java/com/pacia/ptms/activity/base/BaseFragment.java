package com.pacia.ptms.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacia.ptms.utils.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public abstract class BaseFragment extends RxFragment {
    private View view;
    public Context fContext;
    private Unbinder unbinder;
    private boolean isVisity;
    private boolean isPreper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        fContext = getActivity();
        isPreper = true;
        initView();
        initData();
        lazyData();
        return view;
    }

    private void lazyData() {
        if (!isVisity || !isPreper) return;
        lazyLoadData();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisity = true;
            lazyData();
        } else {
            isVisity = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            isVisity = false;
        } else {
            isVisity = true;
            lazyData();
        }
    }

    public void onSessionError(String msg) {
        if (msg.toLowerCase().contains("session") &&
                msg.toLowerCase().contains("-2017")) {
//            doActivity(LoginActivity.class);
            getActivity().finish();
        }
        ToastUtils.showShort(msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != unbinder)
            unbinder.unbind();
    }

    public void doActivity(Class<?> activity) {
        Intent intent = new Intent(fContext, activity);
        fContext.startActivity(intent);
    }

    public void doActivity(Class<?> activity, String... str) {
        Intent intent = new Intent(fContext, activity);
        for (int i = 0; i < str.length; i++) {
            intent.putExtra(String.valueOf(i), str[i]);
        }
        fContext.startActivity(intent);
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    public abstract void lazyLoadData();
}
