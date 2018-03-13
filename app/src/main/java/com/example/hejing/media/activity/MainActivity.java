package com.example.hejing.media.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hejing.media.I;
import com.example.hejing.media.MediaApplication;
import com.example.hejing.media.R;
import com.example.hejing.media.bean.Result;
import com.example.hejing.media.bean.User;
import com.example.hejing.media.dao.SharePrefrenceUtils;
import com.example.hejing.media.dao.UserDao;
import com.example.hejing.media.okhttputils.CommonUtil;
import com.example.hejing.media.okhttputils.L;
import com.example.hejing.media.okhttputils.MFGT;
import com.example.hejing.media.okhttputils.NetDao;
import com.example.hejing.media.okhttputils.OkHttpUtils;
import com.example.hejing.media.okhttputils.ResultUtils;
;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @InjectView(R.id.loginName)
    EditText loginName;
    @InjectView(R.id.loginPassword)
    EditText loginPassword;
    @InjectView(R.id.loginButton)
    Button loginbutton;

    String name;
    String password;
    MainActivity mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mContent=this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.loginButton)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.loginButton:
                checkedInput();
                break;
        }

    }

    private void checkedInput(){
        name = loginName.getText().toString().trim();
        password = loginPassword.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            Toast.makeText(mContent,"用户名不能为空",Toast.LENGTH_SHORT).show();
            loginName.requestFocus();
            return;
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(mContent,"密码不能为空",Toast.LENGTH_SHORT).show();
            loginPassword.requestFocus();
            return;
        }
        login();
    }

    private void login() {
        final ProgressDialog pd = new ProgressDialog(mContent);
        pd.setMessage(getResources().getString(R.string.logining));
        pd.show();
        L.e(TAG,"name="+name+",password="+password);
        NetDao.login(mContent, name, password, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s,User.class);
                L.e(TAG,"s="+s);
                L.e(TAG,"result = "+ result);
                if (result == null){
                    Toast.makeText(mContent,"登录失败，无此用户",Toast.LENGTH_SHORT).show();
                }else {
                    if (result.isMsg()){
                        User user = (User) result.getData();
                        L.e(TAG,"User = "+user);
                        UserDao dao = new UserDao(mContent);
                        boolean isSuccess = dao.saveUser(user);
                        if (isSuccess){
                            SharePrefrenceUtils.getInstance(mContent).saveUser(user.getName());
                            MediaApplication.setUser(user);
                            Toast.makeText(mContent,"登录成功",Toast.LENGTH_LONG).show();
                            MFGT.gotoLogin(mContent);
                            MFGT.finish(mContent);
                        }else {
                            Toast.makeText(mContent,"登录失败，用户名错误",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        if (result.getCode() == I.MSG_LOGIN_UNKNOW_USER){
                            Toast.makeText(mContent,"，登录失败，未知用户",Toast.LENGTH_SHORT).show();
                        }else if (result.getCode() == I.MSG_LOGIN_ERROR_PASSWORD){
                            Toast.makeText(mContent,"登录失败，密码错误",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(mContent,"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                pd.dismiss();

            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtil.showLongToast(error);
                L.e(TAG,"error="+error);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK && requestCode == I.REQUEST_CODE_REGISTER);
        String name = data.getStringExtra(I.User.USER_NAME);
        loginName.setText(name);
    }
}

