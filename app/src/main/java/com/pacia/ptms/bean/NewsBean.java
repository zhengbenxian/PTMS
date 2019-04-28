package com.pacia.ptms.bean;

import java.io.Serializable;

/**
 * 首页新闻
 * Created by p on 2019/03/14.
 */

public class NewsBean implements Serializable {
    private String title;
    //简介
    private String pithy;
    private String newsUrl;
    private String comeFrom;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPithy() {
        return pithy;
    }

    public void setPithy(String pithy) {
        this.pithy = pithy;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
    }
}
