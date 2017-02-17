package com.leyuan.coach.page.mvp.model;

import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.bean.CourseResult;
import com.leyuan.coach.bean.MyCalendar;
import com.leyuan.coach.http.RetrofitHelper;
import com.leyuan.coach.http.RxHelper;
import com.leyuan.coach.http.api.ClassService;
import com.leyuan.coach.page.App;

import java.util.ArrayList;

import rx.Subscriber;

import static com.leyuan.coach.http.RxHelper.transform;

/**
 * Created by user on 2016/12/23.
 */

public class CourseModel {
    private ClassService classService;
    private String id;

    public CourseModel() {
        classService = RetrofitHelper.createApi(ClassService.class);
        if (App.getInstance().isLogin()) {
            id = String.valueOf(App.getInstance().getUser().getId());
        }
    }

    public void getCurrentCalendar(Subscriber<ArrayList<MyCalendar>> subscriber) {
        getCalendar(subscriber, id, "0", "0");
    }

    public void getNextMonthUnconfirmCalendar(Subscriber<ArrayList<MyCalendar>> subscriber) {
        getCalendar(subscriber, id, "1", "2");
    }

    public void getNextMonthConfrimedCalendar(Subscriber<ArrayList<MyCalendar>> subscriber) {
        getCalendar(subscriber, id, "1", "3");
    }

    public void getNextMonthCalendar(Subscriber<ArrayList<MyCalendar>> subscriber, String status) {
        getCalendar(subscriber, id, "1", status);
    }


    public void getCalendar(Subscriber<ArrayList<MyCalendar>> subscriber, String id, String type, String status) {
        if (id != null)
            classService.getCalendar(id, type, status)
                    .compose(RxHelper.<ArrayList<MyCalendar>>transform())
                    .subscribe(subscriber);
    }

    public void getCourseList(Subscriber<CourseResult> subscriber, String courseTime) {
        getCourseList(subscriber, id, courseTime);
    }

    public void getCourseList(Subscriber<CourseResult> subscriber, String id, String courseTime) {
        if (id != null)
            classService.getCourseList(id, courseTime)
                    .compose(RxHelper.<CourseResult>transform())
                    .subscribe(subscriber);
    }

    public void getSuspendCourseList(Subscriber<ArrayList<ClassSchedule>> subscriber) {
        if (id != null)
            classService.getSuspendCourseList(id)
                    .compose(RxHelper.<ArrayList<ClassSchedule>>transform())
                    .subscribe(subscriber);
    }

    public void confirmSuspendClass(Subscriber<Object> subscriber, String timetableId) {
        if (id != null)
            classService.suspendClassConfirm(id, timetableId)
                    .compose(RxHelper.transform())
                    .subscribe(subscriber);
    }

    public void getReplaceCourseList(Subscriber<ArrayList<ClassSchedule>> subscriber) {
        getReplaceCourseList(subscriber, id);
    }

    public void getReplaceCourseList(Subscriber<ArrayList<ClassSchedule>> subscriber, String id) {
        if (id != null)
            classService.getReplaceCourseList(id)
                    .compose(RxHelper.<ArrayList<ClassSchedule>>transform())
                    .subscribe(subscriber);
    }

    public void takeOverClassAgree(Subscriber<Object> subscriber, String timetableId) {
        takeOverClassConfirm(subscriber, id, timetableId, "1");
    }

    public void takeOverClassRefuse(Subscriber<Object> subscriber, String timetableId) {
        takeOverClassConfirm(subscriber, id, timetableId, "2");
    }

    public void takeOverClassConfirm(Subscriber<Object> subscriber, String timetableId, String status) {
        takeOverClassConfirm(subscriber, id, timetableId, status);
    }


    public void takeOverClassConfirm(Subscriber<Object> subscriber, String id, String timetableId, String status) {
        if (id != null)
            classService.takeOverClassConfirm(id, timetableId, status)
                    .compose(transform())
                    .subscribe(subscriber);
    }

    public void signIn(Subscriber<Object> subscriber, String timetableId) {
        signIn(subscriber, id, timetableId, String.valueOf(App.lat), String.valueOf(App.lon));
    }

    public void signIn(Subscriber<Object> subscriber, String coachId, String timetableId, String lat, String lng) {
        if (id != null)
            classService.signIn(coachId, timetableId, lat, lng)
                    .compose(transform())
                    .subscribe(subscriber);
    }

    public void getNextMonthUnconfirmCourseList(Subscriber<CourseResult> subscriber, String courseTime) {
        getNextMonthCourseList(subscriber, id, courseTime, "2");
    }

    public void getNextMonthConfirmedCourseList(Subscriber<CourseResult> subscriber, String courseTime) {
        getNextMonthCourseList(subscriber, id, courseTime, "3");
    }

    public void getNextMonthCourseList(Subscriber<CourseResult> subscriber, String id, String courseTime, String type) {
        if (id != null)
            classService.getNextMonthCourseList(id, courseTime, type)
                    .compose(RxHelper.<CourseResult>transform())
                    .subscribe(subscriber);
    }

    public void nextMonthCourseConfirm(Subscriber<Object> subscriber, String courseTime) {
        nextMonthCourseConfirm(subscriber, id, courseTime);
    }

    public void nextMonthCourseConfirm(Subscriber<Object> subscriber, String id, String courseTime) {
        if (id != null)
            classService.nextMonthCourseConfirm(id, courseTime)
                    .compose(transform())
                    .subscribe(subscriber);
    }

    public void refreshUserId() {
        if (App.getInstance().isLogin()) {
            id = String.valueOf(App.getInstance().getUser().getId());
        }
    }
}
