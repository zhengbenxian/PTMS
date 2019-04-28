package com.pacia.ptms.bean;

import java.io.Serializable;

/**
 * 公共bean类
 * Created by p on 2019/03/01.
 */

public class CommonBean implements Serializable {
    private int gid;
    private String sGid;
    private String name;

    public String getsGid() {
        return sGid;
    }

    public void setsGid(String sGid) {
        this.sGid = sGid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getGid() {
        return gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
