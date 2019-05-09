package com.pacia.ptms.activity.center;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseFragment;
import com.pacia.ptms.activity.login.LoginActivity;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.DateUtils;
import com.pacia.ptms.utils.DialogUtils;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.SPUtils;
import com.pacia.ptms.utils.ToastUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.utils.http.BaseObserver;
import com.pacia.ptms.utils.http.SchedulersHelper;
import com.pacia.ptms.utils.http.ServiceFactory;
import com.trello.rxlifecycle2.android.FragmentEvent;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * 个人中心
 */
public class CenterFragment extends BaseFragment {
    private static final String TAG = "CenterFragment";

    public static Fragment getInstance(String str) {
        CenterFragment fragment = new CenterFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", str);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_time)
    TextView tv_time;
    private DialogUtils dialogUtils;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_center;
    }

    @Override
    public void initView() {
        dialogUtils = new DialogUtils();
        layout.setPadding(0, ToolUtils.getStatusBarHeight(fContext), 0, 0);
        tv_user_name.setText((String) SPUtils.getUserInfo(fContext, "user_name", ""));
        tv_time.setText(DateUtils.getNowDate("yyyy-MM-dd HH:mm"));
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    @Override
    public void initData() {

    }

    @Override
    public void lazyLoadData() {

    }

    @OnClick({R.id.layout_my_msg, R.id.layout_setting, R.id.layout_feedback,
            R.id.layout_about, R.id.layout_login_out})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.layout_my_msg:
                break;
            case R.id.layout_setting:
                break;
            case R.id.layout_feedback:
                break;
            case R.id.layout_about:
                break;
            case R.id.layout_login_out:
                dialogUtils.createAlertDialog(getActivity(), "提示", "确定退出登录", true);
                dialogUtils.setOnSureClickListener(new DialogUtils.onSureClickListener() {
                    @Override
                    public void onClick(View view) {
                        loginOut();
                    }
                });
                break;
        }
    }

    //退出登录
    private void loginOut() {
        ServiceFactory.getService(ApiService.class)
                .loginOut()
                .compose(SchedulersHelper.<ResponseBody>io_main(fContext, true,"退出中..."))
                .compose(this.<ResponseBody>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            handler.removeMessages(1);
                            doActivity(LoginActivity.class);
                            getActivity().finish();
                        } else {
                            ToastUtils.showShort("退出失败");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        ToastUtils.showShort(error);
                    }
                });
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tv_time.setText(DateUtils.getNowDate("yyyy-MM-dd HH:mm"));
                    handler.sendEmptyMessageDelayed(1, 1000);
                    break;
            }
        }
    };
}
