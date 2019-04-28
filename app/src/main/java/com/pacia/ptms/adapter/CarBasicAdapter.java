package com.pacia.ptms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.CarBasicBean;
import com.pacia.ptms.utils.DateUtils;
import com.pacia.ptms.utils.ToolUtils;

import java.util.List;

/**
 * 车辆
 * Created by p on 2019/03/05.
 */

public class CarBasicAdapter extends BaseQuickAdapter<CarBasicBean, BaseViewHolder> {
    private Context context;
    private boolean showStatue = false;

    public CarBasicAdapter(Context context, int layoutResId, @Nullable List<CarBasicBean> data,
                           boolean showStatue) {
        super(layoutResId, data);
        this.context = context;
        this.showStatue = showStatue;
    }

    @Override
    protected void convert(BaseViewHolder helper, CarBasicBean item) {
        helper.setVisible(R.id.tv_car_statue, showStatue);
        ImageView img = helper.getView(R.id.basic_apti_icon);
        ToolUtils.showImage(context, R.mipmap.car, img);
        helper.setText(R.id.tv_car_plant, item.getClPlateNo())
                .setText(R.id.tv_car_statue, item.getClStatus())
                .setText(R.id.tv_car_type, "车辆类型：" + item.getClTruckType())
                .setText(R.id.tv_car_model, "车辆型号：" + item.getClBrandModel())
                .setText(R.id.tv_car_apti_stime, "购入日期：" + DateUtils.timeStampToDate(item.getClPurchaseDate()))
                .setText(R.id.tv_car_apti_etime, "报废日期：" + DateUtils.timeStampToDate(item.getClScrapDate()));
        RecyclerView rv = helper.getView(R.id.rv_car_apti);
        ToolUtils.createRecycleManager(context, rv);
        AptitudeAdapter adapter = new AptitudeAdapter(context, R.layout.item_rv_basic_apti,
                item.getbCarirLienceVo());
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
