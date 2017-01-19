package com.leyuan.commonlibrary.util;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;

public class TimeCountUtil extends CountDownTimer {
    private Button btn;
    private static long lastClickTime;

    public TimeCountUtil(long millisInFuture, long countDownInterval, Button btn) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
    }


    @SuppressLint("NewApi")
    @Override
    public void onTick(long millisUntilFinished) {
        btn.setClickable(false);//设置不能点击
        btn.setText(millisUntilFinished / 1000 + "s再次获取");//设置倒计时时间
        btn.setBackgroundColor(Color.parseColor("#999999"));
    }


    @SuppressLint("NewApi")
    @Override
    public void onFinish() {
        btn.setText("重新获取");
        btn.setClickable(true);//重新获得点击
        btn.setBackgroundColor(Color.parseColor("#FF5000"));

    }


    //    防止按钮多点
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


}