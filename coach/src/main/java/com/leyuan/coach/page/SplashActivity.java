package com.leyuan.coach.page;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.leyuan.coach.R;
import com.leyuan.coach.page.activity.account.LoginActivity;
import com.leyuan.commonlibrary.manager.UiManager;


public class SplashActivity extends BaseActivity {
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    UiManager.activityJump(SplashActivity.this, LoginActivity.class);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        mHandler.sendEmptyMessageDelayed(1, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
