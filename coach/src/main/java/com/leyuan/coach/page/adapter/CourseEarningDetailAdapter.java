package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.RecentEarningResult;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/10.
 */
public class CourseEarningDetailAdapter extends RecyclerView.Adapter<CourseEarningDetailAdapter.Viewholder> {

    private Context context;
    private RecentEarningResult.CourseEarning courseEarning;
    private ArrayList<RecentEarningResult.StroreDetail> array;

    public CourseEarningDetailAdapter(Context context, RecentEarningResult.CourseEarning courseEarning) {
        this.context = context;
        this.courseEarning = courseEarning;
        array = courseEarning.getTypeList();

    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course_earning_detail, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        if (position == 0) {
            holder.txtRight.setVisibility(View.GONE);
            holder.txtLeft.setTextColor(context.getResources().getColor(R.color.black));
            holder.txtLeft.setText(courseEarning.getTypeName());
        } else if (position == array.size() + 1) {
            holder.txtRight.setVisibility(View.VISIBLE);
            holder.txtLeft.setTextColor(context.getResources().getColor(R.color.red_origin));
            holder.txtRight.setTextColor(context.getResources().getColor(R.color.red_origin));
            holder.txtLeft.setText(context.getString(R.string.total_colon));
            String symbol = courseEarning.getTypeAmount() > 0 ? "+" : "-";
            holder.txtRight.setText(symbol + courseEarning.getTypeAmount());
        } else {
            RecentEarningResult.StroreDetail stroreDetail = array.get(position - 1);
            holder.txtRight.setVisibility(View.VISIBLE);
            holder.txtLeft.setTextColor(context.getResources().getColor(R.color.black_gray));
            holder.txtRight.setTextColor(context.getResources().getColor(R.color.black_gray));
            holder.txtLeft.setText(stroreDetail.getStoreName());
            holder.txtRight.setText(stroreDetail.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return array.size() + 2;
    }

    class Viewholder extends RecyclerView.ViewHolder {

        private TextView txtLeft;
        private TextView txtRight;

        public Viewholder(View view) {
            super(view);
            txtLeft = (TextView) view.findViewById(R.id.txt_left);
            txtRight = (TextView) view.findViewById(R.id.txt_right);
        }

    }
}
