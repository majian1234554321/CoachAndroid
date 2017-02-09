package com.leyuan.coach.bean;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/23.
 */
public class CourseResult {

    String dateTime;// "2016年12月",（月份）
    int courseSize;// 6（当前月课程数量）
    int normalCou;//正常课程数量
    int abnormalCou; //异常课程数量

    String tdb;//待确认数量
    String confirm;//:已确认数量
    String courseDate;//: "2016-12-18",（时间）
    String courseTime;//: "2016年12月",（日期）
    int minute;//: 分钟

    ArrayList<ClassSchedule> coachList;

    public String getTdb() {
        return tdb;
    }

    public void setTdb(String tdb) {
        this.tdb = tdb;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public ArrayList<ClassSchedule> getCoachList() {
        return coachList;
    }

    public void setCoachList(ArrayList<ClassSchedule> coachList) {
        this.coachList = coachList;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getCourseSize() {
        return courseSize;
    }

    public void setCourseSize(int courseSize) {
        this.courseSize = courseSize;
    }

    public int getNormalCou() {
        return normalCou;
    }

    public void setNormalCou(int normalCou) {
        this.normalCou = normalCou;
    }

    public int getAbnormalCou() {
        return abnormalCou;
    }

    public void setAbnormalCou(int abnormalCou) {
        this.abnormalCou = abnormalCou;
    }
}
