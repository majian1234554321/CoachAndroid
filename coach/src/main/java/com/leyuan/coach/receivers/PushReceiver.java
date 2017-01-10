package com.leyuan.coach.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.stetho.common.LogUtil;
import com.google.gson.Gson;
import com.leyuan.coach.bean.PushExtroInfo;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.MainActivity;
import com.leyuan.coach.page.activity.course.NextMonthClassScheduleActivity;
import com.leyuan.coach.page.activity.mine.MessageDetailActivity;
import com.leyuan.commonlibrary.manager.UiManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by user on 2016/12/15.
 */

public class PushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        com.leyuan.coach.utils.LogUtil.i(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            //send the Registration Id to your server...
            com.leyuan.coach.utils.LogUtil.i(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            App.getInstance().setJPushID(regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

            String value = bundle.getString(JPushInterface.EXTRA_EXTRA);

            com.leyuan.coach.utils.LogUtil.i(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE) + ", bike id = " + value);


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {

            String value = bundle.getString(JPushInterface.EXTRA_EXTRA);

            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            com.leyuan.coach.utils.LogUtil.i(TAG, "[MyReceiver] 接收到推送下来的通知 notifactionId: " + notifactionId + ",extra = " + value);

            PushExtroInfo info = new Gson().fromJson(value, PushExtroInfo.class);
            switch (info.getType()) {
                case 1:
                    UiManager.activityJump(context, bundle, MessageDetailActivity.class,
                            Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    break;
                case 2:
                    UiManager.activityJump(context, bundle, MainActivity.class,
                            Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    break;
                case 3:
                    UiManager.activityJump(context, bundle, MainActivity.class,
                            Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    break;

                case 4:
                    UiManager.activityJump(context, bundle, NextMonthClassScheduleActivity.class,
                            Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    break;
            }


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            com.leyuan.coach.utils.LogUtil.i(TAG, "[MyReceiver] 用户点击打开了通知");


        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            com.leyuan.coach.utils.LogUtil.i(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogUtil.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            com.leyuan.coach.utils.LogUtil.i(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    LogUtil.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtil.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //    //send msg to MainActivity
    //    private void processCustomMessage(Context context, Bundle bundle) {
    //        if (MainActivity.isForeground) {
    //            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
    //            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
    //            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
    //            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
    //            if (!ExampleUtil.isEmpty(extras)) {
    //                try {
    //                    JSONObject extraJson = new JSONObject(extras);
    //                    if (null != extraJson && extraJson.length() > 0) {
    //                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
    //                    }
    //                } catch (JSONException e) {
    //
    //                }
    //
    //            }
    //            context.sendBroadcast(msgIntent);
    //        }
    //    }
}
