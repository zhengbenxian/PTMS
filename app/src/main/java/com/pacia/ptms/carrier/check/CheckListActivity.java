package com.pacia.ptms.carrier.check;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.utils.DateUtils;
import com.pacia.ptms.utils.ToolUtils;
import com.pacia.ptms.widget.CustomDatePicker;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 安全检查 违规违章列表
 */
public class CheckListActivity extends BaseActivity {
    private static final String TAG = "CheckListActivity";
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipLayout;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_empty_data)
    LinearLayout ll_empty_data;
    @BindView(R.id.tv_error)
    TextView tv_error;
    @BindView(R.id.tv_check_time)
    TextView tv_check_time;
    @BindView(R.id.tv_check_level)
    TextView tv_check_level;
    @BindView(R.id.tv_check_status)
    TextView tv_check_status;
    @BindView(R.id.spinner)
    Spinner spinner;
    private CustomDatePicker picker;
    private ArrayAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_check_list;
    }

    @Override
    public void initView() {
        tv_check_time.setText(DateUtils.getNowDate("yyyy-MM-dd"));
        ToolUtils.initDatePicker(context, picker, tv_check_time);
//        adapter=new ArrayAdapter(context,R.layout.layout_spinner_content,
//                context.getResources().getStringArray(R.array.statue));
        adapter.setDropDownViewResource(R.layout.layout_spinner_content);
        spinner.setAdapter(adapter);
    }

    @Override
    public void initData() {
        int year = DateUtils.getYear();
        picker = new CustomDatePicker(context, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                    tv_check_time.setText(time);
            }
        }, DateUtils.setDate(year - 1, 1, 1),
                DateUtils.setDate(year + 2, 12, 31));
        picker.showSpecificTime(false);
    }

    @OnClick({R.id.tv_check_time, R.id.tv_check_level, R.id.tv_check_status})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_check_time:
                picker.show(DateUtils.getNowDate());
                break;
            case R.id.tv_check_level:

                break;
            case R.id.tv_check_status:
                break;
        }
    }
}
