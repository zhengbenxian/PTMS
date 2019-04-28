package com.pacia.ptms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.RoteBean;
import com.pacia.ptms.utils.DateUtils;

import java.util.List;

/**
 * Created by p on 2019/03/28.
 */

public class RoteAdapter extends BaseQuickAdapter<RoteBean, BaseViewHolder> {
    private Context context;
    private String type;

    public RoteAdapter(Context context, String type, int layoutResId, @Nullable List<RoteBean> data) {
        super(layoutResId, data);
        this.context = context;
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, RoteBean item) {
        if ("rote".equals(type)) {
            helper.setText(R.id.tv_rote_time, "最后偏离时间：" + DateUtils.timeStampToDate(item.getInsertDate()))
                    .setText(R.id.tv_rote_location, "最后偏离位置：" + item.getPositionLat() + " ; " +
                            item.getPositionLong());
        } else if ("overtime".equals(type)) {
            helper.setText(R.id.tv_rote_time, "停车时长：" + DateUtils.timeStampToDate(item.getInsertDate()))
                    .setText(R.id.tv_rote_location, "停车位置：" + item.getPositionLat() + " ; " +
                            item.getPositionLong());
        }
    }
}
