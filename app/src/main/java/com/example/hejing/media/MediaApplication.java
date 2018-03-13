package com.example.hejing.media;

import android.app.Application;

import com.example.hejing.media.bean.User;

/**
 * Created by limx on 2018/3/8.
 */

public class MediaApplication extends Application{
    public static MediaApplication application;
    public static MediaApplication instance;

    public static String name;
    public static User user;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        instance = this;
    }

    public static MediaApplication getInstance() {
        if (instance == null) {
            instance = new MediaApplication();
        }
        return instance;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        MediaApplication.name = name;
    }

    public static User getUser(){
        return user;
    }
    public static void setUser(User user){
        MediaApplication.user = user;
    }
}
