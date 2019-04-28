package com.pacia.ptms.activity.transport;

import android.widget.TextView;

import com.pacia.ptms.R;
import com.pacia.ptms.activity.base.BaseActivity;
import com.pacia.ptms.utils.DateUtils;

import butterknife.BindView;

/**
 * 进度详情
 */
public class TransProDetailActivity extends BaseActivity {
    @BindView(R.id.tv_pro_statue)
    TextView tv_pro_statue;
    @BindView(R.id.tv_pro_place)
    TextView tv_pro_place;
    @BindView(R.id.tv_pro_time)
    TextView tv_pro_time;
    private String statue, place, time;

    @Override
    public int getLayoutId() {
        return R.layout.activity_trans_pro_detail;
    }

    @Override
    public void initView() {
        setTopTitle("进度详情");
        statue = getIntent().getStringExtra("0");
        place = getIntent().getStringExtra("1");
        time = getIntent().getStringExtra("2");
    }

    @Override
    public void initData() {
        tv_pro_statue.setText(statue);
        tv_pro_place.setText(place);
        tv_pro_time.setText(DateUtils.timeStampToDate(time));
    }
}
