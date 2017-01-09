package com.leyuan.coach.page.mvp.view;


import com.leyuan.coach.bean.AppointmentDetailBean;
import com.leyuan.coach.bean.PayOrderBean;

public interface AppointmentDetailViewListener {

    void setAppointmentDetail(AppointmentDetailBean appointmentDetailBean);

    void setChangePayType(PayOrderBean payOrderBean);
}
