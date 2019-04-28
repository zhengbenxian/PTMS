package com.pacia.ptms.activity.carrier;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.CarrierBean;
import com.pacia.ptms.utils.ToolUtils;

import java.util.List;

/**
 * Created by p on 2019/03/12.
 */

public class CarrierListAdapter extends BaseQuickAdapter<CarrierBean, BaseViewHolder> {
    private Context context;

    public CarrierListAdapter(Context context, int layoutResId, @Nullable List<CarrierBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CarrierBean item) {
        helper.setText(R.id.name_carrier_list, item.getCysName())
                .setText(R.id.contract_carrier_list, item.getCysContract())
                .setText(R.id.tel_carrier_list, item.getCysContractTel() + "")
                .setText(R.id.apti_carrier_list, item.getZzSrf1())
                .setText(R.id.type_carrier_list, item.getCysType() + "");
        TextView tv = helper.getView(R.id.tel_carrier_list);
        ToolUtils.setTvUnderLine((Activity) context, tv);
//        helper.addOnClickListener(R.id.tel_carrier_list);
        if ("".equals(item.getZzSrf1()))
            helper.getView(R.id.apti_carrier_list).setVisibility(View.INVISIBLE);
    }
}
