package com.pacia.ptms.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.RecordBean;
import com.pacia.ptms.utils.DateUtils;

import java.util.List;

/**
 * 运行记录
 * Created by p on 2019/03/05.
 */

public class RecordAdapter extends BaseQuickAdapter<RecordBean, BaseViewHolder> {

    public RecordAdapter(int layoutResId, @Nullable List<RecordBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordBean item) {
//        String oils = item.getGasStation();
//        String oil[];
//        if (oils.contains(",")) {
//            oil = oils.split(",");
        helper.setText(R.id.tv_record_company, item.getDyHeaderOutWarehouseName()
                + " -> " + item.getGasStation() + " ...等(" + item.getGasStationNum() + ")");
//        } else {
//            helper.setText(R.id.tv_record_company, item.getDyHeaderOutWarehouseName()
//                    + " -> " + oils);
//        }
        helper.setText(R.id.tv_record_time, DateUtils.timeStampToDate(item.getDyHeaderBDate()
                + " - " + DateUtils.timeStampToDate(item.getDyHeaderSrf9())))
                .setText(R.id.tv_record_status, item.getDyjdStatus());
    }
}
