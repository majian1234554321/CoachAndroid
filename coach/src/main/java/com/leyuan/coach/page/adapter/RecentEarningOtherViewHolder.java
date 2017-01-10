package com.leyuan.coach.page.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leyuan.coach.R;

/**
 * Created by user on 2017/1/9.
 */
public class RecentEarningOtherViewHolder extends RecyclerView.ViewHolder {

     TextView txtOtherEarningDetail;
     RecyclerView recyclerOtherEarning;

    public RecentEarningOtherViewHolder(View view) {
        super(view);
        txtOtherEarningDetail = (TextView) view.findViewById(R.id.txt_other_earning_detail);
        recyclerOtherEarning = (RecyclerView) view.findViewById(R.id.recyclerOtherEarning);
    }
}
