package com.leyuan.coach.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leyuan.coach.R;
import com.leyuan.coach.page.fragment.ClassScheduleFragment;
import com.leyuan.coach.page.fragment.MineFragment;
import com.leyuan.coach.page.fragment.TrainFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout tabCourse;
    private RelativeLayout tabTrain;
    private RelativeLayout tabMineLayout;

    private List<Fragment> mFragments = new ArrayList<>();
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    private void initView() {

        tabCourse = (RelativeLayout) findViewById(R.id.tabCourse);
        tabTrain = (RelativeLayout) findViewById(R.id.tabTrain);
        tabMineLayout = (RelativeLayout) findViewById(R.id.tabMineLayout);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    private void initData() {
        initFragments();
        tabCourse.setOnClickListener(this);
        tabTrain.setOnClickListener(this);
        tabMineLayout.setOnClickListener(this);
    }

    private void initFragments() {
        fm = getSupportFragmentManager();
        mFragments.add(new ClassScheduleFragment());
        mFragments.add(new TrainFragment());
        mFragments.add(new MineFragment());
        setTabSelection(0);
        showFragment(0);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tabCourse:
                setTabSelection(0);
                showFragment(0);
                break;
            case R.id.tabTrain:
                setTabSelection(1);
                showFragment(1);
                break;
            case R.id.tabMineLayout:
                setTabSelection(2);
                showFragment(2);
                break;
        }
    }

    private void showFragment(int tag) {

        ft = fm.beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            if (i != tag) {
                if (mFragments.get(i).isAdded()
                        && mFragments.get(i).isVisible()) {
                    ft.hide(mFragments.get(i));
                }
            } else {
                if (!mFragments.get(i).isAdded()) {
                    ft.add(R.id.frame, mFragments.get(i));
                }
                if (mFragments.get(i).isHidden()) {
                    ft.show(mFragments.get(i));
                }
            }
        }
        ft.commit();

    }

    private void setTabSelection(int index) {
        resetTabBtn();
        switch (index) {
            case 0:
                tabCourse.setSelected(true);
                tabCourse.setClickable(false);
                break;
            case 1:
                tabTrain.setSelected(true);
                tabTrain.setClickable(false);
                break;
            case 2:
                tabMineLayout.setSelected(true);
                tabMineLayout.setClickable(false);
                break;
        }
    }

    protected void resetTabBtn() {
        tabCourse.setSelected(false);
        tabTrain.setSelected(false);
        tabMineLayout.setSelected(false);

        tabCourse.setClickable(true);
        tabTrain.setClickable(true);
        tabMineLayout.setClickable(true);
    }

    private long mPressedTime = 0;

    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//退出程序
            exitApp();
        }
    }

}
