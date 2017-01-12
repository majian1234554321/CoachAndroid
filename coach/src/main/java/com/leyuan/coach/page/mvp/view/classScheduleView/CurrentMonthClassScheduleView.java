package com.leyuan.coach.page.mvp.view.classScheduleView;

import android.content.Context;

import com.leyuan.coach.bean.CourseResult;
import com.leyuan.coach.page.mvp.view.ClassScheduleViewListener;

/**
 * Created by user on 2017/1/11.
 */
public class CurrentMonthClassScheduleView extends BaseClassScheduleView {

    public CurrentMonthClassScheduleView(Context context) {
        this(context, null);
    }

    @Override
    public void setHintLayout(CourseResult courseResult) {
        txtClassNumber.setText(courseResult.getDateTime() + " 本月共" + courseResult.getCourseSize() + "节课");
        if (courseResult.getNormalCou() == 0 && courseResult.getAbnormalCou() == 0) {
            txtSignAll.setText("注:请于课前15分钟内签到");
        } else {
            txtSignAll.setText("正常签到" + courseResult.getNormalCou() + "节 " + "异常签到" + courseResult.getAbnormalCou() + "节");
        }
    }

    public CurrentMonthClassScheduleView(Context context, ClassScheduleViewListener listener) {
        super(context, listener);
    }
}
