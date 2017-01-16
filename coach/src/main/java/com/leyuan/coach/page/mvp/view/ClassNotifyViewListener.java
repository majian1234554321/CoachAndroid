package com.leyuan.coach.page.mvp.view;

/**
 * Created by user on 2016/12/27.
 */
public interface ClassNotifyViewListener {
//    void onGetRepalceCourseList(ArrayList<ClassSchedule> arrayList);

    void onAgreeResult(boolean success, int currentItem);

    void onRefuseResult(boolean success, int currentItem);
}
