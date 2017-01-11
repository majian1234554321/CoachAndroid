package com.leyuan.coach.page.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leyuan.coach.R;

/**
 * Created by user on 2017/1/9.
 */
public class RecentEarningTitleViewHolder extends RecyclerView.ViewHolder {

    TextView txtTotalEarning;
    TextView txtMonth;
    TextView txtCourseEarning;
    TextView txtOtherEarning;

    public RecentEarningTitleViewHolder(View view) {
        super(view);
        txtTotalEarning = (TextView) view.findViewById(R.id.txt_total_earning);
        txtMonth = (TextView) view.findViewById(R.id.txt_month);
        txtCourseEarning = (TextView) view.findViewById(R.id.txt_course_earning);
        txtOtherEarning = (TextView) view.findViewById(R.id.txt_other_earning);
    }
}
