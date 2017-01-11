package com.leyuan.coach.bean;

public class AppointmentDetailBean {
    private Campaign campaign;
    private PayOrderBean payOption;

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public PayOrderBean getPayOption() {
        return payOption;
    }

    public void setPayOption(PayOrderBean payOption) {
        this.payOption = payOption;
    }

    public class Campaign {
        private String title;
        private String campImg;
        private String campaignId;
        private String brandName;
        private String address;
        private String userName;
        private String phone;
        private String orderId;
        private String startDate;
        private String startTime;
        private String endTime;
        private String payType;
        private String payAmount;
        private String status;
        private String littleTime;
        private String tickNo;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCampImg() {
            return campImg;
        }

        public void setCampImg(String campImg) {
            this.campImg = campImg;
        }

        public String getCampaignId() {
            return campaignId;
        }

        public void setCampaignId(String campaignId) {
            this.campaignId = campaignId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLittleTime() {
            return littleTime;
        }

        public void setLittleTime(String littleTime) {
            this.littleTime = littleTime;
        }

        public String getTickNo() {
            return tickNo;
        }

        public void setTickNo(String tickNo) {
            this.tickNo = tickNo;
        }
    }
}
