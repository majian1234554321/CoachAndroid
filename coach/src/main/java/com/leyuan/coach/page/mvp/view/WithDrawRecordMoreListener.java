package com.leyuan.coach.page.mvp.view;

import com.leyuan.coach.bean.RecentEarningResult;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/29.
 */
public interface WithDrawRecordMoreListener {
    void onGetMoreWithDrawRecord(ArrayList<RecentEarningResult> withdrawDetails, int page);
}
