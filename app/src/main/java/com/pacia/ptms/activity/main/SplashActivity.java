package com.pacia.ptms.activity.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.activity.login.LoginActivity;
import com.pacia.ptms.utils.SPUtils;

import butterknife.BindView;

/**
 * 欢迎界面
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    @BindView(R.id.img_splash)
    ImageView imgSplash;

    private boolean isFirst = false;
    private Intent intent;
    private long delay = 1500;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        setToolBarVisibility(View.GONE);
        isFirst = (boolean) SPUtils.get(context, "isFirst", true);
        handler.sendEmptyMessageDelayed(1, delay);
    }

    @Override
    public void initData() {

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (isFirst) {
                        intent = new Intent(context, GuideActivity.class);
                    } else {
                        intent = new Intent(context, LoginActivity.class);
                    }
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
}
