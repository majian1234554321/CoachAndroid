package com.leyuan.coach.page.mvp.view.classScheduleView;

import android.content.Context;

import com.leyuan.coach.bean.CourseResult;
import com.leyuan.coach.page.mvp.view.ClassScheduleViewListener;

/**
 * Created by user on 2017/1/11.
 */

public class NextMonthClassScheduleView extends BaseClassScheduleView {


    public NextMonthClassScheduleView(Context context) {
        this(context, null);
    }

    public NextMonthClassScheduleView(Context context, ClassScheduleViewListener listener) {
        super(context, listener);
    }


    @Override
    public void setHintLayout(CourseResult courseResult) {

    }


}
