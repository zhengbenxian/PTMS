package com.pacia.ptms.carrier.info;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.adapter.PicAdapter;
import com.pacia.ptms.utils.DialogUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.widget.CustomDatePicker;
import com.pacia.ptms.widget.MyGridView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 资质信息 新增 编辑 查看
 */
public class AptitudeActivity extends BaseActivity {
    @BindView(R.id.et_apti_name)
    EditText et_apti_name;
    @BindView(R.id.et_apti_No)
    EditText et_apti_No;
    @BindView(R.id.tv_apti_sTime)
    TextView tv_apti_sTime;
    @BindView(R.id.tv_apti_eTime)
    TextView tv_apti_eTime;
    @BindView(R.id.gv_apti_img)
    MyGridView gv_apti_img;
    private CustomDatePicker picker;
    private PicAdapter adapter;
    private DialogUtils dialogUtils;

    @Override
    public int getLayoutId() {
        return R.layout.activity_aptitude;
    }

    @Override
    public void initView() {
        dialogUtils = new DialogUtils();
        ToolUtils.initDatePicker(context, picker, tv_apti_sTime);
        ToolUtils.initDatePicker(context, picker, tv_apti_eTime);
        adapter = new PicAdapter(context, new ArrayList<String>(), true);
        gv_apti_img.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initData() {
        gv_apti_img.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getCount() - 1) {
                    dialogUtils.createPhotoDialog((Activity) context);
                    dialogUtils.setOnCameraClickListener(new DialogUtils.onCameraClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                }
            }
        });
    }
}
