package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.MyCalendar;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/23.
 */

public class CourseAdapterHorizontal extends RecyclerView.Adapter<CourseAdapterHorizontal.ViewHolder> {

    private Context context;
    private ArrayList<MyCalendar> myCalendars = new ArrayList();
    private ArrayList<Integer> calendarsFirst = new ArrayList<>();
    private ArrayList<Integer> calendarsSecond = new ArrayList<>();
    private OnItemClickListener listener;

    public CourseAdapterHorizontal(Context context, ArrayList<MyCalendar> myCalendars, OnItemClickListener listener) {

        this.context = context;
        this.myCalendars = myCalendars;
        this.listener = listener;
        if (!myCalendars.isEmpty()) {

            for (Integer calendar : myCalendars.get(0).getDayList()) {
                calendarsFirst.add(calendar);
            }

            for (Integer calendar : myCalendars.get(1).getDayList()) {
                calendarsSecond.add(calendar);
            }
        }
    }

    public CourseAdapterHorizontal(Context context) {
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_course_calendar, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int finalPosition = position;
        if (position < calendarsFirst.size()) {
            holder.txt_date.setText(myCalendars.get(0).getTimeMouth() + position);
            holder.txt_course_number.setText(calendarsFirst.get(position) + "");
        } else {
            position = position - calendarsFirst.size();
            holder.txt_date.setText(myCalendars.get(1).getTimeMouth() + position);
            holder.txt_course_number.setText(calendarsSecond.get(position) + "");
        }

        holder.layout_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(finalPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return calendarsFirst.size() + calendarsSecond.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_date, txt_course_number;
        LinearLayout layout_root;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_course_number = (TextView) itemView.findViewById(R.id.txt_course_number);
            layout_root = (LinearLayout) itemView.findViewById(R.id.layout_root);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int currentPosition);
    }
}
