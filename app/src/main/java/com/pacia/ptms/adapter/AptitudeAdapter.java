package com.pacia.ptms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.AptitudeBean;
import com.pacia.ptms.utils.DateUtils;
import com.pacia.ptms.utils.ToolUtils;

import java.util.List;

/**
 * Created by p on 2019/03/28.
 */

public class AptitudeAdapter extends BaseQuickAdapter<AptitudeBean, BaseViewHolder> {
    private Context context;

    public AptitudeAdapter(Context context, int layoutResId, @Nullable List<AptitudeBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AptitudeBean item) {
        helper.setText(R.id.item_tv_apti_name, item.getZzName())
                .setText(R.id.item_tv_card, item.getZzCertid());
        String start, end;
        start = DateUtils.timeStampToDate(item.getCertSDate());
        end = DateUtils.timeStampToDate(item.getZzCertEDate());
        helper.setText(R.id.item_tv_apti_time, start + " - " + end);
        ImageView img = helper.getView(R.id.basic_apti_icon);
//        ToolUtils.showImage(context, item.getZzImgUrl(), img);
        ToolUtils.showImage(context, item.getNativeUrl(), img);
        helper.addOnClickListener(R.id.basic_apti_icon);
    }
}
