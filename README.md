#前言:
[项目源码](https://github.com/yulyu2008/XiayuOkHttp)

**https://github.com/yulyu2008/XiayuOkHttp**

**Okhttputils是张鸿洋基于okhhttp封装的一个框架,使用起来很方便,相信很多人都用到过,这里我们在这个框架的基础上二次封装,让我们用起来更加方便.**
 
**主要功能:**

- 可以添加统一的头信息
- 统一处理异常
- 自动注入加载动画(可选)
- 自动解析bean对象
- 结合Stetho,调试更方便

#1.添加依赖

    compile 'com.zhy:okhttputils:2.6.2'
    compile 'com.squareup.okhttp3:okhttp:3.3.1'
    compile 'com.google.code.gson:gson:2.5'
    compile 'com.facebook.stetho:stetho-okhttp3:1.3.1'
    debugCompile 'com.facebook.stetho:stetho:1.1.1'

#2.初始化(配置统一头和拦截器等)

	public class MyApplication extends Application
	{
	    @Override
	    public void onCreate()
	    {
	        super.onCreate();
	
	        OkHttpClient okHttpClient = new OkHttpClient.Builder()
	                .addInterceptor(new LoggerInterceptor("TAG"))
	                .addNetworkInterceptor(new Interceptor() {//自定义拦截器
	                    @Override
	                    public Response intercept(Chain chain) throws IOException {
	                        Request  request  = chain.request();
	                        //配置统一的头
	                        Request  newRequest     = request.newBuilder().addHeader("head", "xiayu").build();
	                        return  chain.proceed(newRequest);
	                    }
	                })
	                .addNetworkInterceptor( new StethoInterceptor())//增加Stetho拦截器,用于调试
	                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
	                .readTimeout(10000L, TimeUnit.MILLISECONDS)
	                //其他配置
	                .build();
	        //使用自定义OkHttpClient
	        OkHttpUtils.initClient(okHttpClient);
	
	    }
	}

**注意记得在清单文件中配置Application和网络权限**


**Stetho的初始化和使用这里就不做介绍了,可以参考这篇文章**
[FaceBook调试神器Stetho](http://blog.csdn.net/yulyu/article/details/54980871 "Stetho")
 
http://blog.csdn.net/yulyu/article/details/54980871
#3.自定义callback
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

#4.抽取方法
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
#5.使用网络请求
	MovieUtils.getMovie(0, 10, new XiayuCallBack<MovieEntity>(MovieEntity.class, this, true) {
	            @Override
	            public void myError(Call call, Exception e, int id) {
	                //失败
	            }
	
	            @Override
	            public void onResponse(MovieEntity response, int id) {
	                //成功
	            }
	        });
#6.[项目源码](https://github.com/yulyu2008/XiayuOkHttp)
**https://github.com/yulyu2008/XiayuOkHttp**