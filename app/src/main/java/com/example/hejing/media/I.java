package com.example.hejing.media;

/**
 * Created by limx on 2018/3/8.
 */

public interface I {
    String SERVER_ROOT = "http://adv-app.wesogou.com/";
    int REQUEST_CODE_REGISTER = 101;
    int REQUEST_CODE_LOGIN = 102;
    int REQUEST_CODE_LOGIN_FROM_CART = 104;

    interface User {
        String TABLE_NAME = "t_media_user";
        String USER_NAME = "name";//用户账号
        String PASSWORD = "password";//用户密码
        String TOKEN = "token";//登录时获取的token
    }

    String UTF_8 = "utf-8";

    /** 客户端发送的用户登录请求 */
    String REQUEST_LOGIN = "login/index";
    /** 客户端发送的点位列表请求 */
    String REQUEST_POINT = "point/index";

    /** 表示列表项布局的两种类型*/
    int TYPE_ITEM=0;
    int TYPE_FOOTER=1;

    /**
     * 点位排序方式
     */
    int SORT_BY_PRICE_ASC=1;
    int SORT_BY_PRICE_DESC=2;
    int SORT_BY_ADDTIME_ASC=3;
    int SORT_BY_ADDTIME_DESC=4;
    String PAGE_ID = "page_id";//分页的起始下标
    String PAGE_SIZE = "page_size";//分页的每页数量
    int PAGE_ID_DEFAULT = 1;//分页的起始下标默认值
    int PAGE_SIZE_DEFAULT = 15;//分页的每页数量默认值

    /** 下拉刷新*/
    int ACTION_DOWNLOAD=0;
    /** 第一次下载*/
    int ACTION_PULL_DOWN=1;
    /** 上拉刷新*/
    int ACTION_PULL_UP=2;
    /** 每行显示的数量columNum*/
    int COLUM_NUM = 1;

    int CAT_ID=0;
}
