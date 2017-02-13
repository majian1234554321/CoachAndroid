package com.leyuan.coach.utils;

import android.app.Activity;
import android.content.Context;

import com.leyuan.commonlibrary.manager.AutoStartManager;

/**
 * Created by user on 2017/2/13.
 */

public class CheckAutoStartUtils {

    private static long checkInterval = 15 * 24 * 60 * 60 * 1000;

    public static boolean isNeedCheck(Context context) {
        long currentTime = System.currentTimeMillis();
        return currentTime - SharePrefUtils.getLastCheckAutoStartTime(context) > checkInterval;
    }

    public static void skipToAutoStartView(Activity context) {
        AutoStartManager.toAutoStartView(context);
        SharePrefUtils.putCheckAutoStartTime(context);
    }
}
