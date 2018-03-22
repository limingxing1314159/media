package com.example.hejing.media.okhttputils;

import android.content.Context;

import com.example.hejing.media.I;

/**
 * Created by limx on 2018/3/12.
 */

public class NetDao {
    //登录
    public static void login(Context context, String name, String password, OkHttpUtils.OnCompleteListener<String> listener){
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addFormParam(I.User.USER_NAME,name)
                .addFormParam(I.User.PASSWORD,password)
                .targetClass(String.class)
                .execute(listener);

    }
}
