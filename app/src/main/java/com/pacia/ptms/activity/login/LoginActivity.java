package com.pacia.ptms.activity.login;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.activity.main.DepotMainActivity;
import com.pacia.ptms.activity.main.MainActivity;
import com.pacia.ptms.bean.LoginBean;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.BaseUrl;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.SPUtils;
import com.pacia.ptms.utils.ToastUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.utils.http.BaseObserver;
import com.pacia.ptms.utils.http.SchedulersHelper;
import com.pacia.ptms.utils.http.ServiceFactory;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.et_login_user)
    EditText et_login_user;
    @BindView(R.id.et_login_pw)
    EditText et_login_pw;
    @BindView(R.id.spinner_login_role)
    Spinner spinner_login_role;
    @BindView(R.id.tv_forget_pw)
    TextView tv_forget_pw;
    @BindView(R.id.img_head)
    ImageView img_head;

    private String user, pw, captcha;
    private String[] captcha_ids;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setToolBarVisibility(View.GONE);
        captcha_ids = getResources().getStringArray(R.array.user_role_id);
        ToolUtils.checkPermission(this, Constant.permissions);
    }

    @Override
    public void initData() {
        onSetListener();
        et_login_user.setText(SPUtils.getUserPw(this, "user"));
        et_login_pw.setText(SPUtils.getUserPw(this, "password"));
    }

    @OnClick({R.id.tv_login, R.id.tv_forget_pw})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
//                doActivity(MainActivity.class);
//                finish();
                user = et_login_user.getText().toString();
                pw = et_login_pw.getText().toString();
                if ("".equals(user)) {
                    ToastUtils.showShort("请输入用户名");
                    return;
                }
                if ("".equals(pw)) {
                    ToastUtils.showShort("请输入密码");
                    return;
                }
                login(user, ToolUtils.getMD5Str(pw), captcha);
                break;
            case R.id.tv_forget_pw:
                break;
        }
    }

    private void onSetListener() {
        spinner_login_role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                captcha = captcha_ids[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //登录
    private void login(final String userName, final String passWord, final String captcha) {
        ServiceFactory.getService(BaseUrl.BASE_URL, ApiService.class)
                .login(userName, passWord, captcha)
                .compose(SchedulersHelper.<ResponseBody>io_main(this, true,"登录中..."))
                .compose(this.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            LoginBean lb = JsonUtils.getLoginBean(json);
                            SPUtils.putUserPw(context, userName, pw, captcha);
                            SPUtils.putUserInfo(context, lb);
                            if (lb.getRole_type().equals(Constant.ROLE_OIL_WAREHOUSE)) {
                                doActivity(DepotMainActivity.class);
                            } else {
                                doActivity(MainActivity.class);
                            }
                            finish();
                        } else {
                            ToolUtils.getLoginError(JsonUtils.getBusiCode(json));
                        }
                    }

                    @Override
                    public void onError(String error) {
                        ToastUtils.showShort(error);
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult
            (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ToolUtils.PERMISSION_CODE == requestCode) {
            boolean isAll = true;
            List<String> list = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (this, permissions[i]);
                    isAll = false;
                    list.add(permissions[i]);
                }
            }
            if (!isAll) {
                for (int i = 0; i < list.size(); i++) {
                    Log.e(TAG, "onRequestPermissionsResult: " + list.get(i));
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
