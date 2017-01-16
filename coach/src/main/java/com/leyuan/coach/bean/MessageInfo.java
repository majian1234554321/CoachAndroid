package com.leyuan.coach.bean;

/**
 * Created by user on 2017/1/3.
 */
public class MessageInfo {

    int id; //: 消息ID
    String msgContent; //: 消息内容
    String msgDate; //:: 消息日期
    String msgTime; //:：消息时间
    int msgStatus; //:: 消息状态(0未读，1已读)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(String msgDate) {
        this.msgDate = msgDate;
    }

    public int getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(int msgStatus) {
        this.msgStatus = msgStatus;
    }

    public static class MsgStatus {
        public static final int UNREAD = 0;
        public static final int READED = 1;
    }
}
