package com.leyuan.coach.bean;

/**
 * Created by user on 2016/12/28.
 */
public class WithdrawDetail {

    String widthdrawMonth;//提现月份
    String withdrawDate;//提现时间
    String statusName;//提现状态名称
    double cash;//金额

    public String getWidthdrawMonth() {
        return widthdrawMonth;
    }

    public void setWidthdrawMonth(String widthdrawMonth) {
        this.widthdrawMonth = widthdrawMonth;
    }

    public String getWithdrawDate() {
        return withdrawDate;
    }

    public void setWithdrawDate(String withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }
}
