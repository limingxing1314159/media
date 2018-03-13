package com.example.hejing.media.okhttputils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by limx on 2018/3/8.
 */

public abstract class CallBackUtil<T> {
    public static Handler mMainHandler = new Handler(Looper.getMainLooper());

    public  void onProgress(float progress, long total ){};

    public  void onError(final Call call, final Exception e){
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                onFailure(call,e);
            }
        });
    };
    public  void onSeccess(Call call, Response response){
        final T obj = onParseResponse(call, response);
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                onResponse(obj);
            }
        });
    };


    /**
     * 解析response，执行在子线程
     */
    public abstract T onParseResponse(Call call, Response response);

    /**
     * 访问网络失败后被调用，执行在UI线程
     */
    public abstract void onFailure(Call call, Exception e);

    /**
     *
     * 访问网络成功后被调用，执行在UI线程
     */
    public abstract void onResponse(T response);


    public static abstract class CallBackDefault extends CallBackUtil<Response>{
        @Override
        public Response onParseResponse(Call call, Response response) {
            return response;
        }
    }

    public static abstract class CallBackString extends CallBackUtil<String>{
        @Override
        public String onParseResponse(Call call, Response response) {
            try {
                return response.body().string();
            } catch (IOException e) {
                new RuntimeException("failure");
                return "";
            }
        }
    }

}
