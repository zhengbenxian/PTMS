package com.pacia.ptms.service;

import android.Manifest;

/**
 * Created by p on 2018/05/02.
 */

public class Constant {
    //需要申请的权限
    public static String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA
    };
    //sp some name
    public static final String USER_INFO = "user_info";

    //调用相册Code
    public static final int CHOOSE_PHOTO_CODE = 12221;
    public static final int CHOOSE_PHOTO_CODE_LOG = 12222;
    //调用相机Code
    public static final int CHOOSE_CAMERA_CODE = 23222;
    //调用摄像机Code
    public static final int CHOOSE_VIDEO_CODE = 32211;
    //访问接口成功
    public static final int SUCCESS = 0;
    //访问接口失败
    public static final int FAILED = -1;
    //角色类型
    //管理员
    public static final String ROLE_MANAGER = 2 + "";
    //省公司
    public static final String ROLE_PROVINCE = 12 + "";
    //油站
    public static final String ROLE_OIL_STATION = 40 + "";
    //油库
    public static final String ROLE_OIL_WAREHOUSE = 50 + "";
    //承运商
    public static final String ROLE_CARRIER = 200 + "";
    //运输
    //公路运输
    public static final String TRANS_ROAD = "10";
    //水路运输
    public static final String TRANS_WATER = "20";
    //全部  30
    public static final String TRANS_ALL = "";
    //驾驶员
    public static final String PERSON_DRIVER = "10";
    //押运员
    public static final String PERSON_D_ESCORT = "20";
    public static final String TRANS_PRO_ISSUED_CLASS = "已发班";
    public static final String TRANS_PRO_WAREHOUSING = "已入库";
    // 10:非法登车，20: 路线偏离， 30：超时停车
    public static final String WARNING_ILLEGAL = "10";
    public static final String WARNING_ROTE = "20";
    public static final String WARNING_OVERTIME = "30";
    //摄像头
    //在线
    public static final String CAMERA_ON_LINE = "10";
    //离线
    public static final String CAMERA_OFF_LINE = "20";
}
