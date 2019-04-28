package com.pacia.ptms.utils;

import com.google.gson.Gson;
import com.pacia.ptms.R;
import com.pacia.ptms.bean.AptitudeBean;
import com.pacia.ptms.bean.CarBasicBean;
import com.pacia.ptms.bean.CarListBean;
import com.pacia.ptms.bean.CarrierBean;
import com.pacia.ptms.bean.LoginBean;
import com.pacia.ptms.bean.MonitorBean;
import com.pacia.ptms.bean.PersonBean;
import com.pacia.ptms.bean.RecordBean;
import com.pacia.ptms.bean.RoteBean;
import com.pacia.ptms.bean.SafeCheckBean;
import com.pacia.ptms.bean.SecurityBean;
import com.pacia.ptms.bean.StatisticsBean;
import com.pacia.ptms.bean.TransProgressBean;
import com.pacia.ptms.bean.TransSuperBean;
import com.pacia.ptms.bean.ViolaReformBean;
import com.pacia.ptms.bean.ViolaReguInfoBean;
import com.pacia.ptms.bean.ViolatCheckBean;
import com.pacia.ptms.bean.WarningBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析json 工具类
 * Created by p on 2018/05/21.
 */

public class JsonUtils {
    private static final String TAG = "JsonUtils";

    /**
     * 获得返回code
     *
     * @param json
     * @return
     */
    public static int getBusiCode(String json) {
        int busiCode;
        try {
            JSONObject jo = new JSONObject(json);
            busiCode = jo.getInt("busiCode");
        } catch (JSONException e) {
            e.printStackTrace();
            busiCode = -1;
        }
        return busiCode;
    }

    public static int getTotal(String json) {
        int total = getTotal(json, "total");
        return total;
    }

    public static int getTotal(String json, String key) {
        int total;
        try {
            JSONObject jo = new JSONObject(json);
            total = jo.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
            total = 0;
        }
        return total;
    }

