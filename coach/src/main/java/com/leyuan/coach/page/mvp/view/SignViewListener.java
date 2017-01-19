package com.leyuan.coach.page.mvp.view;

import com.leyuan.coach.bean.ClassSchedule;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/4.
 */
public interface SignViewListener {
    void onGetSignList(ArrayList<ClassSchedule> arrayList, int page);

    void onGetMonthList(ArrayList<String> strings);
}
