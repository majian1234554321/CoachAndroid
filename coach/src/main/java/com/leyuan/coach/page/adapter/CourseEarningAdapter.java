package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.RecentEaringResult;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/9.
 */
public class CourseEarningAdapter extends RecyclerView.Adapter<CourseEarningAdapter.Viewholder> {

    private Context context;
    private ArrayList<RecentEaringResult.CourseEarning> lists;

    public CourseEarningAdapter(Context context, ArrayList<RecentEaringResult.CourseEarning> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_child_course_earning, null, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(View itemView) {
            super(itemView);
        }
    }
}
