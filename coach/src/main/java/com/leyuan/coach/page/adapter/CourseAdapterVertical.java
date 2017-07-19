package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.utils.LogUtil;
import com.leyuan.commonlibrary.util.MyDateUtils;
import com.leyuan.commonlibrary.util.StringUtils;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/24.
 */
public class CourseAdapterVertical extends RecyclerView.Adapter<CourseAdapterVertical.Viewholder> {
    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<ClassSchedule> courseArray;
    private OnCourseItemClickListener listener;
    private String cuurentDataTag;

    public CourseAdapterVertical(Context context, OnCourseItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        mInflater = LayoutInflater.from(context);
        cuurentDataTag = MyDateUtils.getCurrentDay();
//        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void refreshData(ArrayList<ClassSchedule> courseArray, String calendarDateByPosition) {
        this.courseArray = courseArray;
        this.notifyDataSetChanged();
        if(!TextUtils.isEmpty(calendarDateByPosition)){
            cuurentDataTag = calendarDateByPosition;
        }
        LogUtil.i("CourseAdapterVertical refreshData  cuurentDataTag = " +  cuurentDataTag
                +" MyDateUtils.isValidDya" +  MyDateUtils.isValidDya(cuurentDataTag));
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_course_new, parent, false);
        return new Viewholder(view);
    }


    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        final ClassSchedule course = courseArray.get(position);
        holder.btSignState.setVisibility(View.VISIBLE);
        holder.btSignState.setClickable(false);
        holder.btSignState.setOnClickListener(null);

        if (course.getCourseType() <= 0) {
            holder.txtCourseJoinNum.setVisibility(View.GONE);
        } else {
            holder.txtCourseJoinNum.setVisibility(View.VISIBLE);
        }
        switch (course.getCourseType()) {
            case 2:
                holder.imgCourseState.setVisibility(View.VISIBLE);
                holder.imgCourseState.setImageResource(R.drawable.exchangeclass);
                holder.btSignState.setVisibility(View.GONE);
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
                if (course.getPrice() <= 0 && course.getAttendance() <= 0 && MyDateUtils.isValidDya(cuurentDataTag)) {
                    holder.btSignState.setSelected(true);
                    holder.btSignState.setText(context.getString(R.string.course_num));
                    holder.btSignState.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onEditCourseJoinNum(course);
                        }
                    });
                }
                break;
            case 6:
                holder.btSignState.setSelected(false);
                holder.btSignState.setText(context.getString(R.string.beLate));
                if (course.getPrice() <= 0 && course.getAttendance() <= 0&& MyDateUtils.isValidDya(cuurentDataTag)) {
                    holder.btSignState.setSelected(true);
                    holder.btSignState.setText(context.getString(R.string.course_num));
                    holder.btSignState.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onEditCourseJoinNum(course);
                        }
                    });
                }

                break;
            case 7:
                holder.btSignState.setSelected(false);
                holder.btSignState.setText(context.getString(R.string.truant));
                break;
            default:
                holder.btSignState.setVisibility(View.GONE);
                break;
        }

//        holder.txtCourseTime.setText(course.getBeginTime() + "-" + course.getEndTime());

        holder.txtCourseName.setText(StringUtils.endSubString(course.getCourseName(),12));
        holder.txtStoreName.setText("场馆: " + course.getStoreName());
        holder.txtCourseAddress.setText("地址: " + course.getAddress());
        holder.txtCourseStartTime.setText(Html.fromHtml("开始: <font color='#d51121'>" + course.getBeginTime() + "</font>"));
        holder.txtCourseEndTime.setText(Html.fromHtml("结束: <font color='#d51121'>" + course.getEndTime() + "</font>"));
        holder.txtCourseJoinNum.setText("人数:  " + course.getAppointed());

        //注册监听的位置
        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(course);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (courseArray == null)
            return 0;
        return courseArray.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        private TextView txtCourseStartTime, txtCourseEndTime, txtCourseJoinNum;
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
            txtCourseStartTime = (TextView) view.findViewById(R.id.txt_course_start_time);
            txtCourseEndTime = (TextView) view.findViewById(R.id.txt_course_end_time);
            txtCourseJoinNum = (TextView) view.findViewById(R.id.txt_course_join_num);
        }
    }

    public interface OnCourseItemClickListener {

        void onRightButtonClick(int id);

        void onItemClick(ClassSchedule course);

        void onEditCourseJoinNum(ClassSchedule course);
    }
}
