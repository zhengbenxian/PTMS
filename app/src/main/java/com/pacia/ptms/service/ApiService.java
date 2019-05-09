package com.pacia.ptms.service;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by p on 2018/05/02.
 */

public interface ApiService {
    //用户登录
    @FormUrlEncoded
    @POST(BaseUrl.userLogin)
    Observable<ResponseBody> login(@Field("loginName") String userName,
                                   @Field("password") String passWord,
                                   @Field("captcha") String captcha);

    //用户退出
    @GET(BaseUrl.userLoginOut)
    Observable<ResponseBody> loginOut();

    //查询承运商列表
    @GET(BaseUrl.queryCarrierList)
    Observable<ResponseBody> queryCarrierList(@Query("type") String type,
                                              @Query("page") int page,
                                              @Query("rows") int rows);

    //承运商 查询基本信息
    @GET(BaseUrl.queryCarrierInfoByCarr)
    Observable<ResponseBody> queryCarrierInfoByCarr(@Query("gid") String gid);

    //承运商 查询人员列表
    @GET(BaseUrl.queryPersonListByCarr)
    Observable<ResponseBody> queryPersonListByCarr(@Query("gid") String gid);

    //省公司 油站油库  查询承运商基本信息
    @GET(BaseUrl.queryCarrierInfoByPri)
    Observable<ResponseBody> queryCarrierByPri(@Query("gid") String gid);

    //省公司 油站油库 查询承运商人员列表
    @GET(BaseUrl.queryPersonListByPri)
    Observable<ResponseBody> queryPersonListByPri(@Query("carrierGid") String gid);

    //根据gid 查询人员详细信息
    @GET(BaseUrl.queryPersonInfoByGid)
    Observable<ResponseBody> queryPersonInfoByGid(@Query("staffGid") String gid);

    //根据gid 查询承运商车辆列表
    @GET(BaseUrl.queryCarListByCarr)
    Observable<ResponseBody> queryCarListByCarr(@Query("carrierGid") String gid,
                                                @Query("page") int page,
                                                @Query("rows") int rows);

    //查询车辆基本信息和资质
    @GET(BaseUrl.queryCarInfoByGid)
    Observable<ResponseBody> queryCarInfoByGid(@Query("cNo") String cNo,
                                               @Query("CnoT") String CnoT);

    //查询车辆车载监控
    @GET(BaseUrl.queryCarMonitorByPlate)
    Observable<ResponseBody> queryCarMonitorByPlate(@Query("plateNo1") String plateNo1,
                                                    @Query("plateNo2") String plateNo2);

    //查询车辆车载监控
    @GET(BaseUrl.queryCarRecordByPlate)
    Observable<ResponseBody> queryCarRecordByPlate(@Query("truckNo1") String truckNo1,
                                                   @Query("truckNo2") String truckNo2,
                                                   @Query("page") int page,
                                                   @Query("rows") int rows);

    //查询车辆运行记录的详情信息
    @GET(BaseUrl.queryTransRecordInfo)
    Observable<ResponseBody> queryTransRecordInfo(@Query("planGid") String planGid);

    //查询发班信息车辆资质和人员资质信息
    @GET(BaseUrl.queryDispatchAptitudeInfo)
    Observable<ResponseBody> queryDispatchAptitude(@Query("truckNo1") String truckNo1,
                                                   @Query("truckNo2") String truckNo2,
                                                   @Query("perGid1") String perGid1,
                                                   @Query("perGid2") String perGid2);

    //查询车辆安全违规列表
    @GET(BaseUrl.querySecurityList)
    Observable<ResponseBody> querySecurityList(@Query("truckNo1") String truckNo1,
                                               @Query("truckNo2") String truckNo2,
                                               @Query("page") int page,
                                               @Query("rows") int rows);

    //查询车辆安全违规详情
    @GET(BaseUrl.querySecurityInfo)
    Observable<ResponseBody> querySecurityInfo(@Query("violatGid") String violatGid);

