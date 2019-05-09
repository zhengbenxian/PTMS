package com.pacia.ptms.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 人员信息
 * Created by p on 2019/03/29.
 */

public class PersonBean implements Serializable {
    //简略
    private String gid;
    private String name;
    private String mPhone;
    //详细人员信息
    private String ryGid;
    private String ryName;
    private String ryGender;
    private String ryHgid;
    private String ryHName;
    private String ryIdCardNum;
    private String ryMPhone;
    private String ryStaffType;
    private String ryQName;
    private String ryQId;
    private String ryQType;
    private String ryQFirstGotTime;
    private String ryQValidSTime;
    private String ryQValidETime;
    private String ryNation;
    private String ryDriveType;
    private String ryAdress;
    private String ryRemarks;
    private List<AptitudeBean> aptitudeList;

    public List<AptitudeBean> getAptitudeList() {
        return aptitudeList;
    }

    public void setAptitudeList(List<AptitudeBean> aptitudeList) {
        this.aptitudeList = aptitudeList;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getRyHName() {
        return ryHName;
    }

    public void setRyHName(String ryHName) {
        this.ryHName = ryHName;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRyGid() {
        return ryGid;
    }

    public void setRyGid(String ryGid) {
        this.ryGid = ryGid;
    }

    public String getRyName() {
        return ryName;
    }

    public void setRyName(String ryName) {
        this.ryName = ryName;
    }

    public String getRyGender() {
        return ryGender;
    }

    public void setRyGender(String ryGender) {
        this.ryGender = ryGender;
    }

    public String getRyHgid() {
        return ryHgid;
    }

    public void setRyHgid(String ryHgid) {
        this.ryHgid = ryHgid;
    }

    public String getRyIdCardNum() {
        return ryIdCardNum;
    }

    public void setRyIdCardNum(String ryIdCardNum) {
        this.ryIdCardNum = ryIdCardNum;
    }

    public String getRyMPhone() {
        return ryMPhone;
    }

    public void setRyMPhone(String ryMPhone) {
        this.ryMPhone = ryMPhone;
    }

    public String getRyStaffType() {
        return ryStaffType;
    }

    public void setRyStaffType(String ryStaffType) {
        this.ryStaffType = ryStaffType;
    }

    public String getRyQName() {
        return ryQName;
    }

    public void setRyQName(String ryQName) {
        this.ryQName = ryQName;
    }

    public String getRyQId() {
        return ryQId;
    }

    public void setRyQId(String ryQId) {
        this.ryQId = ryQId;
    }

    public String getRyQType() {
        return ryQType;
    }

    public void setRyQType(String ryQType) {
        this.ryQType = ryQType;
    }

    public String getRyQFirstGotTime() {
        return ryQFirstGotTime;
    }

    public void setRyQFirstGotTime(String ryQFirstGotTime) {
        this.ryQFirstGotTime = ryQFirstGotTime;
    }

    public String getRyQValidSTime() {
        return ryQValidSTime;
    }

    public void setRyQValidSTime(String ryQValidSTime) {
        this.ryQValidSTime = ryQValidSTime;
    }

    public String getRyQValidETime() {
        return ryQValidETime;
    }

    public void setRyQValidETime(String ryQValidETime) {
        this.ryQValidETime = ryQValidETime;
    }

    public String getRyNation() {
        return ryNation;
    }

    public void setRyNation(String ryNation) {
        this.ryNation = ryNation;
    }

    public String getRyDriveType() {
        return ryDriveType;
    }

    public void setRyDriveType(String ryDriveType) {
        this.ryDriveType = ryDriveType;
    }

    public String getRyAdress() {
        return ryAdress;
    }

    public void setRyAdress(String ryAdress) {
        this.ryAdress = ryAdress;
    }

    public String getRyRemarks() {
        return ryRemarks;
    }

    public void setRyRemarks(String ryRemarks) {
        this.ryRemarks = ryRemarks;
    }
}
