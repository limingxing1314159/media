package com.example.hejing.media.okhttputils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.hejing.media.I;
import com.example.hejing.media.bean.Result;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by limx on 2018/3/8.
 */

public class OkHttpUtils<T> {
    private static String UTF_8 = "utf-8";
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_ERROR = 1;
    /*public static final int DOWNLOAD_START=2;
    public static final int DOWNLOADING=3;
    public static final int DOWNLOAD_FINISH=4;*/

    private static OkHttpClient mOkHttpClient;
    private Handler mHandler;

    /**
     * 存放post请求的实体，实体中存放File类型的文件
     */
    RequestBody mFileBody;
    FormBody.Builder mFormBodyBuilder;
    MultipartBody.Builder mMultipartBodyBuilder;

    public interface OnCompleteListener<T> {
        void onSuccess(T result);

        void onError(String error);
    }

    private OnCompleteListener<T> mListener;

    OkHttpClient.Builder mBuilder;
    /**
     * 构造器，mOkHttpClient必须单例，无论创建多少个OkHttpUtils的实例。
     * 都由mOkHttpClient一个对象处理所有的网络请求。
     */
    public OkHttpUtils(Context context) {
        if (mOkHttpClient == null) {//线程安全的单例
            synchronized (OkHttpUtils.class) {
                if (mOkHttpClient == null) {
                    mBuilder = new OkHttpClient.Builder();
                    File cacheDir = context.getExternalCacheDir();
                    mOkHttpClient = mBuilder
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(20,TimeUnit.SECONDS)
                            .readTimeout(10,TimeUnit.SECONDS)
                            .cache(new Cache(cacheDir,10*(1<<20)))//设置缓存位置和缓存大小
                            .build();
                }
            }
        }
        initHandler();
    }

