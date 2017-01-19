package com.leyuan.coach.bean;

/**
 * Created by user on 2017/1/4.
 */
public class PushExtroInfo {

    int type; //(1是最新消息，2是当月代课通知，3是下月待确认课表,4 停课通知)
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

    public static class PushType {
        public static final int NEWS_MESSAGE = 1;
        public static final int CURRENT_TAKE_OVER_COURSE = 2;
        public static final int MEXT_MONTH_UNCONFIRMED = 3;
        public static final int NOTIFY_SUSPEND_COURSE = 4;

    }

}
