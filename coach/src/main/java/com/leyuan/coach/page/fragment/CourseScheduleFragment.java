package com.leyuan.coach.page.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.bean.CourseResult;
import com.leyuan.coach.bean.MyCalendar;
import com.leyuan.coach.config.Constant;
import com.leyuan.coach.page.BaseFragment;
import com.leyuan.coach.page.activity.course.CalendarActivity;
import com.leyuan.coach.page.activity.course.MapActivity;
import com.leyuan.coach.page.mvp.presenter.CurrentCoursePresenter;
import com.leyuan.coach.page.mvp.view.ClassScheduleViewListener;
import com.leyuan.coach.page.mvp.view.CurrentCourseViewListener;
import com.leyuan.coach.page.mvp.view.classScheduleView.ClassScheduleViewManager;
import com.leyuan.coach.widget.CommonTitleLayout;
import com.leyuan.commonlibrary.manager.UiManager;
import com.leyuan.commonlibrary.util.DialogUtils;
import com.leyuan.commonlibrary.util.MyDateUtils;
import com.leyuan.commonlibrary.util.ToastUtil;

import java.util.ArrayList;


/**
 * Created by user on 2016/12/19.
 */
public class CourseScheduleFragment extends BaseFragment implements CurrentCourseViewListener, ClassScheduleViewListener {

    private CommonTitleLayout titleLayout;
    private FrameLayout layoutContainer;

    private CurrentCoursePresenter presenter;
    private ClassScheduleViewManager viewManager;
    private String dateTag;
    private boolean firstRequest = true;

    private int currentPosition;
    private String phoneLeader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_class_shedule, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleLayout = (CommonTitleLayout) view.findViewById(R.id.title_layout);
        layoutContainer = (FrameLayout) view.findViewById(R.id.layout_container);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new CurrentCoursePresenter(getActivity(), this);
        viewManager = new ClassScheduleViewManager(getActivity(), ClassScheduleViewManager.ScheduleMode.CURRENT_MONTH
                , this);
        dateTag = MyDateUtils.getCurrentDay();
        initView();
        initData();
    }

    private void initView() {
        viewManager.onCreateView(layoutContainer);
        viewManager.onViewCreated();
    }

    private void initData() {
        DialogUtils.showDialog(getActivity(), "", false);
        presenter.getCourseList(dateTag);
        presenter.getCurrentMonthCalendar();
    }


    @Override
    public void onGetCalendar(final ArrayList<MyCalendar> myCalendars) {
        DialogUtils.dismissDialog();
        if (myCalendars == null)
            return;
        if (firstRequest) {
            for (MyCalendar calendar : myCalendars) {
                if (MyDateUtils.getCurrentPositionByMonth(calendar.getTimeMouth()) >= 0) {
                    currentPosition += MyDateUtils.getCurrentPositionByMonth(calendar.getTimeMouth());
                    break;
                } else {
                    currentPosition += calendar.getDayList().length;
                }
            }
            firstRequest = false;
        }
        viewManager.onGetCalendarData(myCalendars, currentPosition);
    }

    @Override
    public void onGetCourseList(CourseResult courseResult) {

        DialogUtils.dismissDialog();
        viewManager.onGetCourseListData(courseResult);
    }

    @Override
    public void onRightButtonClick(int id) {
        DialogUtils.showDialog(getActivity(), "", false);
        presenter.signIn(String.valueOf(id));
    }

    @Override
    public void onSignResult(boolean b) {
        DialogUtils.dismissDialog();
        if (b) {
            DialogUtils.showDialog(getActivity(), "", false);
            presenter.getCourseList(dateTag);
            ToastUtil.show(getActivity(), "签到成功");
        }
    }

    @Override
    public void onGetReplaceCourseListResult(ArrayList<ClassSchedule> arrayList) {

    }

    @Override
    public void onGetSuspendCourseList(ArrayList<ClassSchedule> arrayList) {

    }


    @Override
    public void onItemClick(ClassSchedule course) {
        Bundle bunble = new Bundle();
        bunble.putString(Constant.CURRENT_DATE, dateTag);
        bunble.putParcelable(Constant.CLASS_SCHEDULE, course);
        UiManager.activityJump(getActivity(), bunble, MapActivity.class);
    }

    @Override
    public void startActivityForResult(int currentCalendarPosition, Bundle bundle) {
        UiManager.activityJumpForResult(this, bundle, CalendarActivity.class, Constant.REQUEST_CALENDAR);
    }

    @Override
    public void requestCourseData(String currentData, int currentCalendarPosition) {
        currentPosition = currentCalendarPosition;
        dateTag = currentData;
        presenter.getCourseList(dateTag);
    }

    @Override
    public void onRefresh() {
        presenter.getCourseList(dateTag);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewManager.onActivityResult(requestCode, resultCode, data);
    }

    public void notifyCourseData() {
        presenter.getCurrentMonthCalendar();
        presenter.getCourseList(dateTag);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogUtils.releaseDialog();
        viewManager.onDestroy();
    }

}
