package com.leyuan.coach.bean;

public class CampaignBean {
    private String campaignId;
    private String startDate;
    private String camImg;
    private String startTime;
    private String areaName;


    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaign_id) {
        this.campaignId = campaign_id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCamImg() {
        return camImg;
    }

    public void setCamImg(String camImg) {
        this.camImg = camImg;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return "CampaignBean{" +
                "campaignId='" + campaignId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", camImg='" + camImg + '\'' +
                ", startTime='" + startTime + '\'' +
                ", areaName='" + areaName + '\'' +
                '}';
    }
}
