package com.leyuan.coach.http;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.leyuan.coach.config.UrlConfig;
import com.leyuan.coach.page.App;
import com.leyuan.commonlibrary.manager.DeviceManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitHelper {
    private static final int DEFAULT_TIMEOUT = 30;
    private static Retrofit singleton;

    public static <T> T createApi(Class<T> clazz) {
        if (singleton == null) {
            synchronized (RetrofitHelper.class) {
                if (singleton == null) {
                    Retrofit.Builder builder = new Retrofit.Builder();
                    builder.baseUrl(UrlConfig.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())//设置远程地址
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

                    singleton = builder.client(createClient()).build();
                }
            }
        }
        return singleton.create(clazz);
    }

    public static OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        if (App.getInstance().isLogin() && App.getInstance().getToken() != null) {
                            builder.addHeader("token", App.getInstance().getToken());
                        } else {
                            builder.addHeader("token", "");
                        }
                        builder.addHeader("lat", String.valueOf(App.lat));
                        builder.addHeader("lng", String.valueOf(App.lon));
                        builder.addHeader("device", "android");
                        builder.addHeader("register", App.getInstance().getJPushId());
                        builder.addHeader("version", App.getInstance().getVersionName());
                        builder.addHeader("deviceName", DeviceManager.getPhoneBrand());

                        Request authorised = builder.build();
                        return chain.proceed(authorised);
                    }
                })
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

}
