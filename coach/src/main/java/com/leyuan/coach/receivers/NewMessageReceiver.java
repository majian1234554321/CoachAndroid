package com.leyuan.coach.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by user on 2017/2/15.
 */
public class NewMessageReceiver extends BroadcastReceiver {
    public static final String ACTION = "com.leyuan.coach.newMessage";
    private View imgNewMessage;

    public NewMessageReceiver(View imgNewMessage) {
        this.imgNewMessage = imgNewMessage;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        imgNewMessage.setVisibility(View.VISIBLE);
    }
}
