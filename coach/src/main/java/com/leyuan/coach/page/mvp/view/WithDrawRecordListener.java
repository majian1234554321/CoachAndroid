package com.leyuan.coach.page.mvp.view;

import com.leyuan.coach.bean.WithdrawDetail;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/29.
 */
public interface WithDrawRecordListener {
    void onGetWithDrawRecord(ArrayList<WithdrawDetail> withdrawDetails, int page);

}
