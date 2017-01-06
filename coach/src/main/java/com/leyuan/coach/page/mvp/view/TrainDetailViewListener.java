package com.leyuan.coach.page.mvp.view;

import com.leyuan.coach.bean.CampaignDetailBean;

public interface TrainDetailViewListener {

    /**
     * 获取活动详情
     * @param campaignDetailBean CampaignDetailBean
     */
    void setCampaignDetail(CampaignDetailBean campaignDetailBean);



    /**
     * 分享此活动
     */
    void shareCampaign();

    /**
     * 报名参加此活动
     */
    void applyCampaign();
}
