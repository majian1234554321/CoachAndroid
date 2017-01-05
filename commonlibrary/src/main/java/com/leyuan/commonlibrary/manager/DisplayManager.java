package com.leyuan.commonlibrary.manager;

import android.content.Context;

/**
 * Created by user on 2017/1/4.
 */

public class DisplayManager {

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int deviceWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int deviceHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }


}
