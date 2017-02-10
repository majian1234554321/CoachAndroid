package com.leyuan.coach.page;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.bean.PushExtroInfo;
import com.leyuan.coach.bean.UserCoach;
import com.leyuan.coach.config.ConstantString;
import com.leyuan.coach.page.fragment.CourseScheduleFragment;
import com.leyuan.coach.page.fragment.MineFragment;
import com.leyuan.coach.page.fragment.TrainFragment;
import com.leyuan.coach.page.mvp.presenter.CourseNotifyPresenter;
import com.leyuan.coach.page.mvp.view.CourseNotifyViewListener;
import com.leyuan.coach.utils.LogUtil;
import com.leyuan.coach.widget.popupwindow.PopupWindowSuspendCourseNotify;
import com.leyuan.coach.widget.popupwindow.PopupWindowTakeOverCourseNotify;
import com.leyuan.commonlibrary.util.PermissionsUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener, CourseNotifyViewListener {

    private static final java.lang.String TAG = "MainActivity";
    private static final int GET_NOTIFY = 0;
    private RelativeLayout tabCourse;
    private RelativeLayout tabTrain;
    private RelativeLayout tabMineLayout;

    private List<Fragment> mFragments = new ArrayList<>();
    private FragmentManager fm;
    private FragmentTransaction ft;
    private CourseNotifyPresenter courseNotifyPresenter;
    private ImageView imgNewMessage;
    private PopupWindowTakeOverCourseNotify popupTackOver;
    private PopupWindowSuspendCourseNotify popupSuspend;
    private int tag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseNotifyPresenter = new CourseNotifyPresenter(this, this);
        setContentView(R.layout.activity_main);
        if(getIntent() != null){
            tag = getIntent().getIntExtra("tag",0);
        }
        initView();
        initData();

    }

    private void initView() {

        tabCourse = (RelativeLayout) findViewById(R.id.tabCourse);
        tabTrain = (RelativeLayout) findViewById(R.id.tabTrain);
        tabMineLayout = (RelativeLayout) findViewById(R.id.tabMineLayout);
        imgNewMessage = (ImageView) findViewById(R.id.img_new_message);
    }

    private void initData() {
        initFragments();

        UserCoach user = App.getInstance().getUser();
        if (user != null && user.getStatus() == 1) {
            imgNewMessage.setVisibility(View.VISIBLE);
        } else {
            imgNewMessage.setVisibility(View.GONE);
        }

        tabCourse.setOnClickListener(this);
        tabTrain.setOnClickListener(this);
        tabMineLayout.setOnClickListener(this);
        handler.sendEmptyMessageDelayed(GET_NOTIFY, 1000);

        PermissionsUtil.checkAndRequestPermissions(this, null);

//        Intent intent = new Intent();
//        intent.setAction("miui.intent.action.OP_AUTO_START");
//        startActivityForResult(intent,0);

    }


    private void initFragments() {
        fm = getSupportFragmentManager();
        mFragments.add(new CourseScheduleFragment());
        mFragments.add(new TrainFragment());
        mFragments.add(new MineFragment());
        setTabSelection(tag);
        showFragment(tag);
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


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.i(TAG, "onNewIntent");

        Bundle bundle = intent.getExtras();
        if (bundle == null)
            return;
        int type = bundle.getInt(ConstantString.PUSH_TYPE, 0);
        switch (type) {
            case PushExtroInfo.PushType.NEWS_MESSAGE:
                imgNewMessage.setVisibility(View.VISIBLE);
                break;
            case PushExtroInfo.PushType.CURRENT_TAKE_OVER_COURSE:
                courseNotifyPresenter.getReplaceCourseList();
                break;
            case PushExtroInfo.PushType.NOTIFY_SUSPEND_COURSE:
                courseNotifyPresenter.getSuspendCourseList();
                break;
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LogUtil.i(TAG, "onWindowFocusChanged hasFocus : " + hasFocus);
    }

    private long mPressedTime = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GET_NOTIFY) {
                courseNotifyPresenter.getReplaceCourseList();
                courseNotifyPresenter.getSuspendCourseList();
            }
        }
    };

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

    @Override
    public void onGetReplaceCourseListResult(ArrayList<ClassSchedule> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()){
            if(popupTackOver == null){
                popupTackOver =  new PopupWindowTakeOverCourseNotify(this);
                popupTackOver.setOnDismissListener(popupDismissListener);
            }
            popupTackOver.showAtBottom(arrayList);
        }
    }

    @Override
    public void onGetSuspendCourseList(ArrayList<ClassSchedule> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()){
            if(popupSuspend == null){
                popupSuspend =  new PopupWindowSuspendCourseNotify(this);
                popupSuspend.setOnDismissListener(popupDismissListener);
            }
            popupSuspend.showAtBottom(arrayList);
        }
    }

    private PopupWindow.OnDismissListener popupDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
                if(mFragments.get(0) instanceof CourseScheduleFragment){
                    ((CourseScheduleFragment)(mFragments.get(0))).notifyCourseData();
                }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionsUtil.REQUEST_STATUS_CODE) {

            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {//定位权限
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//同意
                    PermissionsUtil.checkAndRequestPermissions(this, null);
                } else {//不同意
                    Toast.makeText(MainActivity.this, "在设置-应用-" + getString(R.string.app_name) + "-权限中开启定位权限，以正常使用App功能", Toast.LENGTH_SHORT).show();
                }
            }

            if (permissions[0].equals(Manifest.permission.CALL_PHONE)) {//电话权限
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//同意
                    PermissionsUtil.checkAndRequestPermissions(this, null);
                } else {//不同意
                    Toast.makeText(MainActivity.this, "在设置-应用-" + getString(R.string.app_name) + "-权限中开启电话权限，以正常使用App功能", Toast.LENGTH_SHORT).show();

                }
            }

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
