package com.pacia.ptms.adapter;

import android.content.Context;

import com.pacia.ptms.R;
import com.pacia.ptms.adapter.common.CommonAdapter;
import com.pacia.ptms.adapter.common.ViewHolder;
import com.pacia.ptms.bean.CommonBean;

import java.util.List;

/**
 * Created by p on 2019/04/15.
 */

public class MySpinnerAdapter extends CommonAdapter<CommonBean> {

    public MySpinnerAdapter(Context context, List<CommonBean> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, CommonBean cb) {
        holder.setText(R.id.item_spinner_tv, cb.getName());
    }
}
