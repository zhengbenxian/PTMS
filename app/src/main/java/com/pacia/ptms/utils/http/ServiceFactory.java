package com.pacia.ptms.utils.http;

/**
 * Created by p on 2018/03/15.
 */

public class ServiceFactory {

    public static <T> T getService(String url, Class<T> service) {
        return RetrofitUtils.getRetrofit(url).create(service);
    }

    public static <T> T getService(Class<T> service) {
        return RetrofitUtils.getRetrofit().create(service);
    }
}
