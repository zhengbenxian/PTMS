package com.pacia.ptms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.StatisticsBean;

import java.util.List;

/**
 * 承运商安全统计 列表
 * Created by p on 2019/03/28.
 */

public class StatisticsAdapter extends BaseQuickAdapter<StatisticsBean, BaseViewHolder> {
    private Context context;
    private String type;

    public StatisticsAdapter(Context context, String type, int layoutResId, @Nullable List<StatisticsBean> data) {
        super(layoutResId, data);
        this.context = context;
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, StatisticsBean item) {
        TextView tv = helper.getView(R.id.tv_statis_top2);
        if (type.equals("carrier")) {
            tv.setTextColor(context.getResources().getColor(R.color.color_tv_second));
            tv.setTextSize(14f);
            helper.setText(R.id.tv_statis_top1, item.getCysName())
                    .setText(R.id.tv_statis_top2, item.getCysType())
                    .setText(R.id.tv_statis_type, "")
                    .setText(R.id.tv_statis_month, item.getTypeBtpNum())
                    .setText(R.id.tv_statis_count, item.getAllBtpNum());
        } else if (type.equals("tool")) {
            helper.setText(R.id.tv_statis_top1, item.getTruckPlateNo1() + "\n" +
                    item.getTruckPlateNo2())
                    .setText(R.id.tv_statis_top2, item.getCysName())
                    .setText(R.id.tv_statis_type, item.getCysType())
                    .setText(R.id.tv_statis_month, item.getTypeBtpNum())
                    .setText(R.id.tv_statis_count, item.getAllBtpNum());
        }
    }
}
