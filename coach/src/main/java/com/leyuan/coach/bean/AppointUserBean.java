package com.leyuan.coach.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class AppointUserBean implements Parcelable {

    private String id;          //编号
    private String name;        //名字
    private String avatar;      //头像
    private String gender;      //性别
    private String distance;    //距离
    private boolean isFollow;   //是否关注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeString(this.gender);
        dest.writeString(this.distance);
        dest.writeByte(this.isFollow ? (byte) 1 : (byte) 0);
    }

    public AppointUserBean() {
    }

    protected AppointUserBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.avatar = in.readString();
        this.gender = in.readString();
        this.distance = in.readString();
        this.isFollow = in.readByte() != 0;
    }

    public static final Parcelable.Creator<AppointUserBean> CREATOR = new Parcelable.Creator<AppointUserBean>() {
        @Override
        public AppointUserBean createFromParcel(Parcel source) {
            return new AppointUserBean(source);
        }

        @Override
        public AppointUserBean[] newArray(int size) {
            return new AppointUserBean[size];
        }
    };
}
