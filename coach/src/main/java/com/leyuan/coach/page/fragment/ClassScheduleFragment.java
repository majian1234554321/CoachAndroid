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
import com.leyuan.coach.config.ConstantString;
import com.leyuan.coach.page.BaseFragment;
import com.leyuan.coach.page.activity.course.CalendarActivity;
import com.leyuan.coach.page.activity.course.MapActivity;
import com.leyuan.coach.page.adapter.CourseAdapterHorizontal;
import com.leyuan.coach.page.adapter.CourseAdapterVertical;
import com.leyuan.coach.page.mvp.presenter.CurrentCoursePresenter;
import com.leyuan.coach.page.mvp.view.CurrentCourseViewListener;
import com.leyuan.coach.utils.CourseDateUtils;
import com.leyuan.coach.widget.CommonTitleLayout;
import com.leyuan.commonlibrary.manager.LinearLayoutManagerNoScroll;
import com.leyuan.commonlibrary.manager.UiManager;
import com.leyuan.commonlibrary.util.DialogUtils;
import com.leyuan.commonlibrary.util.MyDateUtils;
import com.leyuan.commonlibrary.util.ToastUtil;

import java.util.ArrayList;



@Deprecated
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

    private ArrayList<MyCalendar> myCalendars = new ArrayList<>();

    private String currentDate;
    private int currentCalendarPosition;
    private int totalCalendarItem;
    private ArrayList<Integer> calendarCourseNumberArray = new ArrayList<>();

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
        txtSignAll = (TextView) view.findViewById(R.id.txt_sign_hint);
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

        LinearLayoutManagerNoScroll managerHorizontal = new LinearLayoutManagerNoScroll(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        managerHorizontal.setScrollHorizontalEnabled(false);

        courseAdapterHorizontal = new CourseAdapterHorizontal(getActivity(), new CourseAdapterHorizontal.OnItemClickListener() {

            @Override
            public void onItemClick(int currentPosition) {

                currentCalendarPosition = currentPosition;
                Bundle bundle = new Bundle();
                bundle.putInt(ConstantString.POSITION, currentPosition);
                bundle.putParcelableArrayList(ConstantString.ARRAY, myCalendars);

                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(getActivity(), CalendarActivity.class);
                startActivityForResult(intent, Constant.REQUEST_CALENDAR);

            }
        });
        recyclerHan.setAdapter(courseAdapterHorizontal);
        recyclerHan.setLayoutManager(managerHorizontal);

        LinearLayoutManager managerVertical = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        courseAdapterVertical = new CourseAdapterVertical(getActivity(), this);
        recyclerView.setLayoutManager(managerVertical);
        recyclerView.setAdapter(courseAdapterVertical);


    }

    private void initData() {

        currentDate = MyDateUtils.getCurrentDay();
        layoutPreMonth.setOnClickListener(this);
        layoutNextMonth.setOnClickListener(this);

        DialogUtils.showDialog(getActivity(), "", false);
        presenter.getCourseList(currentDate);
        presenter.getCurrentMonthCalendar();
//        presenter.getReplaceCourseList();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
    public void onGetCalendar(final ArrayList<MyCalendar> myCalendars) {

        DialogUtils.dismissDialog();
        if (myCalendars == null)
            return;

        courseAdapterHorizontal.refreshData(myCalendars);
        this.myCalendars.clear();
        this.myCalendars.addAll(myCalendars);


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

    @Override
    public void onGetCourseList(CourseResult courseResult) {

        DialogUtils.dismissDialog();

        if (courseResult == null) {
            courseAdapterVertical.refreshData(new ArrayList<ClassSchedule>(), CourseDateUtils.getCalendarDateByPosition(currentCalendarPosition, myCalendars));
            return;
        }
        txtClassNumber.setText(courseResult.getDateTime() + " 本月共" + courseResult.getCourseSize() + "节课");
        if (courseResult.getNormalCou() == 0 && courseResult.getAbnormalCou() == 0) {
            txtSignAll.setText("注:请于课前15分钟内签到");
        } else {
            txtSignAll.setText("正常签到" + courseResult.getNormalCou() + "节 " + "异常签到" + courseResult.getAbnormalCou() + "节");
        }

        courseAdapterVertical.refreshData(courseResult.getCoachList(), CourseDateUtils.getCalendarDateByPosition(currentCalendarPosition, myCalendars));

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
            ToastUtil.show(getActivity(), "签到成功");
        }
    }

    @Deprecated
    public void getTackoverCourseList() {
//        DialogUtils.showDialog(getActivity(), "", false);
//        presenter.getReplaceCourseList();
    }

    @Deprecated
    @Override
    public void onGetReplaceCourseListResult(ArrayList<ClassSchedule> arrayList) {
//        DialogUtils.dismissDialog();
//        if (arrayList != null)
//            new PopupWindowClassNotify(getActivity()).showAtBottom(arrayList);
    }

    @Deprecated
    @Override
    public void onGetSuspendCourseList(ArrayList<ClassSchedule> arrayList) {

    }

    @Override
    public void onRegisterCourseNumResult(boolean success) {

    }

    @Override
    public void onItemClick(ClassSchedule course) {
        Bundle bunble = new Bundle();
        bunble.putString(Constant.CURRENT_DATE, currentDate);
        bunble.putParcelable(Constant.CLASS_SCHEDULE, course);
        UiManager.activityJump(getActivity(), bunble, MapActivity.class);
    }

    @Override
    public void onEditCourseJoinNum(ClassSchedule course) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUEST_CALENDAR:
                if (resultCode == Constant.RESULT_CALENDAR) {
                    currentCalendarPosition = data.getIntExtra(Constant.SELECT_CALENDAR_DAY, 0);
                    refreshPreNextState();
                }

                break;
        }
    }

    private void refreshPreNextState() {
        currentDate = getCurrentDataByPositin(currentCalendarPosition);

        recyclerHan.scrollToPosition(currentCalendarPosition);
        refreshPreNextView(currentCalendarPosition);
        presenter.getCourseList(currentDate);
        DialogUtils.showDialog(getActivity(), "", false);
    }

    private void refreshPreNextView(int currentCalendarPosition) {
        if (currentCalendarPosition <= 0) {
            //no pre style
            txtPreMonth.setTextColor(getResources().getColor(R.color.text_gray));
            txtPreMonthClassNumber.setVisibility(View.GONE);
        } else {
            txtPreMonth.setTextColor(getResources().getColor(R.color.black));
            txtPreMonthClassNumber.setVisibility(View.VISIBLE);
            txtPreMonthClassNumber.setText(calendarCourseNumberArray.get(currentCalendarPosition - 1) + "节课");
            // have pre style
        }

        if (currentCalendarPosition >= totalCalendarItem - 1) {
            //no next style
            txtNextMonth.setTextColor(getResources().getColor(R.color.text_gray));
            txtNextMonthClassNumber.setVisibility(View.GONE);

        } else {
            // have next style
            txtNextMonth.setTextColor(getResources().getColor(R.color.black));
            txtNextMonthClassNumber.setVisibility(View.VISIBLE);
            txtNextMonthClassNumber.setText(calendarCourseNumberArray.get(currentCalendarPosition + 1) + "节课");
        }
    }

    private String getCurrentDataByPositin(int currentCalendarPosition) {
        String time = "";
        int temp = 0, monthClickedIndex = 0;
        for (; monthClickedIndex < myCalendars.size(); monthClickedIndex++) {
            temp += myCalendars.get(monthClickedIndex).getDayList().length;
            if (currentCalendarPosition < temp) {
                time = MyDateUtils.formatYearMonthDay(myCalendars.get(monthClickedIndex).getTimeMouth(),
                        currentCalendarPosition - temp + myCalendars.get(monthClickedIndex).getDayList().length);
                break;
            }
        }
        return time;
    }

    public void notifyCourseData() {
        presenter.getCourseList(currentDate);
        presenter.getCurrentMonthCalendar();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogUtils.releaseDialog();
    }

}
