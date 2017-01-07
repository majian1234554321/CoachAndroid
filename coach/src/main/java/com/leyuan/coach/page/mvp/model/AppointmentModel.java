package com.leyuan.coach.page.mvp.model;


import com.leyuan.coach.bean.AppointmentBean;
import com.leyuan.coach.bean.AppointmentDetailBean;
import com.leyuan.coach.bean.BaseBean;
import com.leyuan.coach.http.RetrofitHelper;
import com.leyuan.coach.http.RxHelper;
import com.leyuan.coach.http.api.AppointmentService;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class AppointmentModel {
    private AppointmentService appointmentService;

    public AppointmentModel() {
        appointmentService = RetrofitHelper.createApi(AppointmentService.class);
    }


    public void getAppointments(Subscriber<List<AppointmentBean>> subscriber,String coachId,String type,int page) {
        appointmentService.getAppointments(coachId,type,page)
                .compose(RxHelper.<List<AppointmentBean>>transform())
                .subscribe(subscriber);
    }


    public void getAppointmentDetail(Subscriber<AppointmentDetailBean> subscriber, String orderId) {
        appointmentService.getAppointmentDetail(orderId)
                .compose(RxHelper.<AppointmentDetailBean>transform())
                .subscribe(subscriber);
    }

    public void updateAppointmentStatus(Subscriber<BaseBean> subscriber, String orderId, String type){
        appointmentService.updateAppointmentStatus(orderId,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
