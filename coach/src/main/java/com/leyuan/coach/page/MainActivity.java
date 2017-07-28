package com.leyuan.coach.page;

import android.content.Intent;
import android.content.IntentFilter;
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
import com.leyuan.coach.bean.CoachInfo;
import com.leyuan.coach.bean.PushExtroInfo;
import com.leyuan.coach.bean.UserCoach;
import com.leyuan.coach.config.Constant;
import com.leyuan.coach.config.ConstantString;
import com.leyuan.coach.page.activity.account.LoginActivity;
import com.leyuan.coach.page.activity.course.NextMonthClassScheduleActivity;
import com.leyuan.coach.page.activity.mine.MessageDetailActivity;
import com.leyuan.coach.page.fragment.CourseScheduleFragment;
import com.leyuan.coach.page.fragment.MineFragment;
import com.leyuan.coach.page.fragment.TrainFragment;
import com.leyuan.coach.page.mvp.presenter.CourseNotifyPresenter;
import com.leyuan.coach.page.mvp.presenter.MessagePresenter;
import com.leyuan.coach.page.mvp.presenter.MinePresenter;
import com.leyuan.coach.page.mvp.view.CourseNotifyViewListener;
import com.leyuan.coach.page.mvp.view.MineViewListener;
import com.leyuan.coach.receivers.NewMessageReceiver;
import com.leyuan.coach.utils.CheckAutoStartUtils;
import com.leyuan.coach.utils.LogUtil;
import com.leyuan.coach.widget.popupwindow.PopupWindowSuspendCourseNotify;
import com.leyuan.coach.widget.popupwindow.PopupWindowTakeOverCourseNotify;
import com.leyuan.commonlibrary.manager.UiManager;
import com.leyuan.commonlibrary.widget.dialog.BaseDialog;
import com.leyuan.commonlibrary.widget.dialog.ButtonCancelListener;
import com.leyuan.commonlibrary.widget.dialog.ButtonOkListener;
import com.leyuan.commonlibrary.widget.dialog.DialogDoubleButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener, CourseNotifyViewListener, MineViewListener {

    private static final java.lang.String TAG = "MainActivity";
    private static final int GET_NOTIFY = 0;
    private RelativeLayout tabCourse;
    private RelativeLayout tabTrain;
    private RelativeLayout tabMineLayout;
    private ImageView imgNewMessage;

    private List<Fragment> mFragments = new ArrayList<>();
    private FragmentManager fm;
    private FragmentTransaction ft;
    private CourseNotifyPresenter courseNotifyPresenter;
    private MessagePresenter messagePresenter;

    private PopupWindowTakeOverCourseNotify popupTackOver;
    private PopupWindowTakeOverCourseNotify popupNewLyCourse;
    private PopupWindowSuspendCourseNotify popupSuspend;
    private int tag = 0;
    //    private PermissionManager permissionManager;
    private NewMessageReceiver newMessageReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getIntent().getIntExtra("tag", 0);
        backstageReceiverNotifyJumpPage();
        courseNotifyPresenter = new CourseNotifyPresenter(this, this);
        setContentView(R.layout.activity_main);
        initView();
        registerMessageReceiver();
        initData();

        checkAutoStart();
        LogUtil.w(TAG, "onCreate");
//        checkPermission();

    }

    private void backstageReceiverNotifyJumpPage() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int type = bundle.getInt(ConstantString.PUSH_TYPE, 0);
            if (type == PushExtroInfo.PushType.NEWS_MESSAGE) {
                tag = 2;
                String backup = bundle.getString(ConstantString.PUSH_BACKUP, "0");
                if (messagePresenter == null)
                    messagePresenter = new MessagePresenter(null, this);
                messagePresenter.updateMsgStatus(backup);
                LogUtil.i(TAG, "backup =  " + backup);

                int value = 0;
                try {
                    value = Integer.parseInt(backup.trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                LogUtil.i(TAG, "value =  " + value);

                Bundle b = new Bundle();
                b.putInt(Constant.MESSAGE_ID, value);
                UiManager.activityJump(this, b, MessageDetailActivity.class);
            } else if (type == PushExtroInfo.PushType.MEXT_MONTH_UNCONFIRMED) {
                tag = 2;
                UiManager.activityJump(this, NextMonthClassScheduleActivity.class);
            } else if (!App.getInstance().isLogin()) {
                UiManager.activityJump(this, LoginActivity.class);
            }
        } else if (!App.getInstance().isLogin()) {
            UiManager.activityJump(this, LoginActivity.class);
        }

    }

    private void registerMessageReceiver() {
        newMessageReceiver = new NewMessageReceiver(imgNewMessage);
        registerReceiver(newMessageReceiver, new IntentFilter(NewMessageReceiver.ACTION));
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
        if (user != null) {
            new MinePresenter(this, this).getUserInfo(String.valueOf(user.getId()));
        }
//        LogUtil.i(TAG, "user.getStatus() = " + user.getStatus());

        tabCourse.setOnClickListener(this);
        tabTrain.setOnClickListener(this);
        tabMineLayout.setOnClickListener(this);
        handler.sendEmptyMessageDelayed(GET_NOTIFY, 1000);


    }

    private void initFragments() {
        fm = getSupportFragmentManager();
        mFragments.add(new CourseScheduleFragment());
        mFragments.add(new TrainFragment());
        mFragments.add(new MineFragment());
        setTabSelection(tag);
        showFragment(tag);
    }

    private void checkAutoStart() {
        if (CheckAutoStartUtils.isNeedCheck(this)) {
            new DialogDoubleButton(this).setContentDesc("为了保证能及时收到代课听课通知，请进入设置把应用加入自启动白名单")
                    .setBtnCancelListener(new ButtonCancelListener() {
                        @Override
                        public void onClick(BaseDialog dialog) {
                            dialog.dismiss();
                        }
                    })
                    .setBtnOkListener(new ButtonOkListener() {
                        @Override
                        public void onClick(BaseDialog dialog) {
                            CheckAutoStartUtils.skipToAutoStartView(MainActivity.this);
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

//    private void checkPermission() {
//        //        PermissionsUtil.checkAndRequestPermissions(this, null);
//        Map<String, String> map = new HashMap<>();
//        map.put(Manifest.permission.ACCESS_FINE_LOCATION, "定位权限");
//        map.put(Manifest.permission.CALL_PHONE, "电话权限");
//
//        permissionManager = new PermissionManager(map, this);
//        permissionManager.checkPermissionList();
//    }

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

        LogUtil.i(TAG, "showFragment tag = " + tag);
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
//            case PushExtroInfo.PushType.NEWS_MESSAGE:
//                imgNewMessage.setVisibility(View.VISIBLE);
//                break;
            case PushExtroInfo.PushType.CURRENT_TAKE_OVER_COURSE:
                courseNotifyPresenter.getReplaceCourseList();
                break;
            case PushExtroInfo.PushType.NOTIFY_SUSPEND_COURSE:
                courseNotifyPresenter.getSuspendCourseList();
                break;
            case PushExtroInfo.PushType.NEWLY_INCREASE_COURSE:
                courseNotifyPresenter.getNewlyIncreaseCourseList();
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
                courseNotifyPresenter.getNewlyIncreaseCourseList();
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
        if (arrayList != null && !arrayList.isEmpty()) {

            for(ClassSchedule course : arrayList){
                course.setCourseType(ClassSchedule.CourseStatus.TAKE_OVER);
            }

            if (popupTackOver == null) {
                popupTackOver = new PopupWindowTakeOverCourseNotify(this);
                popupTackOver.setOnDismissListener(popupDismissListener);
            }
            popupTackOver.showAtBottom(arrayList);
        }
    }

    @Override
    public void onGetSuspendCourseList(ArrayList<ClassSchedule> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            if (popupSuspend == null) {
                for(ClassSchedule course : arrayList){
                    course.setCourseType(ClassSchedule.CourseStatus.SUSPEND);
                }
                popupSuspend = new PopupWindowSuspendCourseNotify(this);
                popupSuspend.setOnDismissListener(popupDismissListener);
            }
            popupSuspend.showAtBottom(arrayList);
        }
    }

    @Override
    public void onGetNewlyIncreaseCourseListResult(ArrayList<ClassSchedule> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            for(ClassSchedule course : arrayList){
                course.setCourseType(ClassSchedule.CourseStatus.NEWLY_INCREASE);
            }

            if (popupNewLyCourse == null) {
                popupNewLyCourse = new PopupWindowTakeOverCourseNotify(this);
                popupNewLyCourse.setOnDismissListener(popupDismissListener);
            }
            popupNewLyCourse.showAtBottom(arrayList);
        }
    }

    private PopupWindow.OnDismissListener popupDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            if (mFragments.get(0) instanceof CourseScheduleFragment) {
                ((CourseScheduleFragment) (mFragments.get(0))).notifyCourseData();
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void setNewMessageVisibility(int visible) {
        imgNewMessage.setVisibility(visible);
    }

    @Override
    public void getUserInfo(CoachInfo userInfo) {
        if (userInfo != null && userInfo.getMsgCou() > 0) {
            imgNewMessage.setVisibility(View.VISIBLE);
        } else {
            imgNewMessage.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.w(TAG, "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.w(TAG, "onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(newMessageReceiver);
        handler.removeCallbacksAndMessages(null);
        LogUtil.w(TAG, "onDestroy");
    }


}
