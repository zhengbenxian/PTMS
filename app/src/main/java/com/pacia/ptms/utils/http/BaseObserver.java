package com.pacia.ptms.utils.http;

import android.util.Log;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * rxjava 封装 Observer
 * Created by p on 2018/05/31.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    private static final String TAG = "BaseObserver";

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(T t) {
        try {
            ResponseBody json = (ResponseBody) t;
            String js = json.string().toString();
//            if ("-2017".equals(JsonUtils.getSession(js))) {
//                onError("session验证失效，请退出重新登录");
//            } else if (js.contains("ORA-12899")) {
//                onError("输入字符超长,请重新输入");
//            } else
            onSuccess(js);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable t) {
        Log.e(TAG, "onError: " + t.getMessage());
        String msg;
        if (t instanceof UnknownHostException || t instanceof ConnectException)
            msg = "与服务器连接异常";
        else if (t instanceof SocketTimeoutException)
            msg = "连接超时，请检查您的网络状态，稍后重试";
        else
            msg = "请求服务器失败，请稍后再试";
        onError(msg);
    }

    public abstract void onSuccess(String json);

    public abstract void onError(String error);

}
