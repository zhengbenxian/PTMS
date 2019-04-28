package com.pacia.ptms.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.CarListBean;

import java.util.List;

/**
 * 车辆列表
 * Created by p on 2019/03/05.
 */

public class CarListAdapter extends BaseQuickAdapter<CarListBean, BaseViewHolder> {

    public CarListAdapter(int layoutResId, @Nullable List<CarListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarListBean item) {
        helper.setText(R.id.tv_car_safe_state, item.getViolatNum() + " 件安全违规未结束")
                .setText(R.id.tv_car_plant, item.getPlateNo())
                .setText(R.id.tv_half_car_plant, item.getPlateNo2())
                .setText(R.id.tv_car_apti_state, "资质" + item.getSraf1())
                .setText(R.id.tv_car_state, item.getStatus())
                .setText(R.id.tv_car_task_num, "运输任务  " + item.getPlanNum())
                .setText(R.id.tv_car_safe_num, "安全违规  " + item.getViolatAllNum());
    }
}
