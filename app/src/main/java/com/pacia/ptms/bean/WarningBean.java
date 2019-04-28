package com.pacia.ptms.bean;

import java.io.Serializable;

/**
 * 预警
 * Created by p on 2019/04/09.
 */

public class WarningBean implements Serializable {
    private String gid;
    private String warningNo;
    private String transportNo;
    private String warningType;
    private String warningTypeName;
    private String tsNo;
    private String positionLong;
    private String positionLat;
    private String warningDate;
    private String remarks;
    //挂车车牌号
    private String srf1;
    //停车时长
    private String srf2;
    private String srf3;
    private String srf4;
    private String srf5;
    private String gasStation;
    private String outWarehouseName;
    private String staffName;
    private String staffPhone;
    private String staffName1;
    private String staffPhone1;
    private String staffType;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getWarningNo() {
        return warningNo;
    }

    public void setWarningNo(String warningNo) {
        this.warningNo = warningNo;
    }

    public String getTransportNo() {
        return transportNo;
    }

    public void setTransportNo(String transportNo) {
        this.transportNo = transportNo;
    }

    public String getWarningType() {
        return warningType;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    public String getWarningTypeName() {
        return warningTypeName;
    }

    public void setWarningTypeName(String warningTypeName) {
        this.warningTypeName = warningTypeName;
    }

    public String getTsNo() {
        return tsNo;
    }

    public void setTsNo(String tsNo) {
        this.tsNo = tsNo;
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

    public String getWarningDate() {
        return warningDate;
    }

    public void setWarningDate(String warningDate) {
        this.warningDate = warningDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getGasStation() {
        return gasStation;
    }

    public void setGasStation(String gasStation) {
        this.gasStation = gasStation;
    }

    public String getOutWarehouseName() {
        return outWarehouseName;
    }

    public void setOutWarehouseName(String outWarehouseName) {
        this.outWarehouseName = outWarehouseName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public String getStaffName1() {
        return staffName1;
    }

    public void setStaffName1(String staffName1) {
        this.staffName1 = staffName1;
    }

    public String getStaffPhone1() {
        return staffPhone1;
    }

    public void setStaffPhone1(String staffPhone1) {
        this.staffPhone1 = staffPhone1;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }
}
