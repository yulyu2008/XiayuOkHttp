package com.xiayu.xiayuokhttp.app;

import android.app.Application;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.xiayu.xiayuokhttp.library.okhttp.OkHttpUtils;
import com.xiayu.xiayuokhttp.library.okhttp.log.LoggerInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者     罗夏雨
 * 创建时间   2017/2/8 17:20
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request  request  = chain.request();
                        Request  newRequest     = request.newBuilder().addHeader("xiayu", "xiayu").build();
                        return  chain.proceed(newRequest);
                    }
                })
                .addNetworkInterceptor( new StethoInterceptor())
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }
}
