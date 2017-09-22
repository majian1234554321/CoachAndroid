package com.leyuan.coach.bean;

/**
 * Created by user on 2017/8/23.
 */
public class PayOrderResult {
    private int code;
    private String message;
    private PayOrderBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PayOrderBean getResult() {
        return result;
    }

    public void setResult(PayOrderBean result) {
        this.result = result;
    }
}
