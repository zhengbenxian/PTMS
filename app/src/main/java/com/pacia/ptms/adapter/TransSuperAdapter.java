package com.pacia.ptms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.RecordBean;
import com.pacia.ptms.utils.DateUtils;

import java.util.List;

/**
 * Created by p on 2019/03/28.
 */

public class TransSuperAdapter extends BaseQuickAdapter<RecordBean, BaseViewHolder> {
    private Context context;

    public TransSuperAdapter(Context context, int layoutResId, @Nullable List<RecordBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordBean item) {
        helper.setText(R.id.tv_trans_list_tiOil, item.getGasStation())
                .setText(R.id.tv_trans_list_gas, item.getDyHeaderOutWarehouseName())
                .setText(R.id.tv_trans_list_number, "...等" + item.getGasStationNum() + "" +
                        "个单位")
                .setText(R.id.tv_trans_list_statue, item.getDyHeaderStatus())
                .setText(R.id.tv_trans_list_plate, item.getDyHeaderTsNo1() + " " +
                        item.getDyHeaderTsNo2())
                .setText(R.id.tv_trans_list_time, DateUtils.timeStampToDate(item.getDyHeaderBDate()) + " - " +
                        DateUtils.timeStampToDate(item.getDyHeaderSrf9()));
    }
}
