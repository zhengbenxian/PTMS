package com.pacia.ptms.adapter;

import android.app.Activity;
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
import com.pacia.ptms.bean.PersonBean;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.ToolUtils;

import java.util.List;

/**
 * Created by p on 2019/03/28.
 */

public class DispatchPersonAdapter extends BaseQuickAdapter<PersonBean, BaseViewHolder> {
    private Context context;

    public DispatchPersonAdapter(Context context, int layoutResId, @Nullable List<PersonBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonBean item) {
        helper.setText(R.id.tv_dispatch_driver, item.getRyName());
        helper.setVisible(R.id.tv_driver_type, true);
        if (item.getRyStaffType().equals(Constant.PERSON_DRIVER)) {
            helper.setText(R.id.tv_driver_type, "司机");
        } else {
            helper.setText(R.id.tv_driver_type, "司押");
        }
        ImageView img = helper.getView(R.id.img_dispatch);
        ToolUtils.showImage(context,R.drawable.ic_no_header,img);
        helper.setText(R.id.tv_dispatch_plate, item.getRyMPhone());
        helper.setTextColor(R.id.tv_dispatch_plate, context.getResources().getColor
                (R.color.color_green));
        ToolUtils.setTvUnderLine((Activity) context, (TextView) helper.getView(R.id.tv_dispatch_plate));
//        helper.addOnClickListener(R.id.tv_dispatch_plate);
        LinearLayout layout = helper.getView(R.id.layout_dispatch_aptitude);
        for (int i = 0; i < item.getAptitudeList().size(); i++) {
            AptitudeBean bean = item.getAptitudeList().get(i);
            setViewDate(layout, bean.getZzName(), bean.getZzSrf1());
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
