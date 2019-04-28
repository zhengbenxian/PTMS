package com.pacia.ptms.bean;

import java.io.Serializable;

/**
 * 承运商
 * Created by p on 2019/03/25.
 */

public class CarrierBean implements Serializable {
    private String cysGid;
    //承运商编码
    private String cysCode;
    //承运商名称
    private String cysName;
    //简称
    private String cysSname;
    //英文名
    private String cysEname;
    //联系人
    private String cysContract;
    //联系人电话
    private String cysContractTel;
    //承运商联系电话
    private String cysCarrierTel;
    //邮箱
    private String cysEmail;
    //传真
    private String cysFax;
    //营业执照编号
    private String cysFicenseNo;
    //地址
    private String cysAddress;
    //营业执照开始日期
    private String cysLicenStartDate;
    //营业执照结束日期
    private String cysLicenEndDate;
    //企业法人
    private String cysLegalPerson;
    //审核状态（暂时可空，预留字段）
    private String cysApproveStatus;
    //承运商类型（公路运输 10，水路运输 20，公路与水路运输30）
    private String cysType;
    //营业范围
    private String cysBusinessScope;
    //统一社会信用代码
    private String cysUsci;
    //资质是否有效
    private String zzSrf1;
    //资质gid
    private String zzGid;

    public String getCysGid() {
        return cysGid;
    }

    public void setCysGid(String cysGid) {
        this.cysGid = cysGid;
    }

    public String getCysCode() {
        return cysCode;
    }

    public void setCysCode(String cysCode) {
        this.cysCode = cysCode;
    }

    public String getCysName() {
        return cysName;
    }

    public void setCysName(String cysName) {
        this.cysName = cysName;
    }

    public String getCysSname() {
        return cysSname;
    }

    public void setCysSname(String cysSname) {
        this.cysSname = cysSname;
    }

    public String getCysEname() {
        return cysEname;
    }

    public void setCysEname(String cysEname) {
        this.cysEname = cysEname;
    }

    public String getCysContract() {
        return cysContract;
    }

    public void setCysContract(String cysContract) {
        this.cysContract = cysContract;
    }

    public String getCysContractTel() {
        return cysContractTel;
    }

    public void setCysContractTel(String cysContractTel) {
        this.cysContractTel = cysContractTel;
    }

    public String getCysCarrierTel() {
        return cysCarrierTel;
    }

    public void setCysCarrierTel(String cysCarrierTel) {
        this.cysCarrierTel = cysCarrierTel;
    }

    public String getCysEmail() {
        return cysEmail;
    }

    public void setCysEmail(String cysEmail) {
        this.cysEmail = cysEmail;
    }

    public String getCysFax() {
        return cysFax;
    }

    public void setCysFax(String cysFax) {
        this.cysFax = cysFax;
    }

    public String getCysFicenseNo() {
        return cysFicenseNo;
    }

    public void setCysFicenseNo(String cysFicenseNo) {
        this.cysFicenseNo = cysFicenseNo;
    }

    public String getCysAddress() {
        return cysAddress;
    }

    public void setCysAddress(String cysAddress) {
        this.cysAddress = cysAddress;
    }

    public String getCysLicenStartDate() {
        return cysLicenStartDate;
    }

    public void setCysLicenStartDate(String cysLicenStartDate) {
        this.cysLicenStartDate = cysLicenStartDate;
    }

    public String getCysLicenEndDate() {
        return cysLicenEndDate;
    }

    public void setCysLicenEndDate(String cysLicenEndDate) {
        this.cysLicenEndDate = cysLicenEndDate;
    }

    public String getCysLegalPerson() {
        return cysLegalPerson;
    }

    public void setCysLegalPerson(String cysLegalPerson) {
        this.cysLegalPerson = cysLegalPerson;
    }

    public String getCysApproveStatus() {
        return cysApproveStatus;
    }

    public void setCysApproveStatus(String cysApproveStatus) {
        this.cysApproveStatus = cysApproveStatus;
    }

    public String getCysType() {
        return cysType;
    }

    public void setCysType(String cysType) {
        this.cysType = cysType;
    }

    public String getCysBusinessScope() {
        return cysBusinessScope;
    }

    public void setCysBusinessScope(String cysBusinessScope) {
        this.cysBusinessScope = cysBusinessScope;
    }

    public String getCysUsci() {
        return cysUsci;
    }

    public void setCysUsci(String cysUsci) {
        this.cysUsci = cysUsci;
    }

    public String getZzSrf1() {
        return zzSrf1;
    }

    public void setZzSrf1(String zzSrf1) {
        this.zzSrf1 = zzSrf1;
    }

    public String getZzGid() {
        return zzGid;
    }

    public void setZzGid(String zzGid) {
        this.zzGid = zzGid;
    }
}