    /**
     * 解析jsonobject
     *
     * @param json
     * @param data
     * @return
     */
    public static JSONObject getJsonData(String json, String data) {
        JSONObject obj = new JSONObject();
        try {
            JSONObject jo = new JSONObject(json);
            obj = jo.getJSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 解析jsonarray
     *
     * @param json
     * @param items
     * @return
     */
    public static JSONArray getJsonItems(String json, String items) {
        JSONArray array = null;
        try {
            JSONObject jo = new JSONObject(json);
            array = jo.getJSONArray(items);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * 获取session
     *
     * @param json
     * @return
     */
    public static String getSession(String json) {
        String code = "";
        try {
            JSONObject jo = new JSONObject(json);
            code = jo.getString("code");
            return code;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 通过gson 解析json 返货实体类
     *
     * @param json
     * @param
     * @param
     * @return
     */
    private static <T> T getGsonBean(String json, Class<T> beanClass) {
        Gson gson = new Gson();
        T t = gson.fromJson(json, beanClass);
        return t;
    }

    //解析登录数据
    public static LoginBean getLoginBean(String json) {
        LoginBean loginBean = new LoginBean();
        try {
            JSONObject object = getJsonData(json, "data");
            loginBean = getGsonBean(object.toString(), LoginBean.class);
            return loginBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginBean;
    }

    //承运商列表
    public static List<CarrierBean> getCarrierList(String json) {
        List<CarrierBean> list = new ArrayList<>();
        try {
            JSONArray array = getJsonItems(json, "items");
            for (int i = 0; i < array.length(); i++) {
                CarrierBean bean = getGsonBean(array.getString(i), CarrierBean.class);
                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //承运商基础信息
    public static CarrierBean getCarrierInfo(String json) {
        CarrierBean cb = new CarrierBean();
        try {
            JSONObject object = getJsonData(json, "data");
            cb = getGsonBean(object.toString(), CarrierBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cb;
    }

    //资质列表
    public static List<AptitudeBean> getAptitudeList(String json) {
        List<AptitudeBean> list = new ArrayList<>();
        JSONObject jo = getJsonData(json, "data");
        try {
            JSONArray array = jo.getJSONArray("bCarirLienceVo");
            for (int i = 0; i < array.length(); i++) {
                AptitudeBean bean = getGsonBean(array.getString(i), AptitudeBean.class);
                bean.setNativeUrl(R.mipmap.xuke);
                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //人员资质列表
    public static List<AptitudeBean> getPersonAptitudeList(String json) {
        List<AptitudeBean> list = new ArrayList<>();
        JSONArray array = getJsonItems(json, "items");
        try {
            if (array.length() > 0) {
                JSONObject jo = array.getJSONObject(0);
                JSONArray arr = jo.getJSONArray("bCarirLienceVo");
                for (int i = 0; i < arr.length(); i++) {
                    AptitudeBean bean = getGsonBean(arr.getString(i), AptitudeBean.class);
                    bean.setNativeUrl(R.mipmap.ry);
                    list.add(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取承运商人员类型简略列表
    public static List<PersonBean> getPersonList(String json, String key) {
        List<PersonBean> list_pb = new ArrayList<>();
        try {
            JSONObject jo = getJsonData(json, "data");
            JSONArray array = jo.getJSONArray(key);
            for (int i = 0; i < array.length(); i++) {
                PersonBean person = getGsonBean(array.getString(i), PersonBean.class);
                list_pb.add(person);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_pb;
    }

    //获取承运商人员详细信息
    public static List<PersonBean> getPersonInfo(String json) {
        List<PersonBean> list = new ArrayList<>();
        JSONArray array = getJsonItems(json, "items");
        try {
            for (int i = 0; i < array.length(); i++) {
                PersonBean pb = getGsonBean(array.getString(i), PersonBean.class);
                list.add(pb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取承运商车辆列表
    public static List<CarListBean> getCarList(String json) {
        List<CarListBean> list = new ArrayList<>();
        JSONArray array = getJsonItems(json, "items");
        try {
            for (int i = 0; i < array.length(); i++) {
                CarListBean cb = getGsonBean(array.getString(i), CarListBean.class);
                list.add(cb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取承运商车辆基本信息和资质
    public static List<CarBasicBean> getCarBasicAptiList(String json) {
        List<CarBasicBean> list = new ArrayList<>();
        JSONArray array = getJsonItems(json, "items");
        try {
            for (int i = 0; i < array.length(); i++) {
                CarBasicBean cb = getGsonBean(array.getString(i), CarBasicBean.class);
                JSONObject jo = array.getJSONObject(i);
                JSONArray ja = jo.getJSONArray("bCarirLienceVo");
                List<AptitudeBean> list_apti = new ArrayList<>();
                for (int j = 0; j < ja.length(); j++) {
                    AptitudeBean aptis = getGsonBean(ja.getString(j), AptitudeBean.class);
                    aptis.setNativeUrl(R.mipmap.xuke);
                    list_apti.add(aptis);
                }
                cb.setbCarirLienceVo(list_apti);
                list.add(cb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取车载监控列表
    public static List<MonitorBean> getCarMonitorList(String json) {
        List<MonitorBean> list = new ArrayList<>();
        JSONArray array = getJsonItems(json, "items");
        try {
            for (int i = 0; i < array.length(); i++) {
                MonitorBean mb = getGsonBean(array.getString(i), MonitorBean.class);
                list.add(mb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取运行记录列表
    public static List<RecordBean> getCarRecordList(String json) {
        List<RecordBean> list = new ArrayList<>();
        JSONArray array = getJsonItems(json, "items");
        try {
            for (int i = 0; i < array.length(); i++) {
                RecordBean mb = getGsonBean(array.getString(i), RecordBean.class);
                list.add(mb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取车辆安全违规列表
    public static List<SecurityBean> getSecurityList(String json) {
        List<SecurityBean> list = new ArrayList<>();
        JSONArray array = getJsonItems(json, "items");
        try {
            for (int i = 0; i < array.length(); i++) {
                SecurityBean mb = getGsonBean(array.getString(i), SecurityBean.class);
                list.add(mb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取运行记录详情
    public static RecordBean getTransRecordInfo(String json) {
        RecordBean recordBean = new RecordBean();
        JSONObject jsonObject = getJsonData(json, "data");
        try {
            recordBean = getGsonBean(jsonObject.toString(), RecordBean.class);
            JSONArray array = jsonObject.getJSONArray("bTransportProgressList");
            List<TransProgressBean> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                TransProgressBean bean = getGsonBean(array.getString(i), TransProgressBean.class);
                list.add(bean);
            }
            recordBean.setProgressList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recordBean;
    }

    //获取发班信息 车辆资质信息
    public static List<CarBasicBean> getDispatchCar(String json) {
        List<CarBasicBean> list = new ArrayList<>();
        JSONObject jsonObject = getJsonData(json, "data");
        try {
            JSONArray array_truck = jsonObject.getJSONArray("truck");
            for (int i = 0; i < array_truck.length(); i++) {
                CarBasicBean list_car = getGsonBean(array_truck.getString(i), CarBasicBean.class);
                list.add(list_car);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取发班信息 人员资质信息
    public static List<PersonBean> getDispatchPerson(String json) {
        List<PersonBean> list = new ArrayList<>();
        JSONObject jsonObject = getJsonData(json, "data");
        try {
            JSONArray array_staff = jsonObject.getJSONArray("staff");
            for (int i = 0; i < array_staff.length(); i++) {
                PersonBean list_person = getGsonBean(array_staff.getString(i), PersonBean.class);
                JSONObject jo = array_staff.getJSONObject(i);
                List<AptitudeBean> beans = new ArrayList<>();
                JSONArray jsonArray = jo.getJSONArray("bCarirLienceVo");
                for (int j = 0; j < jsonArray.length(); j++) {
                    AptitudeBean bean = getGsonBean(jsonArray.getString(j), AptitudeBean.class);
                    beans.add(bean);
                }
                list_person.setAptitudeList(beans);
                list.add(list_person);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取违规详细信息
    public static ViolaReguInfoBean getViolaRegu(String json) {
        ViolaReguInfoBean bean = new ViolaReguInfoBean();
        JSONObject object = getJsonData(json, "data");
        try {
            bean = getGsonBean(object.toString(), ViolaReguInfoBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    //获取违章违规列表
    public static List<ViolaReguInfoBean> getViolaReguList(String json) {
        List<ViolaReguInfoBean> list = new ArrayList<>();
        JSONArray array = getJsonItems(json, "items");
        try {
            for (int i = 0; i < array.length(); i++) {
                ViolaReguInfoBean bean = getGsonBean(array.getString(i), ViolaReguInfoBean.class);
                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取违规详细信息 整改处理
    public static List<ViolaReformBean> getViolaReformList(String json) {
        List<ViolaReformBean> list = new ArrayList<>();
        JSONObject object = getJsonData(json, "data");
        try {
            JSONArray array = object.getJSONArray("bCheckRectificationList");
            for (int i = 0; i < array.length(); i++) {
                ViolaReformBean bean = getGsonBean(array.getString(i), ViolaReformBean.class);
                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取违规详细信息 有无证照
    public static List<ViolatCheckBean> getViolaCheckList(String json) {
        List<ViolatCheckBean> list = new ArrayList<>();
        JSONObject object = getJsonData(json, "data");
        try {
            JSONArray array = object.getJSONArray("bCheckViolatRuleList");
            for (int i = 0; i < array.length(); i++) {
                ViolatCheckBean bean = getGsonBean(array.getString(i), ViolatCheckBean.class);
                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //预警列表
    public static List<WarningBean> getWarningList(String json) {
        List<WarningBean> list = new ArrayList<>();
        JSONArray array = getJsonItems(json, "items");
        try {
            for (int i = 0; i < array.length(); i++) {
                WarningBean bean = getGsonBean(array.getString(i), WarningBean.class);
                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //预警非法等车详细信息
    public static WarningBean getWarningIllegalInfo(String json) {
        WarningBean bean = new WarningBean();
        JSONObject object = getJsonData(json, "data");
        try {
            bean = getGsonBean(object.toString(), WarningBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    //非法登车详情监控列表
    public static List<MonitorBean> getIllegalMonitor(String json) {
        List<MonitorBean> list = new ArrayList<>();
        JSONObject object = getJsonData(json, "data");
        try {
            JSONArray array = object.getJSONArray("bTruckCameraVo");
            for (int i = 0; i < array.length(); i++) {
                MonitorBean bean = getGsonBean(array.getString(i), MonitorBean.class);
                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //路线偏离详情
    public static List<RoteBean> getWarningRote(String json) {
        List<RoteBean> list = new ArrayList<>();
        JSONObject object = getJsonData(json, "data");
        try {
            JSONArray array = object.getJSONArray("bWTrajectoryVo");
            for (int i = 0; i < array.length(); i++) {
                RoteBean bean = getGsonBean(array.getString(i), RoteBean.class);
                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //承运商 安全统计
    public static List<StatisticsBean> getStatisticsList(String json) {
        List<StatisticsBean> list = new ArrayList<>();
        JSONArray array = getJsonItems(json, "items");
        try {
            for (int i = 0; i < array.length(); i++) {
                StatisticsBean bean = getGsonBean(array.getString(i), StatisticsBean.class);
                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //运输监管 首页
    public static List<TransSuperBean> getTransSuperList(String json) {
        List<TransSuperBean> list = new ArrayList<>();
        JSONArray array = getJsonItems(json, "items");
        try {
            for (int i = 0; i < array.length(); i++) {
                TransSuperBean bean = getGsonBean(array.getString(i), TransSuperBean.class);
                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<SafeCheckBean> getSafeCheckList(String json) {
        List<SafeCheckBean> list = new ArrayList<>();
        JSONArray array = getJsonItems(json, "items");
        try {
            for (int i = 0; i < array.length(); i++) {
                SafeCheckBean bean = getGsonBean(array.getString(i), SafeCheckBean.class);
                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
