package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.MyCalendar;
import com.leyuan.coach.utils.CourseDateUtils;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/23.
 */

public class CourseAdapterHorizontal extends RecyclerView.Adapter<CourseAdapterHorizontal.ViewHolder> {

    private Context context;
    private OnItemClickListener listener;
    private ArrayList<Integer> calendars = new ArrayList<>();

    private ArrayList<MyCalendar> myCalendars;
//    private ArrayList<Integer> calendarsFirst = new ArrayList<>();
//    private ArrayList<Integer> calendarsSecond = new ArrayList<>();

    public CourseAdapterHorizontal(Context context, ArrayList<MyCalendar> myCalendars, OnItemClickListener listener) {

        this.context = context;
        this.myCalendars = myCalendars;
        this.listener = listener;
        for (MyCalendar calendar : myCalendars) {
            for (Integer day : calendar.getDayList()) {
                calendars.add(day);
            }
        }

        //        this.myCalendars.addAll(myCalendars);
//        this.myCalendars = myCalendars;

    }

    public CourseAdapterHorizontal(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void refreshData(ArrayList<MyCalendar> myCalendars) {
        calendars.clear();
        this.myCalendars = myCalendars;
        for (MyCalendar calendar : myCalendars) {
            for (Integer day : calendar.getDayList()) {
                calendars.add(day);
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_course_calendar, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int finalPosition = position;

        holder.txt_date.setText(CourseDateUtils.getCalendarDateByPosition(position, myCalendars));
        holder.txt_course_number.setText(calendars.get(position) + "节课");
        holder.layout_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(finalPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return calendars.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_date, txt_course_number;
        LinearLayout layout_root;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_date = (TextView) itemView.findViewById(R.id.tv_title);
            txt_course_number = (TextView) itemView.findViewById(R.id.txt_course_number);
            layout_root = (LinearLayout) itemView.findViewById(R.id.layout_root);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int currentPosition);
    }
}
