package com.pacia.ptms.activity.carrier;

import com.pacia.ptms.bean.CarrierBean;

import java.util.List;

/**
 * Created by p on 2019/03/12.
 */

public class CarrierListInterface {
    interface View {
        void showCarrierList(List<CarrierBean> list, int total);

        void onFailed(String msg);
    }

    interface Presenter {
        void getCarrierList(String type, int page, int rows);
    }
}
