package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.bean.MessageInfo;
import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.mvp.model.MessageModel;
import com.leyuan.coach.page.mvp.view.MessageViewListener;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/3.
 */

public class MessagePresenter {

    MessageModel model;
    MessageViewListener listener;
    Context context;

    public MessagePresenter(MessageViewListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        model = new MessageModel();
    }

    public void getMsgList() {
        model.getMsgList(new BaseSubscriber<ArrayList<MessageInfo>>(context) {
            @Override
            public void onNext(ArrayList<MessageInfo> messageInfos) {
                listener.onGetMsgListResult(messageInfos);
            }
        });
    }

    public void updateMsgStatus(String msgId) {
        model.updateMsgStatus(new BaseSubscriber<Object>(context) {
            @Override
            public void onNext(Object o) {

            }
        }, msgId);

    }

}
