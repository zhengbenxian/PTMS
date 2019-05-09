package com.pacia.ptms.carrier.reform;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.ChosePersonAdapter;
import com.pacia.ptms.bean.PersonBean;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.SPUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.utils.http.BaseObserver;
import com.pacia.ptms.utils.http.SchedulersHelper;
import com.pacia.ptms.utils.http.ServiceFactory;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;

/**
 * 人员选择
 */
public class ChosePersonActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener,
        TextWatcher {
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipLayout;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_empty_data)
    LinearLayout ll_empty_data;
    @BindView(R.id.tv_error)
    TextView tv_error;
    @BindView(R.id.et_search_name)
    EditText et_search_name;
    private String type = "";
    private List<PersonBean> list_driver = new ArrayList<>(),
            list_d_escort = new ArrayList<>();
    private ChosePersonAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_chose_person;
    }

    @Override
    public void initView() {
        ToolUtils.createSwipeLayout(this, swipLayout);
        ToolUtils.createRecycleManager(this, recyclerView);
        swipLayout.setRefreshing(false);
        type = getIntent().getStringExtra("type");
        if ("driver".equals(type)) {
            setTopTitle("司机");
        } else {
            setTopTitle("押运员");
        }
        adapter = new ChosePersonAdapter(context, R.layout.item_rv_chose_person,
                new ArrayList<PersonBean>());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();
        et_search_name.addTextChangedListener(this);
    }

    @Override
    public void initData() {
        queryPersonListByGid(SPUtils.getUserGid(this));
    }

    //查询人员列表
    private void queryPersonListByGid(String gid) {
        ServiceFactory.getService(ApiService.class)
                .queryPersonListByPri(gid)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            if ("driver".equals(type)) {
                                list_driver = JsonUtils.getPersonList(json, "bSjList");
                                if (list_driver.size() > 0) {
                                    adapter.addData(list_driver);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    onFailed("查询人员为空");
                                }
                            } else if ("edriver".equals(type)) {
                                list_d_escort = JsonUtils.getPersonList(json, "bSyList");
                                if (list_d_escort.size() > 0) {
                                    adapter.addData(list_d_escort);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    onFailed("查询人员为空");
                                }
                            }
                        } else {
                            onFailed("查询人员为空");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        onFailed(error);
                    }
                });
    }

    private void onFailed(String msg) {
        swipLayout.setVisibility(View.GONE);
        tv_error.setText(msg);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PersonBean pb = (PersonBean) adapter.getItem(position);
        Intent intent = new Intent();
        intent.putExtra("gid", pb.getGid());
        intent.putExtra("name", pb.getName());
        setResult(11111, intent);
        finish();
    }

    private void onSearch(String searchName) {
        adapter.getData().clear();
        if ("driver".equals(type)) {
            if ("".equals(searchName)) {
                adapter.addData(list_driver);
            } else {
                List<PersonBean> list = new ArrayList<>();
                for (int i = 0; i < list_driver.size(); i++) {
                    if (list_driver.get(i).getName().contains(searchName)) {
                        list.add(list_driver.get(i));
                    }
                }
                adapter.addData(list);
            }
            adapter.notifyDataSetChanged();
        } else if ("edriver".equals(type)) {
            if ("".equals(searchName)) {
                adapter.addData(list_d_escort);
            } else {
                List<PersonBean> list = new ArrayList<>();
                for (int i = 0; i < list_d_escort.size(); i++) {
                    if (list_d_escort.get(i).getName().contains(searchName)) {
                        list.add(list_d_escort.get(i));
                    }
                }
                adapter.addData(list);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        onSearch(s.toString().trim());
    }
}
