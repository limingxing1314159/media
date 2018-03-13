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

    public synchronized User getUser(String username){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from " + UserDao.USER_TABLE_NAME + " where "
                + UserDao.USER_COLUMN_NAME + " =?";
        User user = null;
        Cursor cursor = db.rawQuery(sql,new String[]{username});
        if (cursor.moveToNext()){
            user = new User();
            user.setName(username);
            return user;
        }
        return user;
    }

    public synchronized boolean updateUser(User user){
        int result = -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = UserDao.USER_COLUMN_NAME + "=?";
        ContentValues values = new ContentValues();
        if (db.isOpen()){
            result = db.update(UserDao.USER_TABLE_NAME,values,sql,new String[]{user.getName()});
        }
        return result>0;
    }
}
