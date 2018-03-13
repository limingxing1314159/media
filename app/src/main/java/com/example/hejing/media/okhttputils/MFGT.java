package com.example.hejing.media.okhttputils;

import android.app.Activity;
import android.content.Intent;

import com.example.hejing.media.I;
import com.example.hejing.media.R;
import com.example.hejing.media.activity.MainActivity;

/**
 * Created by limx on 2018/3/8.
 */

public class MFGT {

    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    public static void gotoLogin(Activity context){
        Intent intent = new Intent();
        intent.setClass(context,MainActivity.class);
        startActivityForResult(context,intent, I.REQUEST_CODE_LOGIN);
    }

    public static void startActivityForResult(Activity context,Intent intent,int requestCode){
        context.startActivityForResult(intent,requestCode);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_bottom_out);
    }
}
