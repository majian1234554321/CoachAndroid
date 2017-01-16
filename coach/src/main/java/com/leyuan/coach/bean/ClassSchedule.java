package com.leyuan.coach.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2016/12/23.
 */

public class ClassSchedule implements Parcelable {
    int timetableId; // ID
    String courseName; //  课程名称
    String storeName; //  门店名称
    String beginTime; //  开始时间
    String endTime; // 结束时间
    String address; //  地址
    String classRoom; //  教室名称
    String city; // 城市
    String area; // 区
    String courseTime;//日期
    String signTime; //: 签到时间（2016-12-12 12:20）
    double lng; // 经度
    double lat; // 维度
    int signStatus;//签到类型(4：未签到，5：已签到，6：迟到，7：旷课)
    int courseType;//:课程类型(1:正常课程；2：换课，3代课，4停课)

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    protected ClassSchedule(Parcel in) {
        timetableId = in.readInt();
        courseName = in.readString();
        storeName = in.readString();
        beginTime = in.readString();
        endTime = in.readString();
        address = in.readString();
        classRoom = in.readString();
        city = in.readString();
        area = in.readString();
        courseTime = in.readString();
        signTime = in.readString();
        lng = in.readDouble();
        lat = in.readDouble();
        signStatus = in.readInt();
        courseType = in.readInt();
    }

    public static final Creator<ClassSchedule> CREATOR = new Creator<ClassSchedule>() {
        @Override
        public ClassSchedule createFromParcel(Parcel in) {
            return new ClassSchedule(in);
        }

        @Override
        public ClassSchedule[] newArray(int size) {
            return new ClassSchedule[size];
        }
    };

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(int timetableId) {
        this.timetableId = timetableId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public int getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(int signStatus) {
        this.signStatus = signStatus;
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(timetableId);
        dest.writeString(courseName);
        dest.writeString(storeName);
        dest.writeString(beginTime);
        dest.writeString(endTime);
        dest.writeString(address);
        dest.writeString(classRoom);
        dest.writeString(city);
        dest.writeString(area);
        dest.writeString(courseTime);
        dest.writeString(signTime);
        dest.writeDouble(lng);
        dest.writeDouble(lat);
        dest.writeInt(signStatus);
        dest.writeInt(courseType);
    }

    public static class SignStatus {
        public static final int UNSING = 4;
        public static final int SINGED = 5;
        public static final int BE_LATE = 6;
        public static final int TRUANT = 7;
    }

    public static class CourseStatus {
        public static final int NORMAL = 1;
        public static final int CHANGE = 2;
        public static final int TAKE_OVER = 3;
        public static final int SUSPEND = 4;
    }
}
