package com.example.hejing.media.okhttputils;

import android.widget.Toast;

import com.example.hejing.media.MediaApplication;

/**
 * Created by limx on 2018/3/8.
 */

public class CommonUtil {

    public static void showLongToast(String msg){
        Toast.makeText(MediaApplication.application,msg,Toast.LENGTH_LONG).show();
    }
    public static void showShortToast(String msg){
        Toast.makeText(MediaApplication.application,msg,Toast.LENGTH_SHORT).show();
    }
}
