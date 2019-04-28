package com.pacia.ptms.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 运行记录
 * Created by p on 2019/04/03.
 */

public class RecordBean implements Serializable {
    private String dyHeaderGid;
    //调运计划号
    private String dyHeaderTransportNo;
    //油品名称
    private String dyHeaderOilName;
    private String dyDetailGasStation;
    //业务日期B：business
    private String dyHeaderBDate;
    //提油截至日期
    private String dyHeaderOilOutEDate;
    //出库类型
    private String dyHeaderOilOutType;
    //车船编号T:truck,S:ship    存放牵引车，或者货车车牌号
    private String dyHeaderTsNo1;
    //车船编号T:truck,S:ship   存放挂车
    private String dyHeaderTsNo2;
    //发油单位Gid
    private String dyHeaderOutWarehouseGid;
    //提油单位名称
    private String gasStation;
    private String gasStationNum;
    //发油单位名称
    private String dyHeaderOutWarehouseName;
    //发油港站Gid
    private String dyHeaderOutPortGid;
    //发油港名称
    private String dyHeaderOutPortName;
    //所属单位
    private String dyHeaderAffiliation;
    //运输方式
    private String dyHeaderTransportType;
    //司机
    private String dyHeaderDriver;
    private String dyHeaderDriverGid;
    //承运商
    private String dyHeaderCarriarGid;
    private String dyHeaderCarriarName;
    private String dyHeaderSpec;
    private String dyHeaderWarehouseNo;
    //状态
    private String dyHeaderStatus;
    private String dyHeaderRemarks;
    private String dyjdStatus;
    //司押
    private String dyHeaderSrf1;
    private String dyHeaderSrf1Gid;
    //司机是否一致
    private String dyHeaderSrf2;
    //司押是否一致
    private String dyHeaderSrf3;
    //车船编号1是否一致
    private String dyHeaderSrf4;
    //车船编号2是否一致
    private String dyHeaderSrf5;
    private String dyHeaderSrf6;
    private String dyHeaderSrf7;
    private String dyHeaderSrf8;
    //运输结束日期
    private String dyHeaderSrf9;
    private String dyHeaderSrf10;
    //牵引车违规数量
    private String qycNum;
    //挂车违规数量
    private String gcNum;
    //违规单gid
    private String wfgzGid;
    //违规单gid
    private String wfgzGid1;
    private List<TransProgressBean> progressList;

    public List<TransProgressBean> getProgressList() {
        return progressList;
    }

    public String getWfgzGid1() {
        return wfgzGid1;
    }

    public void setWfgzGid1(String wfgzGid1) {
        this.wfgzGid1 = wfgzGid1;
    }

    public String getWfgzGid() {
        return wfgzGid;
    }

    public void setWfgzGid(String wfgzGid) {
        this.wfgzGid = wfgzGid;
    }

    public String getQycNum() {
        return qycNum;
    }

    public void setQycNum(String qycNum) {
        this.qycNum = qycNum;
    }

    public String getGcNum() {
        return gcNum;
    }

    public void setGcNum(String gcNum) {
        this.gcNum = gcNum;
    }

    public void setProgressList(List<TransProgressBean> progressList) {
        this.progressList = progressList;
    }

    public String getGasStationNum() {
        return gasStationNum;
    }

    public void setGasStationNum(String gasStationNum) {
        this.gasStationNum = gasStationNum;
    }

    public String getDyHeaderDriverGid() {
        return dyHeaderDriverGid;
    }

    public void setDyHeaderDriverGid(String dyHeaderDriverGid) {
        this.dyHeaderDriverGid = dyHeaderDriverGid;
    }

    public String getDyHeaderCarriarName() {
        return dyHeaderCarriarName;
    }

    public void setDyHeaderCarriarName(String dyHeaderCarriarName) {
        this.dyHeaderCarriarName = dyHeaderCarriarName;
    }

    public String getDyHeaderSrf1Gid() {
        return dyHeaderSrf1Gid;
    }

