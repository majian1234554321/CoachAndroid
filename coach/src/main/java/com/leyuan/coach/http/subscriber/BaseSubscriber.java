package com.leyuan.coach.http.subscriber;

import android.content.Context;
import android.widget.Toast;

import com.leyuan.coach.R;
import com.leyuan.coach.http.api.exception.LoginDuplicateException;
import com.leyuan.coach.page.App;
import com.leyuan.coach.page.activity.mine.LoginNoDismissActivity;
import com.leyuan.commonlibrary.manager.UiManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;


public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private Context context;

    public BaseSubscriber() {
    }

    public BaseSubscriber(Context context) {
        this.context = context;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, R.string.connect_timeout, Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, R.string.connect_break, Toast.LENGTH_SHORT).show();
        } else if (e instanceof LoginDuplicateException) {
            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            App.getInstance().exitLogin();
            UiManager.activityJump(context, LoginNoDismissActivity.class);
        } else {
            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
