package com.example.hejing.media.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import com.example.hejing.media.I;
import com.example.hejing.media.MediaApplication;
import com.example.hejing.media.R;
import com.example.hejing.media.fragment.CompletedFragment;
import com.example.hejing.media.fragment.PointFragment;
import com.example.hejing.media.fragment.SetFragment;
import com.example.hejing.media.fragment.TaskFragment;
import com.example.hejing.media.okhttputils.L;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    @Bind(R.id.point)
    RadioButton point;
    @Bind(R.id.task)
    RadioButton task;
    @Bind(R.id.completed)
    RadioButton completed;
    @Bind(R.id.set)
    RadioButton set;

    int index;
    int currentIndex = 0;
    RadioButton[] rbs;
    Fragment[] mFragments;
    PointFragment pointFragment;
    TaskFragment taskFragment;
    CompletedFragment completedFragment;
    SetFragment setFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    private void initFragment(){
        mFragments = new Fragment[4];
        //实例化
        pointFragment = new PointFragment();
        taskFragment = new TaskFragment();
        completedFragment = new CompletedFragment();
        setFragment = new SetFragment();
        mFragments[0] = pointFragment;
        mFragments[1] = taskFragment;
        mFragments[2] = completedFragment;
        mFragments[3] = setFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, pointFragment)
                .add(R.id.fragment_container, taskFragment)
                .add(R.id.fragment_container, completedFragment)
                .hide(taskFragment)
                .hide(completedFragment)
                .show(pointFragment)
                .commit();

    }



    @Override
    protected void initView() {
        rbs = new RadioButton[4];
        rbs[0] = point;
        rbs[1] = task;
        rbs[2] = completed;
        rbs[3] = set;
    }

    @Override
    protected void initData() {
        initFragment();
    }

    @Override
    protected void setListener() {

    }

    public void onCheckedChange(View view){
        switch (view.getId()){
            case R.id.point:
                index = 0;
                break;
            case R.id.task:
                    index = 1;
                break;
            case R.id.completed:
                    index = 2;
                break;
            case R.id.set:
                index = 3;
                break;
        }
        setFragments();

    }
    private void setFragments(){
        if (index!=currentIndex){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mFragments[currentIndex]);
            if (!mFragments[index].isAdded()){
                ft.add(R.id.fragment_container,mFragments[index]);
            }
            ft.show(mFragments[index]).commit();
        }
        setRadioButtonStatus();
        currentIndex = index;
    }

    private void setRadioButtonStatus() {
        L.e("index="+index);
        for (int i = 0; i < rbs.length; i++) {
            if (i == index) {
                rbs[i].setChecked(true);
            } else {
                rbs[i].setChecked(false);
            }
        }
    }
    public void onBackPressed(){
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.e(TAG,"onResume...");
        if (index==2 && MediaApplication.getUser()==null){
            index = 0;
        }
        setFragments();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e(TAG,"onActivityResult,requestCode="+requestCode);
        if (MediaApplication.getUser()!=null){
            if (requestCode == I.REQUEST_CODE_LOGIN ){
                index = 2;
            }
            if (requestCode == I.REQUEST_CODE_LOGIN_FROM_CART){
                index = 3;
            }
        }
    }
}
