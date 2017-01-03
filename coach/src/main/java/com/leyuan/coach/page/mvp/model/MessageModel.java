package com.leyuan.coach.page.mvp.model;

import com.leyuan.coach.bean.MessageInfo;
import com.leyuan.coach.http.RetrofitHelper;
import com.leyuan.coach.http.RxHelper;
import com.leyuan.coach.http.api.MessageService;
import com.leyuan.coach.page.App;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by user on 2017/1/3.
 */

public class MessageModel {

    private String id;
    private MessageService service;

    public MessageModel() {
        service = RetrofitHelper.createApi(MessageService.class);
        if (App.getInstance().isLogin()) {
            id = String.valueOf(App.getInstance().getUser().getId());
        }
    }

    public void getMsgList(Subscriber<ArrayList<MessageInfo>> subscriber) {
        service.getMsgList(id)
                .compose(RxHelper.<ArrayList<MessageInfo>>transform())
                .subscribe(subscriber);
    }
}
