package com.pacia.ptms.oildepot.auditing;

import android.view.View;
import android.widget.EditText;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 入库检查
 */
public class AuditingActivity extends BaseActivity {
    @BindView(R.id.et_plate)
    EditText et_plate;
    private String et_content;

    @Override
    public int getLayoutId() {
        return R.layout.activity_auditing;
    }

    @Override
    public void initView() {
        setTopTitle("入库检查");
    }

    @Override
    public void initData() {
        et_plate.setText("京B23213");
    }

    @OnClick({R.id.tv_begin_check})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_begin_check:
                et_content = et_plate.getText().toString();
                if (!"".equals(et_content))
                    doActivity(AuditWarehouseActivity.class, et_content);
                else
                    ToastUtils.showShort("请输入车船牌号");
                break;
        }
    }
}
