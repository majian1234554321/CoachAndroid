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
    ArrayList<ClassSchedule> coachList;

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
