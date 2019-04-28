package com.pacia.ptms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.AptitudeBean;
import com.pacia.ptms.bean.CarBasicBean;
import com.pacia.ptms.utils.ToolUtils;

import java.util.List;

/**
 * Created by p on 2019/03/28.
 */

public class DispatchCarAdapter extends BaseQuickAdapter<CarBasicBean, BaseViewHolder> {
    private Context context;

    public DispatchCarAdapter(Context context, int layoutResId, @Nullable List<CarBasicBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CarBasicBean item) {
        helper.setText(R.id.tv_dispatch_plate, item.getClPlateNo());
        helper.setText(R.id.tv_dispatch_company, item.getCompany_name());
        ImageView img = helper.getView(R.id.img_dispatch);
        ToolUtils.showImage(context, R.mipmap.car, img);
        LinearLayout layout = helper.getView(R.id.layout_dispatch_aptitude);
        for (int i = 0; i < item.getbCarirLienceVo().size(); i++) {
            AptitudeBean aptis = item.getbCarirLienceVo().get(i);
            setViewDate(layout, aptis.getZzName(), aptis.getZzSrf1());
        }
    }

    private void setViewDate(LinearLayout layout, String name, String level) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dispatch_aptitude_add,
                null);
        TextView tv_name = view.findViewById(R.id.item_aptitude_name);
        TextView tv_level = view.findViewById(R.id.item_aptitude_status);
        tv_name.setText(name);
        tv_level.setText(level);
        view.setLayoutParams(params);
        layout.addView(view);
    }
}
