package com.pacia.ptms.carrier.car;

import com.pacia.ptms.bean.MonitorBean;
import com.pacia.ptms.bean.RecordBean;
import com.pacia.ptms.bean.SecurityBean;

import java.util.List;

/**
 * Created by p on 2019/04/03.
 */

public class CarOtherInterface {
    interface View {
        void showMonitorList(List<MonitorBean> list);

        void showRecordList(List<RecordBean> list, int total);

        void showSecurityList(List<SecurityBean> list, int total);

        void onFailed(String msg);
    }

    interface Presenter {
        void getMonitorList(String plate1, String plate2);

        void getRecordList(String truckNo1, String truckNo2, int page, int rows);

        void getSecurityList(String plate1, String plate2, int page, int rows);
    }
}
