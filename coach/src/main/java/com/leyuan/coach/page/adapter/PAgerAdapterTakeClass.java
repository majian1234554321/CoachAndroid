package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.config.Constant;
import com.leyuan.coach.page.activity.course.MapActivity;
import com.leyuan.commonlibrary.manager.UiManager;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/27.
 */
public class PAgerAdapterTakeClass extends PagerAdapter {
    private Context context;
    private ArrayList<ClassSchedule> arrayList;

    public PAgerAdapterTakeClass(Context context, ArrayList<ClassSchedule> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        if (arrayList == null)
            return 0;
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.item_take_class_list, null);

        ImageView imgCourseType = (ImageView) view.findViewById(R.id.img_course_type);
        TextView txtCourseName = (TextView) view.findViewById(R.id.txt_course_name);
        TextView txtDate = (TextView) view.findViewById(R.id.txt_date);
        TextView txtTime = (TextView) view.findViewById(R.id.txt_time);
        TextView txtStoreName = (TextView) view.findViewById(R.id.txt_store_name);
        TextView txtAddress = (TextView) view.findViewById(R.id.txt_address);
        TextView txtMap = (TextView) view.findViewById(R.id.txt_map);

        final ClassSchedule course = arrayList.get(position);
        if (course.getCourseType() == 2) {
            imgCourseType.setImageResource(R.drawable.exchangeclass);
        } else {
            imgCourseType.setImageResource(R.drawable.substitute);
        }

        txtCourseName.setText(course.getCourseName());
        txtDate.setText(course.getBeginTime());
        txtTime.setText(course.getBeginTime() + "-" + course.getEndTime());
        txtStoreName.setText(course.getStoreName());
        txtAddress.setText(course.getAddress());
        txtMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bunble = new Bundle();
                bunble.putString(Constant.CURRENT_DATE, "2016-11-11");
                bunble.putParcelable(Constant.CLASS_SCHEDULE, course);
                UiManager.activityJump(context, bunble, MapActivity.class);
            }
        });

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
