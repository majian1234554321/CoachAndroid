package com.leyuan.coach.page.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leyuan.coach.R;

/**
 * Created by user on 2017/1/9.
 */
public class RecentEarningCourseViewHolder extends RecyclerView.ViewHolder {

     TextView txtCourseEarningDetail;
     RecyclerView recyclerCourseEarning;

    public RecentEarningCourseViewHolder(View view) {
        super(view);
        txtCourseEarningDetail = (TextView) view.findViewById(R.id.txt_course_earning_detail);
        recyclerCourseEarning = (RecyclerView) view.findViewById(R.id.recyclerCourseEarning);
    }
}
