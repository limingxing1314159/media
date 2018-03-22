package com.example.hejing.media.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hejing.media.bean.User;


/**
 * Created by limx on 2018/3/12.
 */
public class DBManager {
    private static DBManager dbMgr = new DBManager();
    private  DBOpenHelper dbHelper;
    void onInit(Context context){
        dbHelper = new DBOpenHelper(context);
    }
    public static synchronized DBManager getInstance(){
        return dbMgr;
    }

    public synchronized void closeDB(){
        if (dbHelper!=null){
            dbHelper.closeDB();
        }
    }

    public synchronized boolean saveUser(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserDao.USER_COLUMN_NAME,user.getName());
        if (db.isOpen()){
            return db.replace(UserDao.USER_TABLE_NAME,null,values)!=-1;
        }
        return false;
    }
}
