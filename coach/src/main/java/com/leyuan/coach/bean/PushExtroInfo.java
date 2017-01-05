package com.leyuan.coach.bean;

/**
 * Created by user on 2017/1/4.
 */
public class PushExtroInfo {

    int type; //(1是最新消息，2是当月代课通知，3是下月待确认课表)
    String backup;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }
}
