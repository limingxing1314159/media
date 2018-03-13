package com.example.hejing.media.activity;

/**
 * Created by limx on 2018/3/8.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hejing.media.okhttputils.MFGT;


public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
    }
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void setListener();

    public void onBackPressed(){
        MFGT.finish(this);
    }
}
