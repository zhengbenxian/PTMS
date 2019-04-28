package com.pacia.ptms.activity.warning;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseFragment;
import com.pacia.ptms.adapter.WarningAdapter;
import com.pacia.ptms.bean.WarningBean;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.utils.http.BaseObserver;
import com.pacia.ptms.utils.http.SchedulersHelper;
import com.pacia.ptms.utils.http.ServiceFactory;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;

/**
 * 预警各个类型
 * A simple {@link Fragment} subclass.
 */
public class WarningTypeFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.RequestLoadMoreListener {
    private static final String TAG = "WarningTypeFragment";

    public static Fragment getInstance(String str) {
        WarningTypeFragment fragment = new WarningTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", str);
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
    private WarningMFragment warningMFragment;
    private String search_str = "", type = "";
    //当前页  行数  总页数
    private int page = 1, rows = 10, pageCount = 1;
    private boolean isRefresh = false;
    private List<WarningBean> list_warning = new ArrayList<>();
    private WarningAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.layout_swip_recycle;
    }

    @Override
    public void initView() {
        ToolUtils.createSwipeLayout(fContext, swipLayout);
        ToolUtils.createRecycleManager(fContext, recyclerView);
        type = getArguments().getString("key");
        warningMFragment = (WarningMFragment) getParentFragment();
    }

    @Override
    public void initData() {
        warningMFragment.setOnEditorActionListener(new WarningMFragment.onEditorActionListener() {
            @Override
            public void onEditAction(String search) {
                search_str = search;
            }
        });
        adapter = new WarningAdapter(fContext, R.layout.item_rv_warning_list, list_warning);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        swipLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page = 1;
                queryWarningList(type, page, rows);
            }
        });
    }

    @Override
    public void lazyLoadData() {
        isRefresh = true;
        page = 1;
        if ("".equals(type)) {
            queryWarningList(type, page, rows);
        } else if ("10".equals(type)) {
            queryWarningList(type, page, rows);
        } else if ("20".equals(type)) {
            queryWarningList(type, page, rows);
        } else if ("30".equals(type)) {
            queryWarningList(type, page, rows);
        }
    }

    private void queryWarningList(String type, int page, int rows) {
        ServiceFactory.getService(ApiService.class)
                .queryWarningList(type, page, rows)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            List<WarningBean> list = JsonUtils.getWarningList(json);
                            int total = JsonUtils.getTotal(json);
                            showWarningList(list, total);
                        } else {
                            onFailed("查询失败");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        onFailed(error);
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        WarningBean bean = (WarningBean) adapter.getItem(position);
        if (bean.getWarningType().equals(Constant.WARNING_ILLEGAL)) {
            doActivity(IllegalBoardActivity.class, bean.getGid());
        } else if (bean.getWarningType().equals(Constant.WARNING_ROTE)) {
            doActivity(WarningRoteActivity.class, "rote", bean.getGid());
        } else if (bean.getWarningType().equals(Constant.WARNING_OVERTIME)) {
            doActivity(WarningRoteActivity.class, "overtime", bean.getGid());
        }
    }

    private void showWarningList(List<WarningBean> list, int total) {
        adapter.removeAllFooterView();
        swipLayout.setVisibility(View.VISIBLE);
        //下拉加载更多
        adapter.setOnLoadMoreListener(this, recyclerView);
        pageCount = (total + rows - 1) / rows;
        if (isRefresh) {
            adapter.getData().clear();
        }
        adapter.addData(list);
        adapter.notifyDataSetChanged();
        adapter.loadMoreComplete();
        swipLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreRequested() {
        swipLayout.setRefreshing(false);
        if (page < pageCount) {
            page++;
            queryWarningList(type, page, rows);
        } else {
            adapter.setEnableLoadMore(false);
            adapter.loadMoreComplete();
            View view = getLayoutInflater().inflate(R.layout.foot_view, null);
            adapter.addFooterView(view);
        }
        isRefresh = false;
    }

    private void onFailed(String msg) {
        adapter.getData().clear();
        adapter.notifyDataSetChanged();
        swipLayout.setVisibility(View.GONE);
        swipLayout.setRefreshing(false);
        tv_error.setText(msg);
    }
}
