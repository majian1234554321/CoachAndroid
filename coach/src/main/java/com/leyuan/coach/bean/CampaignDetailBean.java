package com.leyuan.coach.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class CampaignDetailBean implements Parcelable {
    private String campaignId;
    private String areaName;
    private String startDate;
    private String startTime;
    private String endTime;
    private String camImg;
    private String title;
    private String place;
    private String brandName;
    private String coachLevel;
    private String allowPerson;
    private String alreadyPerson;
    private String price;
    private String signStartTime;
    private String signEndTime;
    private List<AppointUserBean> membersList;
    private String contents;
    private String status = "";
    private String subtitle;

    public String getSubtitle() {
        return subtitle;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCamImg() {
        return camImg;
    }

    public void setCamImg(String camImg) {
        this.camImg = camImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return contents;
    }

    public void setContent(String content) {
        this.contents = content;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCoachLevel() {
        return coachLevel;
    }

    public void setCoachLevel(String coachLevel) {
        this.coachLevel = coachLevel;
    }

    public String getAllowPerson() {
        return allowPerson;
    }

    public void setAllowPerson(String allowPerson) {
        this.allowPerson = allowPerson;
    }

    public String getAlreadyPerson() {
        return alreadyPerson;
    }

    public void setAlreadyPerson(String alreadyPerson) {
        this.alreadyPerson = alreadyPerson;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSignStartTime() {
        return signStartTime;
    }

    public void setSignStartTime(String signStartTime) {
        this.signStartTime = signStartTime;
    }

    public String getSignEndTime() {
        return signEndTime;
    }

    public void setSignEndTime(String signEndTime) {
        this.signEndTime = signEndTime;
    }

    public List<AppointUserBean> getMembersList() {
        return membersList;
    }

    public void setMembersList(List<AppointUserBean> membersList) {
        this.membersList = membersList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.campaignId);
        dest.writeString(this.areaName);
        dest.writeString(this.startDate);
        dest.writeString(this.endTime);
        dest.writeString(this.camImg);
        dest.writeString(this.title);
        dest.writeString(this.place);
        dest.writeString(this.brandName);
        dest.writeString(this.coachLevel);
        dest.writeString(this.allowPerson);
        dest.writeString(this.alreadyPerson);
        dest.writeString(this.price);
        dest.writeString(this.signStartTime);
        dest.writeString(this.signEndTime);
        dest.writeList(this.membersList);
        dest.writeString(this.status);
    }

    public CampaignDetailBean() {
    }

    protected CampaignDetailBean(Parcel in) {
        this.campaignId = in.readString();
        this.areaName = in.readString();
        this.startDate = in.readString();
        this.endTime = in.readString();
        this.camImg = in.readString();
        this.title = in.readString();
        this.place = in.readString();
        this.brandName = in.readString();
        this.coachLevel = in.readString();
        this.allowPerson = in.readString();
        this.alreadyPerson = in.readString();
        this.price = in.readString();
        this.signStartTime = in.readString();
        this.signEndTime = in.readString();
        this.membersList = new ArrayList<AppointUserBean>();
        in.readList(this.membersList, UserCoach.class.getClassLoader());
        this.status = in.readString();
    }

    public static final Creator<CampaignDetailBean> CREATOR = new Creator<CampaignDetailBean>() {
        @Override
        public CampaignDetailBean createFromParcel(Parcel source) {
            return new CampaignDetailBean(source);
        }

        @Override
        public CampaignDetailBean[] newArray(int size) {
            return new CampaignDetailBean[size];
        }
    };
}
