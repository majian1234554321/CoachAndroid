package com.leyuan.coach.receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.leyuan.coach.bean.PushExtroInfo;
import com.leyuan.coach.config.Constant;
import com.leyuan.coach.config.ConstantString;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.HtmlFiveActivity;
import com.leyuan.coach.utils.LogUtil;
import com.leyuan.commonlibrary.manager.UiManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by user on 2016/12/15.
 */

public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        LogUtil.i(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            LogUtil.i(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            App.getInstance().setJPushID(regId);
            LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Constant.BROADCAST_NOTIFY_SAVE_JPUSHID));

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            String value = bundle.getString(JPushInterface.EXTRA_EXTRA);
            LogUtil.i(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " +
                    bundle.getString(JPushInterface.EXTRA_MESSAGE) + ", bike id = " + value);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            String value = bundle.getString(JPushInterface.EXTRA_EXTRA);
            LogUtil.i(TAG, "[MyReceiver] 接收到推送下来的通知  value = " + value);
            dispatchReceiverValue(context, value);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtil.i(TAG, "[MyReceiver] 用户点击打开了通知");
            for (Activity activity : App.mActivities) {
                LogUtil.i(TAG, " activity =  " + activity.getClass().getSimpleName());
            }
            String value = bundle.getString(JPushInterface.EXTRA_EXTRA);
            dispatchReceiverValueWhenClick(context, value);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            LogUtil.i(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogUtil.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            LogUtil.i(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    private void dispatchReceiverValueWhenClick(Context context, String value) {
//        if (!App.mActivities.isEmpty())
        if (App.getInstance().isForeground)
            return;

        PushExtroInfo info = new Gson().fromJson(value, PushExtroInfo.class);
        Bundle pushBundle = new Bundle();
        pushBundle.putString(ConstantString.PUSH_BACKUP, info.getBackup());
        pushBundle.putInt(ConstantString.PUSH_TYPE, info.getType());

        switch (info.getType()) {
            case PushExtroInfo.PushType.NEWS_MESSAGE:
            case PushExtroInfo.PushType.MEXT_MONTH_UNCONFIRMED:
//                if (App.getInstance().isLogin()) {
//                    UiManager.activityJump(context, pushBundle, MainActivity.class,
//                            Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                } else {
//                    UiManager.activityJump(context, pushBundle, LoginActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//                break;
            case PushExtroInfo.PushType.CURRENT_TAKE_OVER_COURSE:
            case PushExtroInfo.PushType.NOTIFY_SUSPEND_COURSE:
            case PushExtroInfo.PushType.NEWLY_INCREASE_COURSE:
                UiManager.activityJump(context, pushBundle, HtmlFiveActivity.class,
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                break;
        }
    }

    private void dispatchReceiverValue(Context context, String value) {
        PushExtroInfo info = new Gson().fromJson(value, PushExtroInfo.class);
        Bundle pushBundle = new Bundle();
        pushBundle.putString(ConstantString.PUSH_BACKUP, info.getBackup());
        pushBundle.putInt(ConstantString.PUSH_TYPE, info.getType());

        switch (info.getType()) {
            case PushExtroInfo.PushType.NEWS_MESSAGE:
//                context.sendBroadcast(new Intent(NewMessageReceiver.ACTION));
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Constant.BROADCAST_NOTIFY_NEWS_NESSAGE));

                break;
            case PushExtroInfo.PushType.CURRENT_TAKE_OVER_COURSE:
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Constant.BROADCAST_NOTIFY_CURRENT_TAKE_OVER_COURSE));

                break;
            case PushExtroInfo.PushType.NOTIFY_SUSPEND_COURSE:
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Constant.BROADCAST_NOTIFY_SUSPEND_COURSE));
                break;
            case PushExtroInfo.PushType.NEWLY_INCREASE_COURSE:
//                if (!App.mActivities.isEmpty())
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Constant.BROADCAST_NOTIFY_NEWLY_INCREASE_COURSE));

//                if (App.getInstance().isForeground){
//                    Intent intent = new Intent("");
//                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//
//                }
////                    UiManager.activityJump(context, pushBundle, MainActivity.class,
//                            Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                break;
            case PushExtroInfo.PushType.MEXT_MONTH_UNCONFIRMED:
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Constant.BROADCAST_NOTIFY_NEXT_MONTH_COURSE));
                break;
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

}
