package com.example.hejing.media;

/**
 * Created by limx on 2018/3/8.
 */

public interface I {
    public String SERVER_ROOT = " http://adv-app.wesogou.com/";
    int REQUEST_CODE_REGISTER = 101;
    int REQUEST_CODE_LOGIN = 102;

    int MSG_LOGIN_UNKNOW_USER=401;//账户不存在
    int MSG_LOGIN_ERROR_PASSWORD=402;//账户密码错误
//    int MSG_LOGIN_NULL=403;//账户或者密码为空

    interface User {
        String TABLE_NAME = "t_media_user";
        String USER_NAME = "name";//用户账号
        String PASSWORD = "password";//用户密码
    }

    String UTF_8 = "utf-8";

    /** 客户端发送的用户登录请求 */
    String REQUEST_LOGIN = "login/index";
}
