package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.ClassSchedule;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/24.
 */
public class CourseAdapterVertical extends RecyclerView.Adapter<CourseAdapterVertical.Viewholder> {
    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<ClassSchedule> courseArray ;
    private OnCourseItemClickListener listener;

    public CourseAdapterVertical(Context context, OnCourseItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void refreshData(ArrayList<ClassSchedule> courseArray) {
        this.courseArray = courseArray;
        this.notifyDataSetChanged();
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.from(context).inflate(R.layout.item_course, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        final ClassSchedule course = courseArray.get(position);
        switch (course.getCourseType()) {
            case 2:
                holder.imgCourseState.setVisibility(View.VISIBLE);
                holder.imgCourseState.setImageResource(R.drawable.exchangeclass);
                break;
            case 3:
                holder.imgCourseState.setVisibility(View.VISIBLE);
                holder.imgCourseState.setImageResource(R.drawable.substitute);
                break;
            case 4:
                holder.imgCourseState.setVisibility(View.VISIBLE);
                holder.imgCourseState.setImageResource(R.drawable.tingke);
                break;
            default:
                holder.imgCourseState.setVisibility(View.GONE);

        }

        holder.btSignState.setVisibility(View.VISIBLE);
        holder.btSignState.setClickable(false);


        switch (course.getSignStatus()) {
            case 4:
                holder.btSignState.setSelected(true);
                holder.btSignState.setText(context.getString(R.string.to_sign_in));
                final int id = course.getTimetableId();
                holder.btSignState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onRightButtonClick(id);
                    }
                });
                break;
            case 5:
                holder.btSignState.setSelected(false);
                holder.btSignState.setText(context.getString(R.string.singed));
                break;
            case 6:
                holder.btSignState.setSelected(false);
                holder.btSignState.setText(context.getString(R.string.beLate));
                break;
            case 7:
                holder.btSignState.setSelected(false);
                holder.btSignState.setText(context.getString(R.string.truant));
                break;
            default:
                holder.btSignState.setVisibility(View.GONE);
                break;
        }

        holder.txtCourseTime.setText(course.getBeginTime() + "-" + course.getEndTime());
        holder.txtCourseName.setText(course.getCourseName() + "");
        holder.txtStoreName.setText(course.getStoreName() + "");
        holder.txtCourseAddress.setText(course.getAddress());

        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(course);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(courseArray == null)
            return  0;
        return courseArray.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        private ImageView imgCourseState;
        private TextView txtCourseTime;
        private TextView txtCourseName;
        private TextView txtStoreName;
        private TextView txtCourseAddress;
        private Button btSignState;
        private RelativeLayout layoutRoot;

        public Viewholder(View view) {
            super(view);
            imgCourseState = (ImageView) view.findViewById(R.id.img_course_state);
            txtCourseTime = (TextView) view.findViewById(R.id.txt_course_time);
            txtCourseName = (TextView) view.findViewById(R.id.txt_course_name);
            txtStoreName = (TextView) view.findViewById(R.id.txt_store_name);
            txtCourseAddress = (TextView) view.findViewById(R.id.txt_course_address);
            btSignState = (Button) view.findViewById(R.id.bt_sign_state);
            layoutRoot = (RelativeLayout) view.findViewById(R.id.layout_root);
        }
    }

    public interface OnCourseItemClickListener {

        void onRightButtonClick(int id);

        void onItemClick(ClassSchedule course);
    }
}
