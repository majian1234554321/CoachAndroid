package com.leyuan.coach.page.mvp.view;

import com.leyuan.coach.bean.CampaignBean;

import java.util.List;

public interface TrainChildViewListener {

    /**
     * 更新列表
     * @param campaignBean CampaignBean
     */
    void updateRecyclerView(List<CampaignBean> campaignBean);

    /**
     * 显示FooterView，提示没有任何内容了
     */
    void showEndFooterView();

    void showEmptyView();
}
