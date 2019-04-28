package com.pacia.ptms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.MonitorBean;
import com.pacia.ptms.service.Constant;

import java.util.List;

/**
 * 车载监控
 * Created by p on 2019/03/05.
 */

public class MonitorAdapter extends BaseQuickAdapter<MonitorBean, BaseViewHolder> {
    private String type = "";
    private Context context;

    public MonitorAdapter(Context context, String type, int layoutResId, @Nullable List<MonitorBean> data) {
        super(layoutResId, data);
        this.type = type;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MonitorBean item) {
        TextView tv = helper.getView(R.id.tv_monitor_status);
        if ("illegal".equals(type)) {
            helper.setText(R.id.tv_monitor_name, item.getCameraName())
                    .setText(R.id.tv_monitor_status, item.getCameraStatus());
            if (item.getCameraStatusId().equals(Constant.CAMERA_OFF_LINE)) {
                tv.setBackgroundResource(R.drawable.shape_stroke_red_5dp);
                tv.setTextColor(context.getResources().getColor(R.color.color_red));
            }
        } else {
            helper.setText(R.id.tv_monitor_name, item.getName())
                    .setText(R.id.tv_monitor_status, item.getSrf1());
            if (item.getSrf1().equals("离线")) {
                tv.setBackgroundResource(R.drawable.shape_stroke_red_5dp);
                tv.setTextColor(context.getResources().getColor(R.color.color_red));
            }
        }

    }
}
