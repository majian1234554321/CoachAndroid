package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;


/**
 * Created by user on 2017/1/3.
 */
public class SignRecordAdapter extends UltimateViewAdapter<SignRecordAdapter.ViewHolder> {


    private Context context;

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
        return 0;
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

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
