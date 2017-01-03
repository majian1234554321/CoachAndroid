package com.leyuan.coach.page.mvp.view;

import com.leyuan.coach.bean.ClassSchedule;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/27.
 */
public interface TakeOverClassViewListener {
    void onGetRepalceCourseList(ArrayList<ClassSchedule> arrayList);

    void onAgreeResult(boolean success);

    void onRefuseResult(boolean success);
}
