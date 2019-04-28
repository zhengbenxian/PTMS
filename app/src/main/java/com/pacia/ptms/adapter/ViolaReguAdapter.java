package com.pacia.ptms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.ViolaReguInfoBean;
import com.pacia.ptms.utils.DateUtils;

import java.util.List;

/**
 * 违章违规
 * Created by p on 2019/03/28.
 */

public class ViolaReguAdapter extends BaseQuickAdapter<ViolaReguInfoBean, BaseViewHolder> {
    private Context context;

    public ViolaReguAdapter(Context context, int layoutResId, @Nullable List<ViolaReguInfoBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ViolaReguInfoBean item) {
        helper.setText(R.id.tv_viola_item_grade, item.getWfgzGrade())
                .setText(R.id.tv_viola_item_plate, item.getWfgzPlateNo())
                .setText(R.id.tv_viola_item_company, item.getOilName())
                .setText(R.id.tv_viola_item_statue, item.getWfgzStatus())
                .setText(R.id.tv_viola_item_No, item.getTransportNo())
                .setText(R.id.tv_viola_item_time,
                        DateUtils.timeStampToDate(item.getWfgzCheckTime()));
    }
}