    /**
     * 设置与服务端连接的时限
     * @param connectTime:连接的时限
     * @return
     */
    public OkHttpUtils<T> connectTimeout(int connectTime) {
        if (mBuilder == null) {
            return this;
        }
        mBuilder.connectTimeout(connectTime, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 设置写数据的时限
     * @param writeTimeout：写数据的时限
     * @return
     */
    public OkHttpUtils<T> writeTimeout(int writeTimeout) {
        if (mBuilder == null) {
            return this;
        }
        mBuilder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 设置读取数据的时限
     * @param readTimeout：读取数据的时限
     * @return
     */
    public OkHttpUtils<T> readTimeout(int readTimeout) {
        if (mBuilder == null) {
            return this;
        }
        mBuilder.readTimeout(readTimeout, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 设置缓存
     * 第一次请求会请求网络得到数据，第二次以及后面的请求则会从缓存中取出数据
     * @param file:缓存的路径
     * @param fileSize：缓存的容量
     * @return
     */
    public OkHttpUtils<T> cache(File file, int fileSize) {
        if (mBuilder == null) {
            return this;
        }
        mBuilder.cache(new Cache(file, fileSize));
        return this;
    }


    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case RESULT_ERROR:
                        mListener.onError(msg.obj==null?msg.toString():msg.obj.toString());
                        break;
                    case RESULT_SUCCESS:
                        T result = (T) msg.obj;
                        mListener.onSuccess(result);
                        break;
                }
            }
        };
    }

    /**
     * 设置为post的请求
     * @return
     */
    public OkHttpUtils<T> post() {
        mFormBodyBuilder = new FormBody.Builder();
        return this;
    }

    StringBuilder mUrl;

    public OkHttpUtils<T> url(String url) {
        mUrl = new StringBuilder(url);
        return this;
    }

    public OkHttpUtils<T> setRequestUrl(String request) {
        mUrl = new StringBuilder(I.SERVER_ROOT);
        mUrl.append(request);
//        Log.e("okhttp","1 murl="+ mUrl.toString());
        return this;
    }

    /**
     * 用于json解析的类对象
     */
    Class<T> mClazz;

    /**
     * 设置json解析的目标类对象
     * @param clazz:解析的类对象
     * @return
     */
    public OkHttpUtils<T> targetClass(Class<T> clazz) {
        mClazz = clazz;
        return this;
    }

    /**
     * 添加请求参数至url，包括GET和POST请求
     * 不包括POST请求中上传文件的同时向Form中添加其它参数的情况
     * @param key:键
     * @param value：值
     */
    public OkHttpUtils<T> addParam(String key, String value) {
        try {
            //post请求的request参数也要拼接到url中
            if (mFormBodyBuilder != null) {//post请求的参数添加方式
                mFormBodyBuilder.add(key, URLEncoder.encode(value, UTF_8));
            } else {//get请求的参数添加方式
                if (mUrl.indexOf("?") == -1) {
                    mUrl.append("?");
                } else {
                    mUrl.append("&");
                }
                mUrl.append(key).append("=").append(URLEncoder.encode(value, UTF_8));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 发送请求
     * @param listener：处理服务端返回结果的代码
     */
    public void execute(OnCompleteListener<T> listener) {
        if (listener != null) {
            mListener = listener;
        }
        Request.Builder builder = new Request.Builder().url(mUrl.toString());
        L.e("url="+mUrl);
        if (mFormBodyBuilder != null) {
            RequestBody body = mFormBodyBuilder.build();
            builder.post(body);
        }
        if (mFileBody != null) {
            builder.post(mFileBody);
        }
        //如果是post请求向Form中添加多个参数
        if (mMultipartBodyBuilder != null) {
            MultipartBody multipartBody = mMultipartBodyBuilder.build();
            builder.post(multipartBody);
        }
        //创建请求
        Request request = builder.build();
        Call call = mOkHttpClient.newCall(request);
        if (mCallback != null) {
            call.enqueue(mCallback);
            return;
        }
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = Message.obtain();
                msg.what = RESULT_ERROR;
                msg.obj = e.getMessage();
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                if(mClazz.equals(String.class)){
                    Message msg = Message.obtain();
                    msg.what = RESULT_SUCCESS;
                    msg.obj = json;
                    mHandler.sendMessage(msg);
                }else {
                    Gson gson = new Gson();
                    T value = gson.fromJson(json, mClazz);
                    Message msg = Message.obtain();
                    msg.what = RESULT_SUCCESS;
                    msg.obj = value;
                    mHandler.sendMessage(msg);
                }
            }
        });
    }

    Callback mCallback;
    /**
     * 在OkHttp创建的工作线程中执行一段代码,
     * @param callback
     * @return
     */
    public OkHttpUtils<T> doInBackground(Callback callback) {
        mCallback=callback;
        return this;
    }

    /**
     * 在主线程中执行的代码，doInBackground方法之后调用
     * @param listener
     * @return
     */
    public OkHttpUtils<T> onPostExecute(OnCompleteListener<T> listener) {
        mListener=listener;
        return this;
    }

    /**doInBackground()之前在主线程中执行的方法，类似与AsyncTask中的onPreExecute()
     * @param r:运行的代码
     * @return
     */
    public OkHttpUtils<T> onPreExecute(Runnable r) {
        r.run();
        return this;
    }

    /**
     * 工作线程向主线程发送消息
     * @param msg
     */
    public void sendMessage(Message msg) {
        mHandler.sendMessage(msg);
    }

    /**
     * 重载的sendMessage方法，用于发送空消息
     * @param what
     */
    public void sendMessage(int what) {
        mHandler.sendEmptyMessage(what);
    }

    public <T> T parseJson(String json, Class<?> clazz) {
        Gson gson=new Gson();
        T t = (T) gson.fromJson(json, clazz);
        return t;
    }

    /*
     * 专门针对Result类的json解析方法，不具有通用性，属性定制、专用的方法
     * @param result
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T parseJson(Result result, Class<?> clazz) {
        if (result.getCode() == 0) {
            String json = result.getData().toString();
            T t = parseJson(json, clazz);
            return t;
        }
        return null;
    }

    /**
     * 释放mClient的资源
     */
    public static void release() {
        if (mOkHttpClient != null) {
            //取消所有请求
            mOkHttpClient.dispatcher().cancelAll();
            mOkHttpClient=null;
        }
    }

}
