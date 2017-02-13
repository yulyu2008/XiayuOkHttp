package com.xiayu.xiayuokhttp.net;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者     罗夏雨
 * 创建时间   2017/2/8 16:14
 * 描述	      ${TODO}
 */
public abstract class XiayuCallBack<T> extends Callback<T> {
    Class mClass;
    private Context        mContext;
    private boolean        mIsShowDialog;
    private ProgressDialog progressDialog;
    private Request        mRequest;
    public static Gson mGson = new Gson();

    public XiayuCallBack(Class targetClazz, Context context, boolean isShowDialog) {
        mClass = targetClazz;
        mContext = context;
        mIsShowDialog = isShowDialog;
    }

    public XiayuCallBack(Class<T> targetClazz, Context mContext) {
        this(targetClazz, mContext, false);//默认不开启动画
    }


    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        T t = null;
        try {
            //解析json,返回bean对象
            t = (T) mGson.fromJson(response.body().string(), mClass);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        //在这里做异常统一处理
        System.out.println("onError");
        myError(call, e, id);
    }

    public abstract void myError(Call call, Exception e, int id);

    @Override
    public void onBefore(Request request, int id) {
        //这里开启动画
        mRequest = request;
        if (mIsShowDialog && mContext != null) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setCancelable(false);
            }
            if (mContext instanceof Activity && !((Activity) mContext).isFinishing()) {
                progressDialog.show();
            }
        }
        super.onBefore(request, id);
    }

    @Override
    public void onAfter(int id) {
        //这里结束动画
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onAfter(id);
    }

}
