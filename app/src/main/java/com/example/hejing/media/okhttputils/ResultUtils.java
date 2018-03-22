package com.example.hejing.media.okhttputils;

import android.util.Log;

import com.example.hejing.media.I;
import com.example.hejing.media.bean.Result;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


public class ResultUtils {
    public static final String TAG = ResultUtils.class.getSimpleName();

    public static <T> Result getResultFromJson(String jsonStr, Class<T> clazz){
        L.e(TAG,"jsonStr="+jsonStr);
        Result result = new Result();
        L.e(TAG,"result="+result);
        try {
            if(jsonStr==null || jsonStr.isEmpty() || jsonStr.length()<3) return null;

            JSONObject jsonObject = new JSONObject(jsonStr);
            L.e(TAG,"jsonObject="+jsonObject);
            if(!jsonObject.isNull("code")) {
                result.setCode(jsonObject.getInt("code"));

            }else if(!jsonObject.isNull("result")){
                result.setCode(jsonObject.getInt("result"));
            }
            if(!jsonObject.isNull("msg")) {
                result.setMsg(jsonObject.getString("msg"));
            }else if(!jsonObject.isNull("result")){
                result.setMsg(jsonObject.getString("result"));
            }
            if (!jsonObject.isNull("token")){
                result.setToken(jsonObject.getString("token"));
            }else if (!jsonObject.isNull("result")){
                result.setToken(jsonObject.getString("result"));
            }
            if(!jsonObject.isNull("data")) {
                JSONObject jsonRetData = jsonObject.getJSONObject("data");
                if (jsonRetData != null) {
                    Log.e("Utils", "jsonRetData=" + jsonRetData);
                    String date;
                    try {
                        date = URLDecoder.decode(jsonRetData.toString(), I.UTF_8);
                        Log.e("Utils", "jsonRetData=" + date);
                        T t = new Gson().fromJson(date, clazz);
                        result.setData((String) t);
                        return result;

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                        T t = new Gson().fromJson(jsonRetData.toString(), clazz);
                        result.setData((String) t);
                        return result;
                    }
                }
            }else{
                if (jsonObject != null) {
                    Log.e("Utils", "jsonRetData=" + jsonObject);
                    String date;
                    try {
                        date = URLDecoder.decode(jsonObject.toString(), I.UTF_8);
                        Log.e("Utils", "jsonRetData=" + date);
                        T t = new Gson().fromJson(date, clazz);
                        result.setData((String) t);
                        return result;

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                        T t = new Gson().fromJson(jsonObject.toString(), clazz);
                        result.setData((String) t);
                        return result;
                    }
                }
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    public static <T> Result getListResultFromJson(String jsonStr, Class<T> clazz){
        Result result = new Result();
        Log.e("Utils","jsonStr="+jsonStr);
        try {
            if(jsonStr==null || jsonStr.isEmpty() || jsonStr.length()<3){
                return null;
            }
            JSONObject jsonObject = new JSONObject(jsonStr);
            L.e(TAG,"jsonObject="+jsonObject);
            if(!jsonObject.isNull("code")) {
                result.setCode(jsonObject.getInt("code"));
            }else if(!jsonObject.isNull("result")){
                result.setCode(jsonObject.getInt("result"));
            }
            if(!jsonObject.isNull("msg")) {
                result.setMsg(jsonObject.getString("msg"));
            }else if(!jsonObject.isNull("result")){
                result.setMsg(jsonObject.getString("result"));
            }
            if (!jsonObject.isNull("token")){
                result.setToken(jsonObject.getString("token"));
            }else if (!jsonObject.isNull("result")){
                result.setToken(jsonObject.getString("result"));
            }
            if(!jsonObject.isNull("data")) {
                JSONArray array = jsonObject.getJSONArray("data");
                if (array != null) {
                    List<T> list = new ArrayList<T>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonGroupAvatar = array.getJSONObject(i);
                        T ga = new Gson().fromJson(jsonGroupAvatar.toString(), clazz);
                        list.add(ga);
                    }
                    result.setData(String.valueOf(list));
                    return result;
                }
            }else{
                JSONArray array = new JSONArray(jsonStr);
                if (array != null) {
                    List<T> list = new ArrayList<T>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonGroupAvatar = array.getJSONObject(i);
                        T ga = new Gson().fromJson(jsonGroupAvatar.toString(), clazz);
                        list.add(ga);
                    }
                    result.setData(String.valueOf(list));
                    return result;
                }
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

}
