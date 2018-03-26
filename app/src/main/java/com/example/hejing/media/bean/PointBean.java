package com.example.hejing.media.bean;

import java.io.Serializable;

/**
 * Created by limx on 2018/3/23.
 */

public class PointBean implements Serializable{
    /**code:  状态码
       data:   点位列表json数据
       msg: 接口响应消息
       page: 当前数据的所属页数
      */

    private int code;
    private String data;
    private String msg;
    private int page;

    private PointBean(){}

    public PointBean(int code, String data, String msg, int page) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.page = page;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "PointBean{" +
                "code=" + code +
                ", data='" + data + '\'' +
                ", msg='" + msg + '\'' +
                ", page=" + page +
                '}';
    }
}
