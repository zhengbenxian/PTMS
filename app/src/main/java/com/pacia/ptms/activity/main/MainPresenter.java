package com.pacia.ptms.activity.main;

import com.pacia.ptms.R;
import com.pacia.ptms.bean.CommonBean;
import com.pacia.ptms.bean.NewsBean;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p on 2019/03/12.
 */

public class MainPresenter implements MainInterface.Presenter {
    private MainInterface.View view;
    private RxFragment context;
    List<String> titles = new ArrayList<>();
    List<Integer> paths = new ArrayList<>();
    private List<CommonBean> list_gv = new ArrayList<>();
    private List<NewsBean> list_news = new ArrayList<>();

    private int[] gv_main_item = {R.string.shipping_manager, R.string.transport_super,
            R.string.safety_statistic, R.string.viola_regulation, R.string.person_quali,
            R.string.ppt, R.string.person_award, R.string.more};

    private int[] gv_main_icon = {R.mipmap.shipping_manager, R.mipmap.transport_super,
            R.mipmap.safety_statistic, R.mipmap.viola_regulation, R.mipmap.person_quali,
            R.mipmap.ppt, R.mipmap.person_award, R.mipmap.more};

    public MainPresenter(RxFragment context, MainInterface.View view) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void getBanner() {
//        titles.clear();
//        paths.clear();
        titles.add("1");
        titles.add("2");
        paths.add(R.mipmap.img1);
        paths.add(R.mipmap.img2);
        view.showBanner(titles, paths);
    }

    @Override
    public void getGridData() {
        for (int i = 0; i < gv_main_icon.length; i++) {
            CommonBean gv = new CommonBean();
            gv.setGid(gv_main_icon[i]);
            gv.setName(context.getString(gv_main_item[i]));
            list_gv.add(gv);
        }
        view.showGrid(list_gv);
    }

    @Override
    public void getNewsData() {
        NewsBean nb1 = new NewsBean();
        nb1.setTitle("内蒙古化工厂爆燃事故遇难人数增至4人");
        nb1.setNewsUrl("https://baijiahao.baidu.com/s?id=1631691845418728505&wfr=spider&for=pc&qq-pf-to=pcqq.temporaryc2c");
        nb1.setPithy("4月24日下午，北京青年报记者从卓资县424伊东东兴化工厂爆燃事故处置工作领导小组了解到，4 月 24 日凌晨 2 时 55 分，位于乌兰察布市卓资县旗下营镇的内蒙古伊东集团东兴化工有限责任公司 PVC 车间发生爆燃事故");
        nb1.setComeFrom("Qnews");
        NewsBean nb2 = new NewsBean();
        nb2.setTitle("内蒙古卓资县一化工厂发生爆燃事故 系当地最大工业项目");
        nb2.setNewsUrl("https://baijiahao.baidu.com/s?id=1631660807413569419&wfr=spider&for=pc&qq-pf-to=pcqq.temporaryc2c");
        nb2.setPithy("新华社消息，4月24日2时55分许，位于乌兰察布市卓资县境内的内蒙古伊东集团东兴化工有限责任公司（以下简称东兴化工）一车间发生爆燃");
        nb2.setComeFrom("每日经济新闻");
        NewsBean nb3 = new NewsBean();
        nb3.setTitle("化工企业为何频发安全事故？专家详解");
        nb3.setNewsUrl("https://baijiahao.baidu.com/s?id=1631467813287413074&wfr=spider&for=pc&qq-pf-to=pcqq.temporaryc2c");
        nb3.setPithy("《法制日报》记者了解到，齐鲁天和惠世制药有限公司(以下简称天和惠世公司)在这次火灾事故前，就曾多次发生爆炸事故。");
        nb3.setComeFrom("环球网");
//        NewsBean nb4 = new NewsBean();
//        nb4.setTitle("江苏泰兴市一化工厂发生火灾事故");
//        nb4.setNewsUrl("http://info.fire.hc360.com/2019/04/2316181114657.shtml");
//        nb4.setPithy(" 4月3日，《每日经济新闻》记者从泰兴市政府有关部门获悉，晚上9时左右，当地经济开发区内一家名为江苏中丹化工技术有限公司（以下简称中丹化工）发生火灾事故，未造成人员伤亡。");
//        nb4.setComeFrom("每日经济新闻");
        NewsBean nb5 = new NewsBean();
        nb5.setTitle("江苏昆山化工厂爆燃事故续：废物暂存仓库设置不合理");
        nb5.setNewsUrl("https://baijiahao.baidu.com/s?id=1630430429488415357&wfr=spider&for=pc&qq-pf-to=pcqq.temporaryc2c");
        nb5.setPithy("新京报讯（记者 刘名洋）今日（4月10日），从江苏省安全生产委员会办公室获悉，3月31日昆山汉鼎精密金属有限公司爆燃事故造成7人死亡、5人受伤的原因。");
        nb5.setComeFrom("新京报");
        NewsBean nb6 = new NewsBean();
        nb6.setTitle("生态环境部通报3·21响水爆炸事故");
        nb6.setNewsUrl("http://news.chinaxiaokang.com/shehuipindao/minsheng/20190329/653480.html");
        nb6.setPithy("22日凌晨，作为省级紧急医疗救援小组一部分的心理危机干预小组进驻伤者接收医院，国家卫健委也派出了专家进行支援。两批25名国家和省心理、精神科专家会同当地卫生专业人员迅速建立心理危机干预小组。");
        nb6.setComeFrom("新华网");
        list_news.add(nb1);
        list_news.add(nb2);
        list_news.add(nb3);
//        list_news.add(nb4);
        list_news.add(nb5);
        list_news.add(nb6);
        view.showNews(list_news);
    }
}
