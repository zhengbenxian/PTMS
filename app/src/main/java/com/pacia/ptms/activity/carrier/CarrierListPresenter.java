package com.pacia.ptms.activity.carrier;

import com.pacia.ptms.bean.CarrierBean;
import com.pacia.ptms.service.ApiService;
import com.pacia.ptms.service.Constant;
import com.pacia.ptms.utils.JsonUtils;
import com.pacia.ptms.utils.http.BaseObserver;
import com.pacia.ptms.utils.http.SchedulersHelper;
import com.pacia.ptms.utils.http.ServiceFactory;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by p on 2019/03/12.
 */

public class CarrierListPresenter implements CarrierListInterface.Presenter {
    private static final String TAG = "CarrierListPresenter";
    private CarrierListInterface.View view;
    private RxFragment context;

    public CarrierListPresenter(RxFragment context, CarrierListInterface.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getCarrierList(String type, int page, int rows) {
        ServiceFactory.getService(ApiService.class)
                .queryCarrierList(type, page, rows)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(context.<ResponseBody>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            List<CarrierBean> list = JsonUtils.getCarrierList(json);
                            int total = JsonUtils.getTotal(json);
                            view.showCarrierList(list, total);
                        } else {
                            view.onFailed("查询失败");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        view.onFailed(error);
                    }
                });
    }
}