    public void setDyHeaderSrf1Gid(String dyHeaderSrf1Gid) {
        this.dyHeaderSrf1Gid = dyHeaderSrf1Gid;
    }

    public String getDyDetailGasStation() {
        return dyDetailGasStation;
    }

    public void setDyDetailGasStation(String dyDetailGasStation) {
        this.dyDetailGasStation = dyDetailGasStation;
    }

    public String getDyjdStatus() {
        return dyjdStatus;
    }

    public void setDyjdStatus(String dyjdStatus) {
        this.dyjdStatus = dyjdStatus;
    }

    public String getDyHeaderGid() {
        return dyHeaderGid;
    }

    public void setDyHeaderGid(String dyHeaderGid) {
        this.dyHeaderGid = dyHeaderGid;
    }

    public String getDyHeaderTransportNo() {
        return dyHeaderTransportNo;
    }

    public void setDyHeaderTransportNo(String dyHeaderTransportNo) {
        this.dyHeaderTransportNo = dyHeaderTransportNo;
    }

    public String getDyHeaderOilName() {
        return dyHeaderOilName;
    }

    public void setDyHeaderOilName(String dyHeaderOilName) {
        this.dyHeaderOilName = dyHeaderOilName;
    }

    public String getDyHeaderBDate() {
        return dyHeaderBDate;
    }

    public void setDyHeaderBDate(String dyHeaderBDate) {
        this.dyHeaderBDate = dyHeaderBDate;
    }

    public String getDyHeaderOilOutEDate() {
        return dyHeaderOilOutEDate;
    }

    public void setDyHeaderOilOutEDate(String dyHeaderOilOutEDate) {
        this.dyHeaderOilOutEDate = dyHeaderOilOutEDate;
    }

    public String getDyHeaderOilOutType() {
        return dyHeaderOilOutType;
    }

    public void setDyHeaderOilOutType(String dyHeaderOilOutType) {
        this.dyHeaderOilOutType = dyHeaderOilOutType;
    }

    public String getDyHeaderTsNo1() {
        return dyHeaderTsNo1;
    }

    public void setDyHeaderTsNo1(String dyHeaderTsNo1) {
        this.dyHeaderTsNo1 = dyHeaderTsNo1;
    }

    public String getDyHeaderTsNo2() {
        return dyHeaderTsNo2;
    }

    public void setDyHeaderTsNo2(String dyHeaderTsNo2) {
        this.dyHeaderTsNo2 = dyHeaderTsNo2;
    }

    public String getDyHeaderOutWarehouseGid() {
        return dyHeaderOutWarehouseGid;
    }

    public void setDyHeaderOutWarehouseGid(String dyHeaderOutWarehouseGid) {
        this.dyHeaderOutWarehouseGid = dyHeaderOutWarehouseGid;
    }

    public String getGasStation() {
        return gasStation;
    }

    public void setGasStation(String gasStation) {
        this.gasStation = gasStation;
    }

    public String getDyHeaderOutWarehouseName() {
        return dyHeaderOutWarehouseName;
    }

    public void setDyHeaderOutWarehouseName(String dyHeaderOutWarehouseName) {
        this.dyHeaderOutWarehouseName = dyHeaderOutWarehouseName;
    }

    public String getDyHeaderOutPortGid() {
        return dyHeaderOutPortGid;
    }

    public void setDyHeaderOutPortGid(String dyHeaderOutPortGid) {
        this.dyHeaderOutPortGid = dyHeaderOutPortGid;
    }

    public String getDyHeaderOutPortName() {
        return dyHeaderOutPortName;
    }

    public void setDyHeaderOutPortName(String dyHeaderOutPortName) {
        this.dyHeaderOutPortName = dyHeaderOutPortName;
    }

    public String getDyHeaderAffiliation() {
        return dyHeaderAffiliation;
    }

    public void setDyHeaderAffiliation(String dyHeaderAffiliation) {
        this.dyHeaderAffiliation = dyHeaderAffiliation;
    }

