package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.ClassSchedule;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;


/**
 * Created by user on 2017/1/3.
 */
public class SignRecordAdapter extends UltimateViewAdapter<SignRecordAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ClassSchedule> courses = new ArrayList<>();

    public SignRecordAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public ViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_sign_record, null);
        return new ViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return courses.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassSchedule course = courses.get(position);
        holder.txtCourseName.setText(course.getStoreName() + " " + course.getCourseName());
        holder.txtCourseTime.setText(course.getBeginTime() + "-" + course.getEndTime());
        holder.txtSignTime.setText("" + course.getSignTime());
        switch (course.getSignStatus()) {
            case ClassSchedule.SignStatus.UNSING:
                holder.txtSignStatus.setText("未签到");
                break;
            case ClassSchedule.SignStatus.SINGED:
                holder.txtSignStatus.setText("已签到");
                break;
            case ClassSchedule.SignStatus.BE_LATE:
                holder.txtSignStatus.setText("迟到");
                break;
            case ClassSchedule.SignStatus.TRUANT:
                holder.txtSignStatus.setText("旷课");
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public void refreshData(ArrayList<ClassSchedule> arrayList) {
        courses.clear();
        courses.addAll(arrayList);
        this.notifyDataSetChanged();
    }

    public void addData(ArrayList<ClassSchedule> arrayList) {
        courses.addAll(arrayList);
        this.notifyDataSetChanged();
    }

    class ViewHolder extends UltimateRecyclerviewViewHolder {

        private TextView txtCourseName;
        private TextView txtCourseTime;
        private TextView txtSignTime;
        private TextView txtSignStatus;

        public ViewHolder(View view) {
            super(view);
            txtCourseName = (TextView) view.findViewById(R.id.txt_course_name);
            txtCourseTime = (TextView) view.findViewById(R.id.txt_course_time);
            txtSignTime = (TextView) view.findViewById(R.id.txt_sign_time);
            txtSignStatus = (TextView) view.findViewById(R.id.txt_sign_status);
        }
    }
}
