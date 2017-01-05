package com.leyuan.coach.bean;

/**
 * Created by user on 2017/1/4.
 */
public class VersionInfomation {

    String version;//: 版本
    int isUpdate;//: 是否强制更新（0否，1是）
    String apk;//：下载地址（安卓）

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getApk() {
        return apk;
    }

    public void setApk(String apk) {
        this.apk = apk;
    }
}
