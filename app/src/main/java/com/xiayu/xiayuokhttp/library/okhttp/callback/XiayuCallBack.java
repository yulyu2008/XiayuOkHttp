package com.xiayu.xiayuokhttp.library.okhttp.callback;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.Response;

/**
 * 创建者     罗夏雨
 * 创建时间   2017/2/8 16:14
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public abstract class XiayuCallBack<T> extends Callback<T> {
    Class mClass;

    public XiayuCallBack(Class aClass) {
        mClass = aClass;
    }

    public static Gson mGson = new Gson();

    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        T t = null;
        try {
            t = (T) mGson.fromJson(response.body().string(), mClass);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

}
