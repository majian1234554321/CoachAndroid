package com.leyuan.coach.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2016/12/23.
 */
public class MyCalendar implements Parcelable{

    @SerializedName("month")
    String timeMouth;

//    @SerializedName("dayList")
//    ArrayList<Integer> dayList;

    @SerializedName("dayList")
    int[] dayList;


    protected MyCalendar(Parcel in) {
        timeMouth = in.readString();
        dayList = in.createIntArray();
    }

    public static final Creator<MyCalendar> CREATOR = new Creator<MyCalendar>() {
        @Override
        public MyCalendar createFromParcel(Parcel in) {
            return new MyCalendar(in);
        }

        @Override
        public MyCalendar[] newArray(int size) {
            return new MyCalendar[size];
        }
    };

    public int[] getDayList() {
        return dayList;
    }

    public void setDayList(int[] dayList) {
        this.dayList = dayList;
    }



    public String getTimeMouth() {
        return timeMouth;
    }

    public void setTimeMouth(String timeMouth) {
        this.timeMouth = timeMouth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(timeMouth);
        dest.writeIntArray(dayList);
    }
}
