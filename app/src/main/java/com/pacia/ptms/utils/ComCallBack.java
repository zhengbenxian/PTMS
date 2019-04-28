package com.pacia.ptms.utils;

/**
 * Created by p on 2018/09/07.
 */

public class ComCallBack {
    public interface LoginCallBack {
        //退出登录
        void onLoginOut();

        void onFailed(String msg);

        void onError(String msg);
    }
}
