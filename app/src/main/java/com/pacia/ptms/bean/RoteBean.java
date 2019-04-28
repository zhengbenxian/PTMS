package com.pacia.ptms.bean;

import java.io.Serializable;

/**
 * 路线偏离
 * Created by p on 2019/04/12.
 */

public class RoteBean implements Serializable {
    private String plgid;
    private String warningNo;
    private String positionLong;
    private String positionLat;
    private String remarks;
    private String insertDate;
    private String creUGid;
    private String srf1;
    private String srf2;
    private String srf3;
    private String srf4;
    private String srf5;

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getPlgid() {
        return plgid;
    }

    public void setPlgid(String plgid) {
        this.plgid = plgid;
    }

    public String getWarningNo() {
        return warningNo;
    }

    public void setWarningNo(String warningNo) {
        this.warningNo = warningNo;
    }

    public String getPositionLong() {
        return positionLong;
    }

    public void setPositionLong(String positionLong) {
        this.positionLong = positionLong;
    }

    public String getPositionLat() {
        return positionLat;
    }

    public void setPositionLat(String positionLat) {
        this.positionLat = positionLat;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreUGid() {
        return creUGid;
    }

    public void setCreUGid(String creUGid) {
        this.creUGid = creUGid;
    }

    public String getSrf1() {
        return srf1;
    }

    public void setSrf1(String srf1) {
        this.srf1 = srf1;
    }

    public String getSrf2() {
        return srf2;
    }

    public void setSrf2(String srf2) {
        this.srf2 = srf2;
    }

    public String getSrf3() {
        return srf3;
    }

    public void setSrf3(String srf3) {
        this.srf3 = srf3;
    }

    public String getSrf4() {
        return srf4;
    }

    public void setSrf4(String srf4) {
        this.srf4 = srf4;
    }

    public String getSrf5() {
        return srf5;
    }

    public void setSrf5(String srf5) {
        this.srf5 = srf5;
    }
}
