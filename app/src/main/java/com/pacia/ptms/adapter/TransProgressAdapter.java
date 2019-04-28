package com.pacia.ptms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.TransProgressBean;
import com.pacia.ptms.service.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 运输详情进度
 * Created by p on 2019/03/28.
 */

public class TransProgressAdapter extends BaseQuickAdapter<TransProgressBean, BaseViewHolder> {
    private Context context;
    private List<TransProgressBean> list = new ArrayList<>();

    public TransProgressAdapter(Context context, int layoutResId, @Nullable List<TransProgressBean> data) {
        super(layoutResId, data);
        this.context = context;
        this.list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, TransProgressBean item) {
        if (item.getDyjdStatus().equals(Constant.TRANS_PRO_ISSUED_CLASS)) {
            helper.setVisible(R.id.tv_progress_No, true)
                    .setVisible(R.id.tv_progress_driver, true)
                    .setVisible(R.id.tv_progress_driver_e, true)
                    .setText(R.id.tv_progress_No, item.getCarNo())
                    .setText(R.id.tv_progress_driver, "司机：" + item.getDriver())
                    .setText(R.id.tv_progress_driver_e, "司押：" + item.getDriver_e());
        }
        helper.setText(R.id.tv_progress_oil, item.getDyjdConfirmBy())
                .setText(R.id.tv_progress_status, item.getDyjdStatus());
        if (helper.getAdapterPosition() == list.size() - 1) {
            helper.setBackgroundColor(R.id.view_line, context.getResources()
                    .getColor(R.color.color_green));
            ImageView img = helper.getView(R.id.img_dot);
            img.setImageResource(R.drawable.ic_circle_green);
        }
    }
}
