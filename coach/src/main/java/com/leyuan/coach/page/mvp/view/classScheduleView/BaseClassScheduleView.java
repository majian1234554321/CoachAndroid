package com.leyuan.coach.page.mvp.view.classScheduleView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.bean.CourseResult;
import com.leyuan.coach.bean.MyCalendar;
import com.leyuan.coach.config.Constant;
import com.leyuan.coach.config.ConstantString;
import com.leyuan.coach.page.adapter.CourseAdapterHorizontal;
import com.leyuan.coach.page.adapter.CourseAdapterVertical;
import com.leyuan.coach.page.mvp.view.ClassScheduleViewListener;
import com.leyuan.coach.utils.CourseDateUtils;
import com.leyuan.coach.widget.CommonEmptyLayout;
import com.leyuan.commonlibrary.manager.LinearLayoutManagerNoScroll;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/9.
 */

public abstract class BaseClassScheduleView implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private Context context;

    protected RecyclerView recyclerHan;
    protected LinearLayout layoutPreMonth;
    protected ImageView imageView;
    protected TextView txtPreMonth;
    protected TextView txtPreMonthClassNumber;
    protected LinearLayout layoutNextMonth;
    protected TextView txtSignHint;
    protected TextView txtNextMonth;
    protected TextView txtNextMonthClassNumber;
    protected TextView txtClassNumber;
    protected RecyclerView recyclerView;

    private CourseAdapterHorizontal courseAdapterHorizontal;
    private CourseAdapterVertical courseAdapterVertical;

    private ArrayList<MyCalendar> myCalendars = new ArrayList<>();

    private int currentCalendarPosition;
    private int totalCalendarItem;
    private ArrayList<Integer> calendarCourseNumberArray = new ArrayList<>();
    private ClassScheduleViewListener listener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CommonEmptyLayout emptyView;

    protected BaseClassScheduleView(Context context, ClassScheduleViewListener listener) {
        this.context = context;
        this.listener = listener;
    }

    protected BaseClassScheduleView(Context context) {
        this.context = context;
    }

    public View createView(LayoutInflater inflater) {
        return createView(inflater, null, false);
    }

    public View createView(LayoutInflater inflater, ViewGroup container,
                           boolean attachRoot) {
        View view = inflater.inflate(R.layout.layout_class_schedule, container, attachRoot);

        recyclerHan = (RecyclerView) view.findViewById(R.id.recyclerHan);
        layoutPreMonth = (LinearLayout) view.findViewById(R.id.layout_pre_month);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        txtPreMonth = (TextView) view.findViewById(R.id.txt_pre_month);
        txtPreMonthClassNumber = (TextView) view.findViewById(R.id.txt_pre_month_class_number);
        layoutNextMonth = (LinearLayout) view.findViewById(R.id.layout_next_month);
        txtNextMonth = (TextView) view.findViewById(R.id.txt_next_month);
        txtNextMonthClassNumber = (TextView) view.findViewById(R.id.txt_next_month_class_number);
        txtClassNumber = (TextView) view.findViewById(R.id.txt_class_number);
        txtSignHint = (TextView) view.findViewById(R.id.txt_sign_hint);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        emptyView = (CommonEmptyLayout) view.findViewById(R.id.empty_view);

        return view;
    }

    public void initViewData() {
        initView();
        initData();
    }


    private void initView() {

        LinearLayoutManagerNoScroll managerHorizontal = new LinearLayoutManagerNoScroll(context, LinearLayoutManager.HORIZONTAL, false);
        managerHorizontal.setScrollHorizontalEnabled(false);
        recyclerHan.setLayoutManager(managerHorizontal);
        courseAdapterHorizontal = new CourseAdapterHorizontal(context, myCalendars, horizontalItemClickListener);
        recyclerHan.setAdapter(courseAdapterHorizontal);


        LinearLayoutManager managerVertical = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        courseAdapterVertical = new CourseAdapterVertical(context, verticalItemClickListener);
        recyclerView.setLayoutManager(managerVertical);
        recyclerView.setAdapter(courseAdapterVertical);


    }

    private void initData() {
        swipeRefreshLayout.setOnRefreshListener(this);
        setHintLayout(txtSignHint);
        layoutPreMonth.setOnClickListener(this);
        layoutNextMonth.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_pre_month:
                if (currentCalendarPosition <= 0) {
                    return;
                }
                currentCalendarPosition--;
                refreshPreNextState();

                break;
            case R.id.layout_next_month:
                if (currentCalendarPosition >= totalCalendarItem - 1) {
                    return;
                }
                currentCalendarPosition++;
                refreshPreNextState();

                break;
        }
    }

    @Override
    public void onRefresh() {
        listener.onRefresh();

    }

    private CourseAdapterHorizontal.OnItemClickListener horizontalItemClickListener = new CourseAdapterHorizontal.OnItemClickListener() {

        @Override
        public void onItemClick(int currentPosition) {
            currentCalendarPosition = currentPosition;

            Bundle bundle = new Bundle();
            bundle.putInt(ConstantString.POSITION, currentPosition);
            bundle.putParcelableArrayList(ConstantString.ARRAY, myCalendars);

            listener.startActivityForResult(currentCalendarPosition, bundle);
        }
    };

    private CourseAdapterVertical.OnCourseItemClickListener verticalItemClickListener = new CourseAdapterVertical.OnCourseItemClickListener() {
        @Override
        public void onRightButtonClick(int id) {
            listener.onRightButtonClick(id);
        }

        @Override
        public void onItemClick(ClassSchedule course) {
            listener.onItemClick(course);
        }

        @Override
        public void onEditCourseJoinNum(ClassSchedule course) {
            listener.onEditCourseJoinNum(course);
        }
    };

    private void refreshPreNextState() {
        recyclerHan.scrollToPosition(currentCalendarPosition);
        refreshPreNextView(currentCalendarPosition);

        listener.requestCourseData(CourseDateUtils.getCalendarDateByPosition(
                currentCalendarPosition, myCalendars), currentCalendarPosition);
    }

    private void refreshPreNextView(int currentCalendarPosition) {
        if (currentCalendarPosition <= 0) {
            //no pre style
            txtPreMonth.setTextColor(context.getResources().getColor(R.color.text_gray));
            txtPreMonthClassNumber.setVisibility(View.GONE);
        } else {
            txtPreMonth.setTextColor(context.getResources().getColor(R.color.black));
            txtPreMonthClassNumber.setVisibility(View.VISIBLE);
            txtPreMonthClassNumber.setText(calendarCourseNumberArray.get(currentCalendarPosition - 1) + "节课");
            // have pre style
        }

        if (currentCalendarPosition >= totalCalendarItem - 1) {
            //no next style
            txtNextMonth.setTextColor(context.getResources().getColor(R.color.text_gray));
            txtNextMonthClassNumber.setVisibility(View.GONE);

        } else {
            // have next style
            txtNextMonth.setTextColor(context.getResources().getColor(R.color.black));
            txtNextMonthClassNumber.setVisibility(View.VISIBLE);
            txtNextMonthClassNumber.setText(calendarCourseNumberArray.get(currentCalendarPosition + 1) + "节课");
        }
    }

    public void setCalendarData(final ArrayList<MyCalendar> myCalendars, int calendarPosition) {
        if (myCalendars == null)
            return;
        courseAdapterHorizontal.refreshData(myCalendars);
        this.myCalendars = myCalendars;
        totalCalendarItem = 0;
        calendarCourseNumberArray.clear();

        for (MyCalendar calendar : myCalendars) {
            totalCalendarItem += calendar.getDayList().length;
            for (int i = 0; i < calendar.getDayList().length; i++) {
                calendarCourseNumberArray.add(calendar.getDayList()[i]);
            }
        }
        currentCalendarPosition = calendarPosition;

        recyclerHan.scrollToPosition(currentCalendarPosition);
        refreshPreNextView(currentCalendarPosition);
    }


    public void setCourseList(CourseResult courseResult) {
        swipeRefreshLayout.setRefreshing(false);
        if (courseResult == null) {
            courseAdapterVertical.refreshData(null, CourseDateUtils.getCalendarDateByPosition(currentCalendarPosition, myCalendars));
            return;
        }
        setHintCourse(courseResult);
        courseAdapterVertical.refreshData(courseResult.getCoachList(),CourseDateUtils.getCalendarDateByPosition(currentCalendarPosition, myCalendars));
        if (courseResult.getCoachList() == null || courseResult.getCoachList().isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    public void setActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.REQUEST_CALENDAR:
                if (resultCode == Constant.RESULT_CALENDAR) {
                    currentCalendarPosition = data.getIntExtra(Constant.SELECT_CALENDAR_DAY, 0);
                    refreshPreNextState();
                }

                break;
        }
    }

    public void destroyView() {

    }

    public abstract void setHintCourse(CourseResult courseResult);

    public abstract void setHintLayout(View txtSignHint);

}
