package com.pacia.ptms.service;

/**
 * Created by p on 2018/05/02.
 */

public class BaseUrl {
    //谷平
    public static final String BASE_URL = "http://192.168.1.102:19011/potms/";
    //测试服务器
//    public static final String BASE_URL = "http://192.168.1.8:19011/potms/";
    //Main
//    public static final String MAIN = "pk=main&sub_pk=main&compress_mode=0&ENCODE_SC=0";
    //登录
    public static final String userLogin = "user/login/checkUserLogin?";
    //退出登录
    public static final String userLoginOut = "user/login/logout?";
    //查询承运商列表
    public static final String queryCarrierList = "m/cair/bCarrier/queryCarrierByType?";
    //承运商 查询基本信息
    public static final String queryCarrierInfoByCarr = "m/cair/bCarrier/selectInfoByGid?";
    //承运商 查询人员列表
    public static final String queryPersonListByCarr = "m/cair/bCarirStaff/selectPersonInfor?";
    //省公司 油站油库 查询承运商基本信息
    public static final String queryCarrierInfoByPri = "m/cair/bCarrier/selectByPrimaryKey?";
    //省公司 油站油库 查询承运商人员列表
    public static final String queryPersonListByPri = "m/cair/bCarirStaff/selectSGSInfor?";
    //根据gid 查询人员详细信息
    public static final String queryPersonInfoByGid = "m/cair/bCarirStaff/selectPersonInforByGid?";
    //根据gid 查询承运商车辆列表
    public static final String queryCarListByCarr = "m/cair/bCarirTruck/querySgsTruckByCarrierGid?";
    //车辆基本信息和资质
    public static final String queryCarInfoByGid = "m/cair/bCarirLience/selectTPByHGID?";
    //查询车辆车载监控
    public static final String queryCarMonitorByPlate = "m/cair/bTruckCamera/getBytrunkGid?";
    //查询车辆运行记录
    public static final String queryCarRecordByPlate = "m/cair/bCarirTruck/queryTransportRecordByTruckNo?";
    //查询车辆运行记录的详情信息
    public static final String queryTransRecordInfo = "m/cair/bCarirTruck/queryTransRecordByPlanGid?";
    //查询发班信息车辆资质和人员资质信息
    public static final String queryDispatchAptitudeInfo = "m/cair/bCarirTruck/queryAptitudeByCondition?";
    //查询车辆安全违规列表
    public static final String querySecurityList = "m/check/bCheckViolat/selectByCondition?";
    //查询车辆安全违规详情
    public static final String querySecurityInfo = "m/check/bCheckViolat/queryInfoByViolatGid?";
    //查询预警列表
    public static final String queryWarningList = "m/warning/bWarning/warningList?";
    //查询非法登车详情
    public static final String queryWarningIllegal = "m/warning/bWarning/bWMonitorDetail?";
    //查询路线偏离详情
    public static final String queryWarningRote = "m/warning/bWarning/bWTrajectoryDetail?";
    //查询超时停车详情
    public static final String queryWarningOverTime = "m/warning/bWarning/warningOvertimeDetail?";
    //承运商安全统计
    public static final String queryStatisticsByCarrier = "m/cair/bCarrier/queryCarrierCapacityByType?";
    //运输工具安全统计
    public static final String queryStatisticsByTool = "m/cair/bCarirTruck/queryTruckCapacityByType?";
    //首页入口违章违规
    public static final String queryViolaReguList = "m/check/bCheckViolat/selectListByRecord?";
    //首页入口运输监管
    public static final String queryTransSuperList = "m/transport/bTransportPlan/selectBTransportPlanList?";
    //获取安全检查类型
    public static final String querySafeCheckType = "m/check/bCheckRule/selectByTrunkNo?";
    //根据车牌号查询审核的信息
    public static final String queryCheckMsgByPlate = "m/transport/bTransportPlan/selectInfoByPlanNo?";
    //判断资质信息是否一致
    public static final String updateByExampleSelective = "m/transport/bTransportPlan/updateByCondition?";
    //提交整改
    public static final String updateReform = "";
    //违规单创建
    public static final String createViola = "m/check/bCheckViolat/insertSelective?";
    //油库审核
    public static final String oilWareHouseViola = "m/check/bCheckViolat/updateByViolatGid?";
    //承运商选择人员提交
    public static final String ChosePerson = "m/transport/bTransportPlan/updateByPlanGid?";
}
