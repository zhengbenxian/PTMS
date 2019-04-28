package com.pacia.ptms.utils;

import android.widget.Toast;

import com.pacia.ptms.MyApplication;


/**
 * Toast工具类
 * Created by p on 2018/03/16.
 */

public class ToastUtils {
    private static boolean isShow = true;
    private static Toast toast = null;

    private ToastUtils() {
        throw new UnsupportedOperationException("不能被实例化");
    }

    public static void isShow(boolean show) {
        isShow = show;
    }

    public static void cancel() {
        if (isShow && null != toast)
            toast.cancel();
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        if (isShow) {
            if (null == toast)
                toast = Toast.makeText(MyApplication.getInstance(), message, Toast.LENGTH_SHORT);
            else
                toast.setText(message);
            toast.show();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param resId
     */
    public static void showShort(int resId) {
        if (isShow) {
            if (null == toast)
                toast = Toast.makeText(MyApplication.getInstance(), resId, Toast.LENGTH_SHORT);
            else
                toast.setText(resId);
            toast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        if (isShow) {
            if (null == toast)
                toast = Toast.makeText(MyApplication.getInstance(), message, Toast.LENGTH_LONG);
            else
                toast.setText(message);
            toast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param resId
     */
    public static void showLong(int resId) {
        if (isShow) {
            if (null == toast)
                toast = Toast.makeText(MyApplication.getInstance(), resId, Toast.LENGTH_LONG);
            else
                toast.setText(resId);
            toast.show();
        }
    }

    /**
     * 自定义时间
     *
     * @param message
     * @param duration
     */
    public static void showDuration(CharSequence message, int duration) {
        if (isShow) {
            if (null == toast)
                toast = Toast.makeText(MyApplication.getInstance(), message, duration);
            else {
                toast.setText(message);
                toast.setDuration(duration);
            }
            toast.show();
        }
    }

    /**
     * 自定义时间
     *
     * @param resId
     * @param duration
     */
    public static void showDuration(int resId, int duration) {
        if (isShow) {
            if (null == toast)
                toast = Toast.makeText(MyApplication.getInstance(), resId, duration);
            else {
                toast.setText(resId);
                toast.setDuration(duration);
            }
            toast.show();
        }
    }
}
