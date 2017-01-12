package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.RecentEarningResult;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/9.
 */
public class CourseEarningAdapter extends RecyclerView.Adapter<CourseEarningAdapter.Viewholder> {

    private Context context;
    private ArrayList<RecentEarningResult.CourseEarning> lists;

    public CourseEarningAdapter(Context context, ArrayList<RecentEarningResult.CourseEarning> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_child_course_earning, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        RecentEarningResult.CourseEarning courseEarning = lists.get(position);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        CourseEarningDetailAdapter adapter = new CourseEarningDetailAdapter(context, courseEarning);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public Viewholder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        }
    }
}
