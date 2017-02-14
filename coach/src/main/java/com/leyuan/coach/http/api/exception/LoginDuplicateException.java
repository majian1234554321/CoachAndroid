package com.leyuan.coach.http.api.exception;

/**
 * Created by user on 2017/1/17.
 */
public class LoginDuplicateException extends Exception {
    public LoginDuplicateException(String message) {
        super(message);
    }
}
