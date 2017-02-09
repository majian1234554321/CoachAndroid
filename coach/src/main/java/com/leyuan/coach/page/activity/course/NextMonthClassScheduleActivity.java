package com.leyuan.coach.page.activity.course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.bean.CourseResult;
import com.leyuan.coach.bean.MyCalendar;
import com.leyuan.coach.bean.UserCoach;
import com.leyuan.coach.config.Constant;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.mvp.presenter.NextMonthCoursePresenter;
import com.leyuan.coach.page.mvp.view.ClassScheduleViewListener;
import com.leyuan.coach.page.mvp.view.NextMonthCourseViewListener;
import com.leyuan.coach.page.mvp.view.classScheduleView.ClassScheduleViewManager;
import com.leyuan.coach.utils.CourseDateUtils;
import com.leyuan.coach.widget.dialog.BaseDialog;
import com.leyuan.coach.widget.dialog.ButtonCancelListener;
import com.leyuan.coach.widget.dialog.ButtonOkListener;
import com.leyuan.coach.widget.dialog.DialogDoubleButton;
import com.leyuan.commonlibrary.manager.TelephoneManager;
import com.leyuan.commonlibrary.manager.UiManager;
import com.leyuan.commonlibrary.util.MyDateUtils;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/29.
 */
public class NextMonthClassScheduleActivity extends Activity implements View.OnClickListener, NextMonthCourseViewListener, ClassScheduleViewListener {

    private LinearLayout layoutBottom;
    private FrameLayout layoutContainer;

    private NextMonthCoursePresenter presenter;
    private ClassScheduleViewManager viewManager;
    private String dateTag;

    private RequestType requestType = RequestType.UNCONFIRM;
    private boolean firstRequest = true;
    private int currentPosition = -1;
    private String phoneLeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_month_class_schedule);
        presenter = new NextMonthCoursePresenter(this, this);
        viewManager = new ClassScheduleViewManager(this, ClassScheduleViewManager.ScheduleMode.NEXT_MONTH
                , this);
        dateTag = MyDateUtils.getCurrentDay();

        initView();
        initData();
    }

    private void initView() {
        layoutContainer = (FrameLayout) findViewById(R.id.layout_container);
        layoutBottom = (LinearLayout) findViewById(R.id.layout_bottom);

        viewManager.onCreateView(layoutContainer);
        viewManager.onViewCreated();
    }

    private void initData() {
        presenter.getNextMonthUnconfirmCalendar();

        findViewById(R.id.bt_unconfirmed).setSelected(true);
        findViewById(R.id.img_left).setOnClickListener(this);
        findViewById(R.id.confirmed).setOnClickListener(this);
        findViewById(R.id.bt_unconfirmed).setOnClickListener(this);
        findViewById(R.id.bt_contact).setOnClickListener(this);
        findViewById(R.id.bt_know).setOnClickListener(this);

        UserCoach user = App.getInstance().getUser();
        if (user != null)
            phoneLeader = user.getMemberPhone();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.confirmed:
                findViewById(R.id.confirmed).setSelected(true);
                findViewById(R.id.bt_unconfirmed).setSelected(false);
                requestType = RequestType.CONFIRMED;
                presenter.getNextMonthConfirmedCalendar();
                layoutBottom.setVisibility(View.GONE);
                break;
            case R.id.bt_unconfirmed:
                findViewById(R.id.confirmed).setSelected(false);
                findViewById(R.id.bt_unconfirmed).setSelected(true);
                requestType = RequestType.UNCONFIRM;
                presenter.getNextMonthUnconfirmCalendar();
                layoutBottom.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_contact:
                new DialogDoubleButton(this).setContentDesc("拨打电话")
                        .setLeftButton("取消")
                        .setRightButton("拨打")
                        .setContentDesc("" + phoneLeader)
                        .setBtnCancelListener(new ButtonCancelListener() {
                            @Override
                            public void onClick(BaseDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .setBtnOkListener(new ButtonOkListener() {
                            @Override
                            public void onClick(BaseDialog dialog) {
                                TelephoneManager.callImmediate(NextMonthClassScheduleActivity.this, phoneLeader);
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.bt_know:
                presenter.confirmNextMonthCourse(dateTag);
                break;
        }
    }

    @Override
    public void onGetNextMonthUnconfirmCalendar(ArrayList<MyCalendar> myCalendars) {
        if (firstRequest) {
            currentPosition = CourseDateUtils.getFirstHaveCoursePosition(myCalendars);
            firstRequest = false;
        }

        viewManager.onGetCalendarData(myCalendars, currentPosition);
        dateTag = CourseDateUtils.getCalendarDateByPosition(currentPosition
                , myCalendars);
        presenter.getNextMonthUnconfirmCourseList(dateTag);
    }

    @Override
    public void onGetNextMonthconfirmedCalendar(ArrayList<MyCalendar> myCalendars) {
        viewManager.onGetCalendarData(myCalendars, currentPosition);
        dateTag = CourseDateUtils.getCalendarDateByPosition(currentPosition
                , myCalendars);
        presenter.getNextMonthConfirmedCourseList(dateTag);
    }

    @Override
    public void onGetNextMonthCourseList(CourseResult courseNextMonthResult) {
        viewManager.onGetCourseListData(courseNextMonthResult);

        if (requestType == RequestType.UNCONFIRM && courseNextMonthResult != null && courseNextMonthResult.getCoachList() != null
                && !courseNextMonthResult.getCoachList().isEmpty()) {
            layoutBottom.setVisibility(View.VISIBLE);
        } else {
            layoutBottom.setVisibility(View.GONE);
        }

    }

    @Override
    public void nextMonthCourseConfirm(boolean success) {
        if (success) {
            if (requestType == RequestType.UNCONFIRM) {
                presenter.getNextMonthUnconfirmCalendar();
            } else {
                presenter.getNextMonthConfirmedCalendar();
            }
        }
    }

    @Override
    public void onRightButtonClick(int id) {

    }

    @Override
    public void onItemClick(ClassSchedule course) {

    }

    @Override
    public void startActivityForResult(int currentCalendarPosition, Bundle bundle) {
        UiManager.activityJumpForResult(this, bundle, CalendarActivity.class, Constant.REQUEST_CALENDAR);
    }

    @Override
    public void requestCourseData(String currentData, int currentCalendarPosition) {
        currentPosition = currentCalendarPosition;
        dateTag = currentData;
        if (requestType == RequestType.UNCONFIRM) {



            presenter.getNextMonthUnconfirmCourseList(dateTag);
        } else {
            presenter.getNextMonthConfirmedCourseList(dateTag);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewManager.onDestroy();
    }

    enum RequestType {
        CONFIRMED, UNCONFIRM;
    }

}
