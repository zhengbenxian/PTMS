package com.pacia.ptms.adapter;

import android.content.Context;

import com.pacia.ptms.R;
import com.pacia.ptms.adapter.common.CommonAdapter;
import com.pacia.ptms.adapter.common.ViewHolder;
import com.pacia.ptms.bean.CommonBean;

import java.util.List;

/**
 * Created by p on 2019/03/01.
 */

public class GridViewAdapter extends CommonAdapter<CommonBean> {
    private int number;
    private Context context;

    public GridViewAdapter(Context context, List mDatas, int layoutId, int number) {
        super(context, mDatas, layoutId);
        this.number = number;
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, CommonBean cb) {
        holder.setText(R.id.item_main_tv_name, cb.getName());
        holder.setImageResource(R.id.item_main_gv_img, cb.getGid());
//        TextView tv=holder.getView(R.id.item_main_tv_number);
//        tv.setBackgroundResource(R.mipmap.ic_error);
//        tv.setText("");
//        BadgeView badgeView = new BadgeView(context);
//        badgeView.setTargetView(holder.getView(R.id.item_main_gv_img));
//        badgeView.setBadgeCount(number);
    }
}
