package com.leyuan.commonlibrary.util;

/**
 * Created by user on 2016/12/26.
 */

public class NumberUtils {

    public static String intToString(int number){
        if(number < 10){
            return "0"+number;
        }else{
            return String.valueOf(number);
        }
    }
}
