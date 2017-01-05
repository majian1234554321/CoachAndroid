package com.leyuan.coach.page.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.leyuan.coach.page.BaseFragment;
import com.leyuan.coach.page.activity.course.CalendarActivity;
import com.leyuan.coach.page.activity.course.MapActivity;
import com.leyuan.coach.page.adapter.CourseAdapterHorizontal;
import com.leyuan.coach.page.adapter.CourseAdapterVertical;
import com.leyuan.coach.page.mvp.presenter.CurrentCoursePresenter;
import com.leyuan.coach.page.mvp.view.CurrentCourseViewListener;
import com.leyuan.coach.utils.LogUtil;
import com.leyuan.coach.widget.CommonTitleLayout;
import com.leyuan.commonlibrary.manager.UiManager;
import com.leyuan.commonlibrary.util.MyDateUtils;
import com.leyuan.commonlibrary.util.NumberUtils;
import com.leyuan.commonlibrary.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/19.
 */
public class ClassScheduleFragment extends BaseFragment implements CourseAdapterVertical.OnCourseItemClickListener, CurrentCourseViewListener, View.OnClickListener {

    private CommonTitleLayout titleLayout;
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

    private ArrayList<MyCalendar> myCalendars;
    private String currentDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_class_shedule, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleLayout = (CommonTitleLayout) view.findViewById(R.id.title_layout);
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

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new CurrentCoursePresenter(getActivity(), this);

        initView();
        initData();
    }

    private void initView() {

        LinearLayoutManager managerHorizontal = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerHan.setLayoutManager(managerHorizontal);

        LinearLayoutManager managerVertical = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        courseAdapterVertical = new CourseAdapterVertical(getActivity(), this);
        recyclerView.setLayoutManager(managerVertical);
        recyclerView.setAdapter(courseAdapterVertical);


    }

    private void initData() {

        presenter.getCurrentMonthCalendar();
        currentDate = MyDateUtils.getCurrentDay();
        presenter.getCourseList(currentDate);
        layoutPreMonth.setOnClickListener(this);
        layoutNextMonth.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_pre_month:

                break;
            case R.id.layout_next_month:
                break;

        }
    }

    @Override
    public void onGetCalendar(final ArrayList<MyCalendar> myCalendars) {
        this.myCalendars = myCalendars;
        courseAdapterHorizontal = new CourseAdapterHorizontal(getActivity(), myCalendars, new CourseAdapterHorizontal.OnItemClickListener() {

            @Override
            public void onItemClick(int currentPosition) {
                Bundle bundle = new Bundle();
                bundle.putInt(StringConstant.POSITION, currentPosition);
//                bundle.putSerializable(StringConstant.ARRAY,myCalendars);
                bundle.putParcelableArrayList(StringConstant.ARRAY, myCalendars);
                UiManager.activityJumpForResult(getActivity(), bundle, CalendarActivity.class, Constant.REQUEST_CALENDAR);
            }
        });
        recyclerHan.setAdapter(courseAdapterHorizontal);
    }

    @Override
    public void onGetCourseList(CourseResult courseResult) {
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

    @Override
    public void toSignIn(int id) {

        presenter.signIn(String.valueOf(id));
    }

    @Override
    public void onSignResult(boolean b) {
        if (b) {
            //update view

            ToastUtil.show(getActivity(), "签到成功");
        }
    }

    @Override
    public void onGetReplaceCourseListResult(ArrayList<ClassSchedule> arrayList) {

    }

    @Override
    public void turnToMap(ClassSchedule course) {
        Bundle bunble = new Bundle();
        bunble.putString(Constant.CURRENT_DATE, currentDate);
        bunble.putParcelable(Constant.CLASS_SCHEDULE, course);
        UiManager.activityJump(getActivity(), bunble, MapActivity.class);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case Constant.REQUEST_CALENDAR:
//                if (resultCode == Constant.RESULT_CALENDAR) {
        int selectPosition = data.getIntExtra(Constant.SELECT_CALENDAR_DAY, 0);
        LogUtil.i("ClassScheduleFragment", "onActivityResult positionClicked  = " + selectPosition);
        recyclerHan.scrollToPosition(selectPosition);

        if (selectPosition > myCalendars.get(0).getDayList().length - 1) {
            selectPosition = selectPosition - myCalendars.get(0).getDayList().length;
            currentDate = myCalendars.get(1).getTimeMouth() + NumberUtils.intToString(selectPosition);
        } else {
            currentDate = myCalendars.get(0).getTimeMouth() + NumberUtils.intToString(selectPosition);
        }

        presenter.getCourseList(currentDate);

//                }
//
//                break;
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
