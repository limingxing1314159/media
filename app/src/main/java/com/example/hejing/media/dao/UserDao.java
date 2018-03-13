package com.example.hejing.media.dao;

import android.content.Context;

import com.example.hejing.media.bean.User;



/**
 * Created by limx on 2018/3/12.
 */
public class UserDao {
    public static final String USER_TABLE_NAME = "name";
    public static final String USER_COLUMN_NAME = "name";

    public UserDao(Context context) {
        DBManager.getInstance().onInit(context);
    }
    public boolean saveUser(User user){
        return DBManager.getInstance().saveUser(user);
    }

}