    //查询车辆安全违规详情
    @GET(BaseUrl.queryWarningList)
    Observable<ResponseBody> queryWarningList(@Query("warningType") String warningType,
                                              @Query("page") int page,
                                              @Query("rows") int rows);

    //查询非法登车详情
    @GET(BaseUrl.queryWarningIllegal)
    Observable<ResponseBody> queryWarningIllegal(@Query("warningGid") String gid);

    //查询路线偏离详情
    @GET(BaseUrl.queryWarningRote)
    Observable<ResponseBody> queryWarningRote(@Query("warningGid") String gid);

    //查询超时停车详情
    @GET(BaseUrl.queryWarningOverTime)
    Observable<ResponseBody> queryWarningOverTime(@Query("warningGid") String gid);

    //查询承运商安全统计
    @GET(BaseUrl.queryStatisticsByCarrier)
    Observable<ResponseBody> queryStatisticsByCarrier(@Query("type") String type,
                                                      @Query("yearMonth") String yearMonth,
                                                      @Query("page") int page,
                                                      @Query("rows") int rows);

    //查询承运商安全统计
    @GET(BaseUrl.queryStatisticsByTool)
    Observable<ResponseBody> queryStatisticsByTool(@Query("type") String type,
                                                   @Query("yearMonth") String yearMonth,
                                                   @Query("page") int page,
                                                   @Query("rows") int rows);

    //首页入口违章违规
    @GET(BaseUrl.queryViolaReguList)
    Observable<ResponseBody> queryViolaReguList(@Query("checkTime") String checkTime,
                                                @Query("checkTimeEnd") String checkTimeEnd,
                                                @Query("wgGrade") String wgGrade,
                                                @Query("status") String status,
                                                @Query("page") int page,
                                                @Query("rows") int rows);

    //首页入口运输监管
    @GET(BaseUrl.queryTransSuperList)
    Observable<ResponseBody> queryTransSuperList(@Query("bDate") String bDate,
                                                 @Query("bDateEnd") String bDateEnd,
                                                 @Query("status") String status,
                                                 @Query("page") int page,
                                                 @Query("rows") int rows);

    //首页入口运输监管
    @GET(BaseUrl.querySafeCheckType)
    Observable<ResponseBody> querySafeCheckType();

    //根据车牌号查询检查的信息
    @GET(BaseUrl.queryCheckMsgByPlate)
    Observable<ResponseBody> queryCheckMsgByPlate(@Query("trunkNo") String trunkNo);

    //判断资质信息是否一致
    @FormUrlEncoded
    @POST(BaseUrl.updateByExampleSelective)
    Observable<ResponseBody> updateByExampleSelective(@Field("userGid") String userGid,
                                                      @Field("dyHeaderGid") String gid,
                                                      @Field("gcStatus") String gcStatus,
                                                      @Field("qycStatus") String qycStatus,
                                                      @Field("siStatus") String siStatus,
                                                      @Field("syStatus") String syStatus);

    //提交整改
    @POST(BaseUrl.updateReform)
    Observable<ResponseBody> updateReform(@PartMap Map<String, RequestBody> map,
                                          @Part MultipartBody.Part[] part);

    //违规单创建
    @Multipart
    @POST(BaseUrl.createViola)
    Observable<ResponseBody> createViola(@PartMap Map<String,RequestBody> map,
                                         @Part MultipartBody.Part[] part);

    //油库审核
    @FormUrlEncoded
    @POST(BaseUrl.oilWareHouseViola)
    Observable<ResponseBody> oilWareHouseViola(@Field("userGid") String userGid,
                                               @Field("updateByViolatGid") String gid,
                                               @Field("violatStatus") String violatStatus);

    //选择人员
    @FormUrlEncoded
    @POST(BaseUrl.ChosePerson)
    Observable<ResponseBody> chosePerson(@Field("dyHeaderGid") String dyHeaderGid,
                                         @Field("driver") String driver,
                                         @Field("escort") String escort,
                                         @Field("userGid") String userGid);
}
