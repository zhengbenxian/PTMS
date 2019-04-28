package com.pacia.ptms.utils.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pacia.ptms.service.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 访问网络工具类
 * Created by p on 2018/03/15.
 */

public class RetrofitUtils {
    private static Retrofit retrofit;

    private RetrofitUtils() {
        retrofit = createRetrofit(BaseUrl.BASE_URL);
    }

    private static Retrofit createRetrofit(String url) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(OkHttpUtils.getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static class RetrofitHolder {
        public static RetrofitUtils utils = new RetrofitUtils();
    }

    public static Retrofit getRetrofit() {
        return RetrofitHolder.utils.retrofit;
    }

    public static Retrofit getRetrofit(String url) {
        return createRetrofit(url);
    }
}
