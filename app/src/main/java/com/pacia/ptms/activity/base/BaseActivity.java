package com.pacia.ptms.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.utils.ToastUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public abstract class BaseActivity extends RxAppCompatActivity {
    private static final String TAG = "BaseActivity";
    private TextView tv_top_back;
    private View view_top_line;
    private TextView tv_top_left;
    private TextView tv_top_title;
    private TextView tv_top_right;
    private TextView tv_top_lright;
    private TextView tv_top_llright;
//    private EditText et_top_search;
    private Toolbar toolBar;

    private LinearLayout view;
    private OnBackClickListen onBackClickListen;
    private OnLeftClickListen onLeftClickListen;
    private OnRightClickListen onRightClickListen;
    private OnLRightClickListen onLRightClickListen;
    private OnLLRightClickListen onLLRightClickListen;
    private Unbinder unbinder;
    public Context context;
    private View subView;
    private onEditorActionListener onEditorActionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initTopView());
        context = this;
        ToolUtils.setStatusBarFullTransparent(this);
        unbinder = ButterKnife.bind(this);
//        ToolUtils.checkPermission(this, Constant.permissions);
        initView();
        initData();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != unbinder)
            unbinder.unbind();
    }

    private View initTopView() {
        view = (LinearLayout) getLayoutInflater().inflate(R.layout.top_layout, null);
        initToolBar(view);
        if (0 != getLayoutId()) {
            subView = getLayoutInflater().inflate(getLayoutId(), null);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            subView.setLayoutParams(params);
            view.addView(subView);
        }
        return view;
    }

    private void initToolBar(View view) {
        toolBar = view.findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        toolBar.setPadding(0, ToolUtils.getStatusBarHeight(this) ,
                0, 0);
        tv_top_back = view.findViewById(R.id.tv_top_back);
        view_top_line = view.findViewById(R.id.view_top_line);
        tv_top_left = view.findViewById(R.id.tv_top_left);
        tv_top_title = view.findViewById(R.id.tv_top_title);
        tv_top_right = view.findViewById(R.id.tv_top_right);
        tv_top_lright = view.findViewById(R.id.tv_top_lright);
        tv_top_llright = view.findViewById(R.id.tv_top_llright);
//        et_top_search = view.findViewById(R.id.et_top_search);
        //是否显示应用logo
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        //是否显示应用标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        et_top_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    String search = v.getText().toString();
//                    onEditorActionListener.onEditAction(search);
//                    ToolUtils.closeSoft((Activity) context);
//                }
//                return false;
//            }
//        });
    }

    //是否显示toolBar
    public void setToolBarVisibility(int visible) {
        if (null != toolBar)
            toolBar.setVisibility(visible);
    }

    //是否显示关闭图标
    public void setBackVisibility(int visible) {
        if (null != tv_top_back)
            tv_top_back.setVisibility(visible);
    }

    public Toolbar getToolBar() {
        return toolBar;
    }

    public void setToolBar(Toolbar toolBar) {
        this.toolBar = toolBar;
    }

    public TextView getTv_top_back() {
        return tv_top_back;
    }

    public TextView getTv_top_right() {
        return tv_top_right;
    }

    public TextView getTv_top_lright() {
        return tv_top_lright;
    }

    //设置关闭信息
    public void setBackMsg(String text) {
        setBackMsg(text, 0);
    }

    public void setBackMsg(String text, @DrawableRes int id) {
        if (null != tv_top_back) {
            tv_top_back.setText(text);
            tv_top_back.setCompoundDrawablesWithIntrinsicBounds
                    (id, 0, 0, 0);
        }
    }

    //是否显示竖线
    public void setLineVisibility(int visible) {
        if (null != view_top_line)
            view_top_line.setVisibility(visible);
    }

    //是否显示左边
    public void setLeftVisibility(int visible) {
        if (null != tv_top_left)
            tv_top_left.setVisibility(visible);
    }

    public TextView getTv_top_left() {
        return tv_top_left;
    }

    //设置左边图标
    public void setLeftMsg(String text) {
        setLeftMsg(text, 0);
    }

    public void setLeftMsg(String text, @DrawableRes int id) {
        if (null != tv_top_left) {
            tv_top_left.setText(text);
            tv_top_left.setCompoundDrawablesWithIntrinsicBounds
                    (0, 0, id, 0);
        }
    }

    //是否显示标题
    public void setTitleVisibility(int visible) {
        if (null != tv_top_title)
            tv_top_title.setVisibility(visible);
    }

    //设置标题
    public void setTopTitle(CharSequence title) {
        if (null != tv_top_title)
            tv_top_title.setText(title);
    }

    //是否显示右边图标
    public void setRightVisibility(int visible) {
        if (null != tv_top_right)
            tv_top_right.setVisibility(visible);
    }

    //设置右边图标
    public void setRightMsg(String text) {
        setRightMsg(text, 0);
    }

    public String getRightMsg() {
        String msg = "";
        if (null != tv_top_right) {
            msg = tv_top_right.getText().toString();
        }
        return msg;
    }

    public void setRightMsg(String text, @DrawableRes int id) {
        if (null != tv_top_right) {
            tv_top_right.setText(text);
            tv_top_right.setCompoundDrawablesWithIntrinsicBounds
                    (0, 0, id, 0);
        }
    }

    //是否显示靠右边图标
    public void setLRightVisibility(int visible) {
        if (null != tv_top_lright)
            tv_top_lright.setVisibility(visible);
    }

    public void setLLRightVisibility(int visible) {
        if (null != tv_top_llright)
            tv_top_llright.setVisibility(visible);
    }

    //设置靠右边图标
    public void setLRightMsg(String text) {
        setLRightMsg(text, 0);
    }

    public void setLRightMsg(String text, @DrawableRes int id) {
        if (null != tv_top_lright) {
            tv_top_lright.setText(text);
            tv_top_lright.setCompoundDrawablesWithIntrinsicBounds
                    (0, 0, id, 0);
        }
    }

    public void setLLRightMsg(String text, @DrawableRes int id) {
        if (null != tv_top_llright) {
            tv_top_llright.setText(text);
            tv_top_llright.setCompoundDrawablesWithIntrinsicBounds
                    (0, 0, id, 0);
        }
    }

    public void onSessionError(String msg) {
        if (msg.toLowerCase().contains("session")) {
//            doActivity(LoginActivity.class);
            finish();
        }
        ToastUtils.showShort(msg);
    }


    @OnClick({R.id.tv_top_back, R.id.tv_top_left, R.id.tv_top_right,
            R.id.tv_top_lright, R.id.tv_top_llright})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_top_back:
                if (null == onBackClickListen) {
                    finish();
                } else {
                    onBackClickListen.onBackClick(view);
                }
                break;
            case R.id.tv_top_left:
                if (null != onLeftClickListen)
                    onLeftClickListen.onLeftClick(view);
                break;
            case R.id.tv_top_right:
                if (null != onRightClickListen)
                    onRightClickListen.onRightClick(view);
                break;
            case R.id.tv_top_lright:
                if (null != onLRightClickListen)
                    onLRightClickListen.onLRightClick(view);
                break;
            case R.id.tv_top_llright:
                if (null != onLLRightClickListen)
                    onLLRightClickListen.onLLRightClick(view);
                break;
        }
    }

    @Override
    public void startActivity(Intent intent) {
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        super.startActivity(intent);
    }

    public void doActivity(Class<?> activity) {
        Intent intent = new Intent(context, activity);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        context.startActivity(intent);
    }

    public void doActivity(Class<?> activity, String... str) {
        Intent intent = new Intent(context, activity);
        for (int i = 0; i < str.length; i++) {
            intent.putExtra(String.valueOf(i), str[i]);
        }
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        context.startActivity(intent);
    }

    public void doActivity(Class<?> activity, Integer... args) {
        Intent intent = new Intent(context, activity);
        for (int i = 0; i < args.length; i++) {
            intent.putExtra(String.valueOf(i), args[i]);
        }
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        context.startActivity(intent);
    }

    public interface OnBackClickListen {
        void onBackClick(View view);
    }

    public interface OnLeftClickListen {
        void onLeftClick(View view);
    }

    public interface OnRightClickListen {
        void onRightClick(View view);
    }

    public interface OnLRightClickListen {
        void onLRightClick(View view);
    }

    public interface OnLLRightClickListen {
        void onLLRightClick(View view);
    }

    public void setOnBackClickListener(OnBackClickListen onBackClickListener) {
        this.onBackClickListen = onBackClickListener;
    }

    public void setOnLeftClickListen(OnLeftClickListen onLeftClickListen) {
        this.onLeftClickListen = onLeftClickListen;
    }

    public void setRightClickListener(OnRightClickListen onRightClickListen) {
        this.onRightClickListen = onRightClickListen;
    }

    public void setLRightClickListener(OnLRightClickListen onLRightClickListen) {
        this.onLRightClickListen = onLRightClickListen;
    }

    public void setLLRightClickListener(OnLLRightClickListen onLLRightClickListen) {
        this.onLLRightClickListen = onLLRightClickListen;
    }

    public interface onEditorActionListener {
        void onEditAction(String search);
    }

    public void setOnEditorActionListener(onEditorActionListener onEditorActionListener) {
        if (null != onEditorActionListener) {
            this.onEditorActionListener = onEditorActionListener;
        }
    }


    private float x, y;
    private float scollX;

    private boolean isScroll = false;//是否滑动

    private boolean isCanScroll = true;//是否可以滑动返回，默认可以

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    //右滑关闭界面
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                x = event.getX();
//                y = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float mX = event.getX();
//                float mY = event.getY();
//                scollX = mX - x;
//                if (isScroll && scollX > 0) {
//                    view.scrollTo(-(int) scollX, 0);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                if (isScroll && scollX > 400) {
//                    finish();
//                } else {
//                    isScroll = false;
//                    view.scrollTo(0, 0);
//                }
//                break;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            float x = ev.getX();
//            if (x <= 50 && isCanScroll) {
//                isScroll = true;
//                return true;
//            }
//        }
//        try {
//            return super.dispatchTouchEvent(ev);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();
}
