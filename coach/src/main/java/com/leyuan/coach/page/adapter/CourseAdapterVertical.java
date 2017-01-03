package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.ClassSchedule;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/24.
 */
public class CourseAdapterVertical extends RecyclerView.Adapter<CourseAdapterVertical.Viewholder> {
    private Context context;
    private ArrayList<ClassSchedule> courseArray = new ArrayList<>();
    private OnCourseItemClickListener listener;

    public CourseAdapterVertical(Context context, OnCourseItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void refreshData(ArrayList<ClassSchedule> courseArray) {
        this.courseArray.clear();
        this.courseArray.addAll(courseArray);
        this.notifyDataSetChanged();
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_course, null);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        final ClassSchedule course = courseArray.get(position);
        switch (course.getCourseType()) {
            case 2:
                holder.imgCourseState.setVisibility(View.VISIBLE);
                holder.imgCourseState.setSelected(true);
            case 3:
                holder.imgCourseState.setVisibility(View.VISIBLE);
                holder.imgCourseState.setEnabled(false);
            default:
                holder.imgCourseState.setVisibility(View.GONE);

        }

        holder.btSignState.setVisibility(View.VISIBLE);
        holder.btSignState.setClickable(false);


        switch (course.getSignStatus()) {
            case 1:
                holder.btSignState.setSelected(true);
                holder.btSignState.setText(context.getString(R.string.to_sign_in));
                final int id = course.getTimetableId();
                holder.btSignState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.toSignIn(id);
                    }
                });
            case 2:
                holder.btSignState.setSelected(false);
                holder.btSignState.setText(context.getString(R.string.singed));
            case 3:
                holder.btSignState.setSelected(false);
                holder.btSignState.setText(context.getString(R.string.beLate));
            case 4:
                holder.btSignState.setSelected(false);
                holder.btSignState.setText(context.getString(R.string.truant));
            default:
                holder.btSignState.setVisibility(View.GONE);
        }

        holder.txtCourseTime.setText(course.getBeginTime() + "-" + course.getEndTime());
        holder.txtCourseName.setText(course.getCourseName() + "");
        holder.txtStoreName.setText(course.getStoreName() + "");
        holder.txtCourseAddress.setText(course.getAddress());

        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.turnToMap(course);
            }
        });

    }

    @Override
    public int getItemCount() {
        return courseArray.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        private ImageView imgCourseState;
        private TextView txtCourseTime;
        private TextView txtCourseName;
        private TextView txtStoreName;
        private TextView txtCourseAddress;
        private Button btSignState;
        private FrameLayout layoutRoot;

        public Viewholder(View view) {
            super(view);
            imgCourseState = (ImageView) view.findViewById(R.id.img_course_state);
            txtCourseTime = (TextView) view.findViewById(R.id.txt_course_time);
            txtCourseName = (TextView) view.findViewById(R.id.txt_course_name);
            txtStoreName = (TextView) view.findViewById(R.id.txt_store_name);
            txtCourseAddress = (TextView) view.findViewById(R.id.txt_course_address);
            btSignState = (Button) view.findViewById(R.id.bt_sign_state);
            layoutRoot = (FrameLayout) view.findViewById(R.id.layout_root);
        }
    }

    public interface OnCourseItemClickListener {

        void toSignIn(int id);

        void turnToMap(ClassSchedule course);
    }
}
