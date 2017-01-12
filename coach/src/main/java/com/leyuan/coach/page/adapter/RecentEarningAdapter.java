package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.RecentEarningResult;


/**
 * Created by user on 2017/1/9.
 */
public class RecentEarningAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private RecentEarningResult recentEaringResult;
    private LayoutInflater inflater;

    public RecentEarningAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_TITLE.ordinal()) {
            View view = inflater.inflate(R.layout.item_recent_earning_title, parent, false);
            return new RecentEarningTitleViewHolder(view);
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_COURSE.ordinal()) {
            View view = inflater.inflate(R.layout.item_recent_earning_course, parent, false);
            return new RecentEarningCourseViewHolder(view);
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_OTHER.ordinal()) {
            View view = inflater.inflate(R.layout.item_recent_earning_other, parent, false);
            return new RecentEarningOtherViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (recentEaringResult == null)
            return;

        if (holder instanceof RecentEarningTitleViewHolder) {
            ((RecentEarningTitleViewHolder) holder).txtTotalEarning.setText(String.valueOf(recentEaringResult.getTotalIncome()));
            ((RecentEarningTitleViewHolder) holder).txtCourseEarning.setText(String.valueOf(recentEaringResult.getClassIncome()));
            ((RecentEarningTitleViewHolder) holder).txtMonth.setText(recentEaringResult.getMonths());
            ((RecentEarningTitleViewHolder) holder).txtOtherEarning.setText(String.valueOf(recentEaringResult.getWelfareIncome()));

        } else if (holder instanceof RecentEarningCourseViewHolder) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            CourseEarningAdapter adapter = new CourseEarningAdapter(context, recentEaringResult.getLists());
            ((RecentEarningCourseViewHolder) holder).recyclerCourseEarning.setLayoutManager(linearLayoutManager);
            ((RecentEarningCourseViewHolder) holder).recyclerCourseEarning.setAdapter(adapter);


        } else if (holder instanceof RecentEarningOtherViewHolder) {

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            OtherEarningDetailAdapter adapter = new OtherEarningDetailAdapter(context, recentEaringResult.getOtherList(),
                    recentEaringResult.getWelfareIncome());

            ((RecentEarningOtherViewHolder) holder).recyclerOtherEarning.setLayoutManager(linearLayoutManager);
            ((RecentEarningOtherViewHolder) holder).recyclerOtherEarning.setAdapter(adapter);
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

    public void refreshData(RecentEarningResult recentEaringResult) {
        this.recentEaringResult = recentEaringResult;
        notifyDataSetChanged();
    }

    enum ITEM_TYPE {
        ITEM_TYPE_TITLE, ITEM_TYPE_COURSE, ITEM_TYPE_OTHER
    }

}
