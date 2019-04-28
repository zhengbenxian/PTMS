package com.pacia.ptms.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.SecurityBean;
import com.pacia.ptms.utils.DateUtils;

import java.util.List;

/**
 * 安全违规
 * Created by p on 2019/03/05.
 */

public class SecurityAdapter extends BaseQuickAdapter<SecurityBean, BaseViewHolder> {

    public SecurityAdapter(int layoutResId, @Nullable List<SecurityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityBean item) {
        helper.setText(R.id.tv_record_time, DateUtils.timeStampToDate(item.getWfgzCheckTime()))
                .setText(R.id.tv_record_company, item.getOilName())
                .setText(R.id.tv_record_status, item.getWfgzStatus());
    }
}
