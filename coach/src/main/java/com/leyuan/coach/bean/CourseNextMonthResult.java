package com.leyuan.coach.bean;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/23.
 */
public class CourseNextMonthResult {

    int tdb;//待确认数量
    int confirm;//:已确认数量
    String courseDate;//: "2016-12-18",（时间）
    String courseTime;//: "2016年12月",（日期）
    int minute;//: 分钟
    ArrayList<ClassSchedule> coachList;


    public ArrayList<ClassSchedule> getCoachList() {
        return coachList;
    }

    public void setCoachList(ArrayList<ClassSchedule> coachList) {
        this.coachList = coachList;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public int getConfirm() {
        return confirm;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }

    public int getTdb() {
        return tdb;
    }

    public void setTdb(int tdb) {
        this.tdb = tdb;
    }
}
