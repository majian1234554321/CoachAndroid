package com.leyuan.coach.bean;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/9.
 */
public class RecentEaringResult {
    double totalIncome; //: 100 总收益
    double welfareIncome; //: 福利收益
    double classIncome; //课时收益
    String months; //: "2016-12"年月,
    ArrayList<CourseEarning> lists;
    ArrayList<OtherEarning> otherList;

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getWelfareIncome() {
        return welfareIncome;
    }

    public void setWelfareIncome(double welfareIncome) {
        this.welfareIncome = welfareIncome;
    }

    public double getClassIncome() {
        return classIncome;
    }

    public void setClassIncome(double classIncome) {
        this.classIncome = classIncome;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public ArrayList<CourseEarning> getLists() {
        return lists;
    }

    public void setLists(ArrayList<CourseEarning> lists) {
        this.lists = lists;
    }

    public ArrayList<OtherEarning> getOtherList() {
        return otherList;
    }

    public void setOtherList(ArrayList<OtherEarning> otherList) {
        this.otherList = otherList;
    }

    public class CourseEarning {
        ArrayList<StroreDetail> typeList;
        double typeAmount; //500
        String typeName; //团体课

        public ArrayList<StroreDetail> getTypeList() {
            return typeList;
        }

        public double getTypeAmount() {
            return typeAmount;
        }

        public String getTypeName() {
            return typeName;
        }
    }

    public class StroreDetail {
        String storeName; //: "威尔士日光店",
        String title; //: "105/节*8"

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public class OtherEarning {
        String amount; //: "100",
        String title; //: "签到成功"

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
