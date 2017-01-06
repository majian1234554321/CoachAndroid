package com.leyuan.coach.page.mvp.view;


import com.leyuan.coach.bean.AppointmentBean;
import com.leyuan.coach.bean.BaseBean;

import java.util.List;

public interface AppointmentViewListener {

    /**
     * 更新列表
     * @param appointmentBeanList
     */
    void updateRecyclerView(List<AppointmentBean> appointmentBeanList);

    /**
     * 显示空值界面
     */
    void showEmptyView();

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();

    void setUpdateOderStatus(BaseBean baseBean);
}
