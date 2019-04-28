package com.pacia.ptms.utils;

import android.content.Context;

/**
 * Created by p on 2018/09/07.
 */

public class ComHttpUtils {
    private Context context;
    private ComCallBack.LoginCallBack comCallBack;

    public ComHttpUtils(Context context, ComCallBack.LoginCallBack comCallBack) {
        this.context = context;
        this.comCallBack = comCallBack;
    }


    public ComHttpUtils(Context context) {
        this.context = context;
    }

//    //退出登录
//    public void loginOut() {
//        ServiceFactory.getService(ApiService.class)
//                .loginout(SPUtils.getInfo(context, "gid"))
//                .compose(SchedulersHelper.<ResponseBody>io_main(context, true))
//                .subscribe(new BaseObserver<ResponseBody>() {
//                    @Override
//                    public void onSuccess(String resp) {
//                        if (Constant.SUCCESS == JsonUtils.getBusiCode(resp)) {
//                            comCallBack.onLoginOut();
//                        } else if (Constant.FAILED == JsonUtils.getBusiCode(resp)) {
//                            comCallBack.onFailed("注销失败，请稍后再试");
//                        }
//                    }
//
//                    @Override
//                    public void onError(String error) {
//                        comCallBack.onError(error);
//                    }
//                });
//    }
}
