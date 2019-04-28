package com.pacia.ptms.activity.main;

import com.pacia.ptms.bean.CommonBean;
import com.pacia.ptms.bean.NewsBean;

import java.util.List;

/**
 * Created by p on 2019/03/12.
 */

public class MainInterface {
    interface View {
        void showBanner(List<String> title, List<Integer> paths);

        void showGrid(List<CommonBean> list);

        void showNews(List<NewsBean> list_car);
    }

    interface Presenter {
        void getBanner();

        void getGridData();

        void getNewsData();
    }
}
