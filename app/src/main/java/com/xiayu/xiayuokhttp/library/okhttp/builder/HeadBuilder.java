package com.xiayu.xiayuokhttp.library.okhttp.builder;


import com.xiayu.xiayuokhttp.library.okhttp.OkHttpUtils;
import com.xiayu.xiayuokhttp.library.okhttp.request.OtherRequest;
import com.xiayu.xiayuokhttp.library.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