    public String getDyHeaderTransportType() {
        return dyHeaderTransportType;
    }

    public void setDyHeaderTransportType(String dyHeaderTransportType) {
        this.dyHeaderTransportType = dyHeaderTransportType;
    }

    public String getDyHeaderDriver() {
        return dyHeaderDriver;
    }

    public void setDyHeaderDriver(String dyHeaderDriver) {
        this.dyHeaderDriver = dyHeaderDriver;
    }

    public String getDyHeaderCarriarGid() {
        return dyHeaderCarriarGid;
    }

    public void setDyHeaderCarriarGid(String dyHeaderCarriarGid) {
        this.dyHeaderCarriarGid = dyHeaderCarriarGid;
    }

    public String getDyHeaderSpec() {
        return dyHeaderSpec;
    }

    public void setDyHeaderSpec(String dyHeaderSpec) {
        this.dyHeaderSpec = dyHeaderSpec;
    }

    public String getDyHeaderWarehouseNo() {
        return dyHeaderWarehouseNo;
    }

    public void setDyHeaderWarehouseNo(String dyHeaderWarehouseNo) {
        this.dyHeaderWarehouseNo = dyHeaderWarehouseNo;
    }

    public String getDyHeaderStatus() {
        return dyHeaderStatus;
    }

    public void setDyHeaderStatus(String dyHeaderStatus) {
        this.dyHeaderStatus = dyHeaderStatus;
    }

    public String getDyHeaderRemarks() {
        return dyHeaderRemarks;
    }

    public void setDyHeaderRemarks(String dyHeaderRemarks) {
        this.dyHeaderRemarks = dyHeaderRemarks;
    }

    public String getDyHeaderSrf1() {
        return dyHeaderSrf1;
    }

    public void setDyHeaderSrf1(String dyHeaderSrf1) {
        this.dyHeaderSrf1 = dyHeaderSrf1;
    }

    public String getDyHeaderSrf2() {
        return dyHeaderSrf2;
    }

    public void setDyHeaderSrf2(String dyHeaderSrf2) {
        this.dyHeaderSrf2 = dyHeaderSrf2;
    }

    public String getDyHeaderSrf3() {
        return dyHeaderSrf3;
    }

    public void setDyHeaderSrf3(String dyHeaderSrf3) {
        this.dyHeaderSrf3 = dyHeaderSrf3;
    }

    public String getDyHeaderSrf4() {
        return dyHeaderSrf4;
    }

    public void setDyHeaderSrf4(String dyHeaderSrf4) {
        this.dyHeaderSrf4 = dyHeaderSrf4;
    }

    public String getDyHeaderSrf5() {
        return dyHeaderSrf5;
    }

    public void setDyHeaderSrf5(String dyHeaderSrf5) {
        this.dyHeaderSrf5 = dyHeaderSrf5;
    }

    public String getDyHeaderSrf6() {
        return dyHeaderSrf6;
    }

    public void setDyHeaderSrf6(String dyHeaderSrf6) {
        this.dyHeaderSrf6 = dyHeaderSrf6;
    }

    public String getDyHeaderSrf7() {
        return dyHeaderSrf7;
    }

    public void setDyHeaderSrf7(String dyHeaderSrf7) {
        this.dyHeaderSrf7 = dyHeaderSrf7;
    }

    public String getDyHeaderSrf8() {
        return dyHeaderSrf8;
    }

    public void setDyHeaderSrf8(String dyHeaderSrf8) {
        this.dyHeaderSrf8 = dyHeaderSrf8;
    }

    public String getDyHeaderSrf9() {
        return dyHeaderSrf9;
    }

    public void setDyHeaderSrf9(String dyHeaderSrf9) {
        this.dyHeaderSrf9 = dyHeaderSrf9;
    }

    public String getDyHeaderSrf10() {
        return dyHeaderSrf10;
    }

    public void setDyHeaderSrf10(String dyHeaderSrf10) {
        this.dyHeaderSrf10 = dyHeaderSrf10;
    }
}
