package com.xiayu.xiayuokhttp.net;

import com.xiayu.xiayuokhttp.bean.MovieEntity;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * 创建者     罗夏雨
 * 创建时间   2017/2/8 16:20
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class MovieUtils {
    public static void getMovie(int start, int count, XiayuCallBack<MovieEntity> callBack) {
        OkHttpUtils.get()
                .url("https://api.douban.com/v2/movie/top250")
                .addHeader("MovieUtils","start")
                .addParams("start",start+"")
                .addParams("count",count+"")
                .build()
                .execute(callBack);
    }
}
