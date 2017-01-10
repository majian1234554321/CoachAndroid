package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.RecentEaringResult;


/**
 * Created by user on 2017/1/9.
 */
public class RecentEarningAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private RecentEaringResult recentEaringResult;

    public RecentEarningAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_TITLE.ordinal()) {
            View view = View.inflate(context, R.layout.item_recent_earning_title, null);
            return new RecentEarningTitleViewHolder(view);
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_COURSE.ordinal()) {
            View view = View.inflate(context, R.layout.item_recent_earning_course, null);
            return new RecentEarningCourseViewHolder(view);
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_OTHER.ordinal()) {
            View view = View.inflate(context, R.layout.item_recent_earning_other, null);
            return new RecentEarningOtherViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof RecentEarningTitleViewHolder) {
            ((RecentEarningTitleViewHolder) holder).txtTotalEarning.setText(String.valueOf(recentEaringResult.getTotalIncome()));
            ((RecentEarningTitleViewHolder) holder).txtCourseEarning.setText(String.valueOf(recentEaringResult.getClassIncome()));
            ((RecentEarningTitleViewHolder) holder).txtMonth.setText(recentEaringResult.getMonths());
            ((RecentEarningTitleViewHolder) holder).txtOtherEarning.setText(String.valueOf(recentEaringResult.getWelfareIncome()));

        } else if (holder instanceof RecentEarningCourseViewHolder) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
            CourseEarningAdapter adapter = new CourseEarningAdapter(context,recentEaringResult.getLists());

        } else if (holder instanceof RecentEarningCourseViewHolder) {

        }

    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return ITEM_TYPE.ITEM_TYPE_TITLE.ordinal();
            case 1:
                return ITEM_TYPE.ITEM_TYPE_COURSE.ordinal();
            case 2:
                return ITEM_TYPE.ITEM_TYPE_OTHER.ordinal();
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void refreshData(RecentEaringResult recentEaringResult) {
        this.recentEaringResult = recentEaringResult;
    }

    enum ITEM_TYPE {
        ITEM_TYPE_TITLE, ITEM_TYPE_COURSE, ITEM_TYPE_OTHER
    }

}
