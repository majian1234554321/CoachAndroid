package com.leyuan.coach.page.mvp.presenter;

import android.content.Context;

import com.leyuan.coach.bean.CoachInfo;
import com.leyuan.coach.http.subscriber.BaseSubscriber;
import com.leyuan.coach.page.mvp.model.MineModel;
import com.leyuan.coach.page.mvp.view.MineViewListener;

/**
 * Created by user on 2016/12/20.
 */

public class MinePresenter {
    private Context context;
    private MineViewListener viewListener;
    private MineModel mineModel;

    public MinePresenter(Context context, MineViewListener viewListener) {
        this.context = context;
        this.viewListener = viewListener;
        mineModel = new MineModel();
    }

    public void getUserInfo(String id){
        mineModel.getUserInfo(new BaseSubscriber<CoachInfo>(context) {
            @Override
            public void onNext(CoachInfo userCoach) {

                viewListener.getUserInfo(userCoach);

            }
        },id);
    }
}
