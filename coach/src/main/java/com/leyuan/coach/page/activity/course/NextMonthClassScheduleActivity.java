package com.leyuan.coach.page.activity.course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.bean.CourseResult;
import com.leyuan.coach.bean.MyCalendar;
import com.leyuan.coach.config.Constant;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.mvp.presenter.NextMonthCoursePresenter;
import com.leyuan.coach.page.mvp.view.ClassScheduleViewListener;
import com.leyuan.coach.page.mvp.view.NextMonthCourseViewListener;
import com.leyuan.coach.page.mvp.view.classScheduleView.ClassScheduleViewManager;
import com.leyuan.coach.utils.CourseDateUtils;
import com.leyuan.coach.utils.LogUtil;
import com.leyuan.commonlibrary.manager.TelephoneManager;
import com.leyuan.commonlibrary.manager.UiManager;
import com.leyuan.commonlibrary.util.MyDateUtils;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/29.
 */
public class NextMonthClassScheduleActivity extends Activity implements View.OnClickListener, NextMonthCourseViewListener, ClassScheduleViewListener {

    private FrameLayout layoutContainer;
    private NextMonthCoursePresenter presenter;
    private ClassScheduleViewManager viewManager;
    private String dateTag;
    private RequestType requestType = RequestType.UNCONFIRM;

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
        viewManager.onCreateView(layoutContainer);
        viewManager.onViewCreated();
    }

    private void initData() {
        presenter.getNextMonthCalendar();
//        presenter.getNextMonthUnconfirmCourseList(MyDateUtils.getCurrentDay());
        findViewById(R.id.bt_unconfirmed).setSelected(true);

        findViewById(R.id.img_left).setOnClickListener(this);
        findViewById(R.id.confirmed).setOnClickListener(this);
        findViewById(R.id.bt_unconfirmed).setOnClickListener(this);
        findViewById(R.id.bt_contact).setOnClickListener(this);
        findViewById(R.id.bt_know).setOnClickListener(this);
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
                presenter.getNextMonthConfirmedCourseList(dateTag);
                break;
            case R.id.bt_unconfirmed:
                findViewById(R.id.confirmed).setSelected(false);
                findViewById(R.id.bt_unconfirmed).setSelected(true);
                requestType = RequestType.UNCONFIRM;
                presenter.getNextMonthUnconfirmCourseList(dateTag);
                break;
            case R.id.bt_contact:
                TelephoneManager.callImmediate(App.getInstance().getUser().getMemberPhone());
                break;
            case R.id.bt_know:
                presenter.confirmNextMonthCourse(dateTag);
                break;
        }
    }

    @Override
    public void onGetNextMonthCalendar(ArrayList<MyCalendar> myCalendars) {

        viewManager.onGetCalendarData(myCalendars);

        dateTag = CourseDateUtils.getCalendarDateByPosition(CourseDateUtils.getFirstHaveCoursePosition(myCalendars)
                , myCalendars);
        LogUtil.i("course", "getCalendarDateByPosition dateTag  = " + dateTag);
        presenter.getNextMonthUnconfirmCourseList(dateTag);
    }

    @Override
    public void courseNextMonthResult(CourseResult courseNextMonthResult) {
        viewManager.onGetCourseListData(courseNextMonthResult);
    }

    @Override
    public void nextMonthCourseConfirm(boolean success) {
        presenter.confirmNextMonthCourse(dateTag);
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
    public void refreshCourseList(String currentData) {
        dateTag = currentData;
        presenter.getNextMonthUnconfirmCourseList(dateTag);
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
