package com.leyuan.coach.page.mvp.view.implement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.leyuan.coach.config.StringConstant;
import com.leyuan.coach.page.adapter.CourseAdapterHorizontal;
import com.leyuan.coach.page.adapter.CourseAdapterVertical;
import com.leyuan.coach.page.mvp.presenter.CurrentCoursePresenter;
import com.leyuan.commonlibrary.manager.LinearLayoutManagerNoScroll;
import com.leyuan.commonlibrary.util.MyDateUtils;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/9.
 */

public abstract class BaseClassScheduleView implements CourseAdapterVertical.OnCourseItemClickListener, View.OnClickListener {
    private Context context;

    private RecyclerView recyclerHan;
    private LinearLayout layoutPreMonth;
    private ImageView imageView;
    private TextView txtPreMonth;
    private TextView txtPreMonthClassNumber;
    private LinearLayout layoutNextMonth;
    private TextView txtSignAll;
    private TextView txtNextMonth;
    private TextView txtNextMonthClassNumber;
    private TextView txtClassNumber;
    private RecyclerView recyclerView;

    private CourseAdapterHorizontal courseAdapterHorizontal;
    private CourseAdapterVertical courseAdapterVertical;

    private CurrentCoursePresenter presenter;

    private ArrayList<MyCalendar> myCalendars = new ArrayList<>();

    private int currentCalendarPosition;
    private int totalCalendarItem;
    private ArrayList<Integer> calendarCourseNumberArray = new ArrayList<>();
    private ClassScheduleViewListener listener;

    public BaseClassScheduleView(Context context, ClassScheduleViewListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public View createView(LayoutInflater inflater) {

        return createView(inflater, null, false);
    }

    public View createView(LayoutInflater inflater, ViewGroup container,
                           boolean attachRoot) {
        View view = inflater.inflate(R.layout.layout_class_schedule, null, false);

        recyclerHan = (RecyclerView) view.findViewById(R.id.recyclerHan);
        layoutPreMonth = (LinearLayout) view.findViewById(R.id.layout_pre_month);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        txtPreMonth = (TextView) view.findViewById(R.id.txt_pre_month);
        txtPreMonthClassNumber = (TextView) view.findViewById(R.id.txt_pre_month_class_number);
        layoutNextMonth = (LinearLayout) view.findViewById(R.id.layout_next_month);
        txtNextMonth = (TextView) view.findViewById(R.id.txt_next_month);
        txtNextMonthClassNumber = (TextView) view.findViewById(R.id.txt_next_month_class_number);
        txtClassNumber = (TextView) view.findViewById(R.id.txt_class_number);
        txtSignAll = (TextView) view.findViewById(R.id.txt_sign_all);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);


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

        LinearLayoutManager managerVertical = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        courseAdapterVertical = new CourseAdapterVertical(context, this);
        recyclerView.setLayoutManager(managerVertical);
        recyclerView.setAdapter(courseAdapterVertical);


    }

    private void initData() {
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
    public void onRightButtonClick(int id) {
        listener.onRightButtonClick(id);
    }

    @Override
    public void onItemClick(ClassSchedule course) {
        listener.onItemClick(course);
    }


    private void refreshPreNextState() {
        recyclerHan.scrollToPosition(currentCalendarPosition);
        refreshPreNextView(currentCalendarPosition);
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

    public void setCalendarData(final ArrayList<MyCalendar> myCalendars) {
        if (myCalendars == null)
            return;
        this.myCalendars.clear();
        this.myCalendars.addAll(myCalendars);

        courseAdapterHorizontal = new CourseAdapterHorizontal(context, myCalendars, new CourseAdapterHorizontal.OnItemClickListener() {

            @Override
            public void onItemClick(int currentPosition) {
                currentCalendarPosition = currentPosition;

                Bundle bundle = new Bundle();
                bundle.putInt(StringConstant.POSITION, currentPosition);
                bundle.putParcelableArrayList(StringConstant.ARRAY, myCalendars);

                listener.startActivityForResult(currentCalendarPosition, bundle);

            }
        });
        recyclerHan.setAdapter(courseAdapterHorizontal);

        for (MyCalendar calendar : myCalendars) {
            totalCalendarItem += calendar.getDayList().length;

//            currentCalendarPosition = MyDateUtils.getCurrentPositionByMonth(calendar.getTimeMouth());
            for (int i = 0; i < calendar.getDayList().length; i++) {
                calendarCourseNumberArray.add(calendar.getDayList()[i]);
            }
        }

        for (MyCalendar calendar : myCalendars) {
            if (MyDateUtils.getCurrentPositionByMonth(calendar.getTimeMouth()) >= 0) {
                currentCalendarPosition += MyDateUtils.getCurrentPositionByMonth(calendar.getTimeMouth());
                break;
            } else {
                currentCalendarPosition += calendar.getDayList().length;
            }
        }

        recyclerHan.scrollToPosition(currentCalendarPosition);
        refreshPreNextView(currentCalendarPosition);
    }


    public void setCourseList(CourseResult courseResult) {
        if (courseResult == null) {
            courseAdapterVertical.refreshData(new ArrayList<ClassSchedule>());
            return;
        }
        txtClassNumber.setText(courseResult.getDateTime() + " 本月共" + courseResult.getCourseSize() + "节课");
        if (courseResult.getNormalCou() == 0 && courseResult.getAbnormalCou() == 0) {
            txtSignAll.setText("注:请于课前15分钟内签到");
        } else {
            txtSignAll.setText("正常签到" + courseResult.getNormalCou() + "节 " + "异常签到" + courseResult.getAbnormalCou() + "节");
        }

        courseAdapterVertical.refreshData(courseResult.getCoachList());
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

    public void destoryView() {

    }
}
