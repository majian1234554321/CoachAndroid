package com.leyuan.coach.page.mvp.view;


import com.leyuan.coach.bean.AppointmentDetailBean;

public interface AppointmentDetailViewListener {
    /**
     * 设置预约详情
     * @param appointmentDetailBean 预约详情实体
     */
    void setAppointmentDetail(AppointmentDetailBean appointmentDetailBean);
}
