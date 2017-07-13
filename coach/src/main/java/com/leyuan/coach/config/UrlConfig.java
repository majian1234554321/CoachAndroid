package com.leyuan.coach.config;

/**
 * Created by user on 2016/12/3.
 */

public class UrlConfig {

    public static boolean debug = true;

    private static String urlHtml;
    private static String urlHost;

    static {
        if (debug) {
            urlHtml = "http://opentest.aidong.me/";
//            urlHost = "http://192.168.0.208:8080/";
            urlHost = "http://m1.aidong.me/coach/";
        } else {
            urlHtml = "http://open.aidong.me/";
            urlHost = "http://coach.aidong.me/coach/";
        }
    }

    public static final String BASE_URL = urlHost;
    static final String BASE_URL_HTML = urlHtml;

}
