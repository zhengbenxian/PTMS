package com.pacia.ptms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.WarningBean;
import com.pacia.ptms.utils.DateUtils;

import java.util.List;

/**
 * 预警列表
 * Created by p on 2019/03/28.
 */

public class WarningAdapter extends BaseQuickAdapter<WarningBean, BaseViewHolder> {
    private Context context;

    public WarningAdapter(Context context, int layoutResId, @Nullable List<WarningBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, WarningBean item) {
        helper.setText(R.id.tv_warning_type, item.getWarningTypeName())
                .setText(R.id.tv_warning_plate, item.getTsNo())
                .setText(R.id.tv_warning_remark, item.getRemarks())
                .setText(R.id.tv_warning_place, item.getOutWarehouseName() + " ->\n" +
                        item.getGasStation());
        String time = DateUtils.timeStampToDate(item.getWarningDate());
        if (time.contains(" ")) {
            String[] times = time.split(" ");
            helper.setText(R.id.tv_warning_time, times[0] + "\n" + times[1]);
        } else {
            helper.setText(R.id.tv_warning_time, time);
        }

    }
}
