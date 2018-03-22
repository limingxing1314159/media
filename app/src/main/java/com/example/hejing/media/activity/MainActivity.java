package com.example.hejing.media.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hejing.media.I;
import com.example.hejing.media.R;
import com.example.hejing.media.okhttputils.L;
import com.example.hejing.media.okhttputils.MFGT;
import com.example.hejing.media.okhttputils.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

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

    private MyHandler myhandler = new MyHandler(this);

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

    //弱引用，防止内存泄露
    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() == null) {
                return;
            }
            mActivity.get().updateUIThread(msg);
        }
    }

    //配合子线程更新UI线程
    private void updateUIThread(Message msg){
        Bundle bundle = new Bundle();
        bundle = msg.getData();
        String str = bundle.getString("result");
        L.e(TAG,"str="+str);
        try {
            JSONObject jsonObject = new JSONObject(str);
            L.e(TAG,"jsonObject="+jsonObject);
            int code = jsonObject.getInt("code");
            if (code == 1){
                Toast.makeText(mContent, "登录成功", Toast.LENGTH_SHORT).show();
                MFGT.gotoLogin(mContent);
                MFGT.finish(mContent);
            }else if (code == 0){
                Toast.makeText(mContent, "用户名或密码有误，请重新输入", Toast.LENGTH_SHORT).show();
            }else if (code == -1){
                Toast.makeText(mContent,"登录信息已过期，请重新申请",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(mContent, "登录失败", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @OnClick(R.id.loginButton)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.loginButton:
                checkedInput();
                break;
        }

    }

    private void checkedInput() {
        name = loginName.getText().toString().trim();
        password = loginPassword.getText().toString().trim();
        L.e(TAG,"name="+name+",password="+password);
        if (TextUtils.isEmpty(name)){
            Toast.makeText(mContent, "用户名不能为空", Toast.LENGTH_LONG).show();
            loginName.requestFocus();
            return;
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(mContent, "密码不能为空", Toast.LENGTH_LONG).show();
            loginPassword.requestFocus();
            return;
        }
        login();
    }


    private void login() {
        final ProgressDialog pd = new ProgressDialog(mContent);
        pd.setMessage(getResources().getString(R.string.logining));
        pd.show();

        //开启访问数据库线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                String string = OkHttpUtils.LoginByPost(name,password);
                Bundle bundle = new Bundle();
                bundle.putString("result",string);
                Message msg = new Message();
                msg.setData(bundle);
                myhandler.sendMessage(msg);
            }
        }).start();
        pd.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK && requestCode == I.REQUEST_CODE_REGISTER);
        String name = data.getStringExtra(I.User.USER_NAME);
        loginName.setText(name);
    }
}

