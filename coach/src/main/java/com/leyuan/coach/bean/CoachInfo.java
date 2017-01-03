package com.leyuan.coach.bean;

/**
 * Created by user on 2016/12/20.
 */

public class CoachInfo {
   int  nextCou;  //: 1,（是否存在下月课表，0否，1是）
   int  msgCou; //: 消息表示（0否，1是）
    String timeCard; //：考勤
    String rated; //：评价

    public int getNextCou() {
        return nextCou;
    }

    public void setNextCou(int nextCou) {
        this.nextCou = nextCou;
    }

    public int getMsgCou() {
        return msgCou;
    }

    public void setMsgCou(int msgCou) {
        this.msgCou = msgCou;
    }

    public String getTimeCard() {
        return timeCard;
    }

    public void setTimeCard(String timeCard) {
        this.timeCard = timeCard;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }
}
