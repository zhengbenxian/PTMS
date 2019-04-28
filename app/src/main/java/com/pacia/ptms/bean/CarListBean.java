package com.pacia.ptms.bean;

import java.io.Serializable;

/**
 * 车辆列表信息
 * Created by p on 2019/03/05.
 */

public class CarListBean implements Serializable {
    private String gid;
    private String plateCode;
    private String engineCode;
    private String plateNo;
    private String plateNo2;
    private String hgid;
    private String truckType;
    private String remarks;
    private int violatNum;
    private String sraf1;
    private int planNum;
    private int violatAllNum;
    private String status;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getPlateCode() {
        return plateCode;
    }

    public void setPlateCode(String plateCode) {
        this.plateCode = plateCode;
    }

    public String getEngineCode() {
        return engineCode;
    }

    public void setEngineCode(String engineCode) {
        this.engineCode = engineCode;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getPlateNo2() {
        return plateNo2;
    }

    public void setPlateNo2(String plateNo2) {
        this.plateNo2 = plateNo2;
    }

    public String getHgid() {
        return hgid;
    }

    public void setHgid(String hgid) {
        this.hgid = hgid;
    }

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getViolatNum() {
        return violatNum;
    }

    public void setViolatNum(int violatNum) {
        this.violatNum = violatNum;
    }

    public String getSraf1() {
        return sraf1;
    }

    public void setSraf1(String sraf1) {
        this.sraf1 = sraf1;
    }

    public int getPlanNum() {
        return planNum;
    }

    public void setPlanNum(int planNum) {
        this.planNum = planNum;
    }

    public int getViolatAllNum() {
        return violatAllNum;
    }

    public void setViolatAllNum(int violatAllNum) {
        this.violatAllNum = violatAllNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
