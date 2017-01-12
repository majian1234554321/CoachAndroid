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
        getCalendar(subscriber, id, "0");
    }

    public void getNextMonthCalendar(Subscriber<ArrayList<MyCalendar>> subscriber) {
        getCalendar(subscriber, id, "1");
    }

    public void getCalendar(Subscriber<ArrayList<MyCalendar>> subscriber, String id, String type) {
        classService.getCalendar(id, type)
                .compose(RxHelper.<ArrayList<MyCalendar>>transform())
                .subscribe(subscriber);
    }

    public void getCourseList(Subscriber<CourseResult> subscriber, String courseTime) {
        getCourseList(subscriber, id, courseTime);
    }

    public void getCourseList(Subscriber<CourseResult> subscriber, String id, String courseTime) {
        classService.getCourseList(id, courseTime)
                .compose(RxHelper.<CourseResult>transform())
                .subscribe(subscriber);
    }

    public void getSuspendCourseList(Subscriber<ArrayList<ClassSchedule>> subscriber) {
        classService.getSuspendCourseList(id)
                .compose(RxHelper.<ArrayList<ClassSchedule>>transform())
                .subscribe(subscriber);
    }

    public void confirmSuspendClass(Subscriber<Object> subscriber, String timetableId) {
        classService.suspendClassConfirm(id, timetableId)
                .compose(RxHelper.transform())
                .subscribe(subscriber);
    }

    public void getReplaceCourseList(Subscriber<ArrayList<ClassSchedule>> subscriber) {
        getReplaceCourseList(subscriber, id);
    }

    public void getReplaceCourseList(Subscriber<ArrayList<ClassSchedule>> subscriber, String id) {
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

        classService.takeOverClassConfirm(id, timetableId, status)
                .compose(transform())
                .subscribe(subscriber);
    }

    public void signIn(Subscriber<Object> subscriber, String timetableId) {
        signIn(subscriber, id, timetableId, String.valueOf(App.lat), String.valueOf(App.lon));
    }

    public void signIn(Subscriber<Object> subscriber, String coachId, String timetableId, String lat, String lng) {

        classService.signIn(coachId, timetableId, lat, lng)
                .compose(transform())
                .subscribe(subscriber);
    }

    public void getNextMonthUnconfirmCourseList(Subscriber<CourseResult> subscriber, String courseTime) {
        getNextMonthCourseList(subscriber, id, courseTime, "0");
    }

    public void getNextMonthConfirmedCourseList(Subscriber<CourseResult> subscriber, String courseTime) {
        getNextMonthCourseList(subscriber, id, courseTime, "1");
    }

    public void getNextMonthCourseList(Subscriber<CourseResult> subscriber, String id, String courseTime, String type) {
        classService.getNextMonthCourseList(id, courseTime, type)
                .compose(RxHelper.<CourseResult>transform())
                .subscribe(subscriber);
    }

    public void nextMonthCourseConfirm(Subscriber<Object> subscriber, String courseTime) {
        nextMonthCourseConfirm(subscriber, id, courseTime);
    }

    public void nextMonthCourseConfirm(Subscriber<Object> subscriber, String id, String courseTime) {
        classService.nextMonthCourseConfirm(id, courseTime)
                .compose(transform())
                .subscribe(subscriber);
    }

}
