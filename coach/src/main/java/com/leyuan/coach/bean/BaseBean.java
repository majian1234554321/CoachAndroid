package com.leyuan.coach.bean;


public class BaseBean<T> {

//    private int status;
    private int code;
    private String message;
//    private T data;
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return result;
    }
//
    public void setData(T data) {
        this.result = data;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
