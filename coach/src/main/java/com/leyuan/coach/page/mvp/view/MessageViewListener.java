package com.leyuan.coach.page.mvp.view;

import com.leyuan.coach.bean.MessageInfo;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/3.
 */
public interface MessageViewListener {
    void onGetMsgListResult(ArrayList<MessageInfo> messageInfos);
}
