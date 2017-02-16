package com.leyuan.coach.utils;

import android.util.Log;

import com.leyuan.coach.config.UrlConfig;


/**
 * Log
 */
public class LogUtil {

    private LogUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static final String TAG = "com.leyuan.coach";

    public static void i(String msg) {
        if (UrlConfig.debug)
            Log.i(TAG, msg);
    }


    public static void w(String msg) {
        if (UrlConfig.debug)
            Log.w(TAG, msg);
    }

    public static void d(String msg) {
        if (UrlConfig.debug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (UrlConfig.debug)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (UrlConfig.debug)
            Log.v(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (UrlConfig.debug)
            Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (UrlConfig.debug)
            Log.w(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (UrlConfig.debug)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (UrlConfig.debug)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (UrlConfig.debug)
            Log.i(tag, msg);
    }
}