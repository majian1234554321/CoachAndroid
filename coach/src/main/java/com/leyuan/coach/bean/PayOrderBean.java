package com.leyuan.coach.bean;

import com.google.gson.annotations.SerializedName;


public class PayOrderBean {
    private String orderId;
    private String payType;
    private String payAmount;
    private String status;
    private PayOptionBean payOption;


    /********订单详情************/
    private String coin;
    private String integral;
    private String coupon;
    private String created_at;

    public static class PayOptionBean {
        private String payString;

        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String _package;
        private String noncestr;
        private String timestamp;
        private String sign;


        public String getPayString() {
            return payString;
        }

        public void setPayString(String payString) {
            this.payString = payString;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String get_package() {
            return _package;
        }

        public void set_package(String _package) {
            this._package = _package;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        @Override
        public String toString() {
            return "PayOptionBean{" +
                    "payString='" + payString + '\'' +
                    ", appid='" + appid + '\'' +
                    ", partnerid='" + partnerid + '\'' +
                    ", prepayid='" + prepayid + '\'' +
                    ", _package='" + _package + '\'' +
                    ", noncestr='" + noncestr + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", sign='" + sign + '\'' +
                    '}';
        }
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public PayOptionBean getPayOption() {
        return payOption;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PayOptionBean getpayOption() {
        return payOption;
    }

    public void setPayOption(PayOptionBean payOption) {
        this.payOption = payOption;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}