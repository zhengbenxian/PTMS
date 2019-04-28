package com.pacia.ptms.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.pacia.ptms.bean.LoginBean;
import com.pacia.ptms.service.Constant;

/**
 * 共享参数工具类
 * Created by p on 2018/05/10.
 */

public class SPUtils {
    private static String SP_NAME = "ptms";
    private static SPUtils spUtils;

    public static SPUtils getInstence(String name) {
        if (null == spUtils) {
            spUtils = new SPUtils();
        }
        spUtils.SP_NAME = name;
        return spUtils;
    }

    //保存用户信息
    public static void putUserInfo(Context context, LoginBean lb) {
        SharedPreferences sp = context.getSharedPreferences
                (Constant.USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("gid", lb.getGid());
        editor.putString("role_type", lb.getRole_type() + "");
        editor.putString("user_name", lb.getUser_name());
        editor.commit();
    }

    public static String getRoleType(Context context) {
        return (String) getUserInfo(context, "role_type", "");
    }

    public static String getUserGid(Context context) {
        return (String) getUserInfo(context, "gid", "");
    }
    public static String getUserName(Context context) {
        return (String) getUserInfo(context, "user_name", "");
    }

    public static Object getUserInfo(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(Constant.USER_INFO,
                Context.MODE_PRIVATE);
        String type = object == null ? "" : object.getClass().getSimpleName();
        if ("String".equals(type)) {
            return sp.getString(key, (String) object);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) object);
        }
        return "";
    }

    //保存登录信息
    public static void putUserPw(Context context, String user, String pw,
                                 String captcha) {
        SharedPreferences sp = context.getSharedPreferences
                ("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user", user);
        editor.putString("password", pw);
        editor.putString("captcha", captcha);
        editor.commit();
    }

    public static String getUserPw(Context context, String key) {
        String info;
        SharedPreferences sp = context.getSharedPreferences("user",
                Context.MODE_PRIVATE);
        info = sp.getString(key, "");
        return info;
    }

    public static void putSession(Context context, String session) {
        SharedPreferences sp = context.getSharedPreferences
                (Constant.USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("session", session);
        editor.commit();
    }

//    public static String getSession(Context context) {
//        String info;
//        SharedPreferences sp = context.getSharedPreferences(Constant.USER_INFO,
//                Context.MODE_PRIVATE);
//        info = sp.getString("session", "");
//        return info;
//    }

    /**
     * 写入
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String type = object == null ? "" : object.getClass().getSimpleName();
        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }
        editor.commit();
    }

    /**
     * 读取
     *
     * @param context
     * @param key
     * @param object
     * @return
     */
    public static Object get(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String type = object == null ? "" : object.getClass().getSimpleName();
        if ("String".equals(type)) {
            return sp.getString(key, (String) object);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) object);
        }
        return null;
    }

    /**
     * 清除
     *
     * @param context
     */
    public void cleanAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
