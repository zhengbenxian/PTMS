package com.pacia.ptms.carrier.car;

import com.pacia.ptms.bean.MonitorBean;
import com.pacia.ptms.bean.RecordBean;
import com.pacia.ptms.bean.SecurityBean;
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
 * Created by p on 2019/04/03.
 */

public class CarOtherPresenter implements CarOtherInterface.Presenter {
    private CarOtherInterface.View view;
    private RxFragment context;

    public CarOtherPresenter(RxFragment context, CarOtherInterface.View view) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void getMonitorList(String plate1, String plate2) {
        ServiceFactory.getService(ApiService.class)
                .queryCarMonitorByPlate(plate1, plate2)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(context.<ResponseBody>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            List<MonitorBean> list = JsonUtils.getCarMonitorList(json);
                            view.showMonitorList(list);
                        } else {
                            view.onFailed("查询为空");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        view.onFailed(error);
                    }
                });
    }

    @Override
    public void getRecordList(String truckNo1, String truckNo2, int page, int rows) {
        ServiceFactory.getService(ApiService.class)
                .queryCarRecordByPlate(truckNo1, truckNo2, page, rows)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(context.<ResponseBody>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            List<RecordBean> list = JsonUtils.getCarRecordList(json);
                            int total = JsonUtils.getTotal(json);
                            view.showRecordList(list, total);
                        } else {
                            view.onFailed("查询为空");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        view.onFailed(error);
                    }
                });
    }

    @Override
    public void getSecurityList(String plate1, String plate2, int page, int rows) {
        ServiceFactory.getService(ApiService.class)
                .querySecurityList(plate1, plate2, page, rows)
                .compose(SchedulersHelper.<ResponseBody>io_main())
                .compose(context.<ResponseBody>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(String json) {
                        if (Constant.SUCCESS == JsonUtils.getBusiCode(json)) {
                            List<SecurityBean> list = JsonUtils.getSecurityList(json);
                            int total = JsonUtils.getTotal(json);
                            view.showSecurityList(list, total);
                        } else {
                            view.onFailed("查询为空");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        view.onFailed(error);
                    }
                });
    }
}
