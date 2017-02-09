package com.leyuan.coach.page.mvp.view.classScheduleView;

import android.content.Context;
import android.view.View;

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
    public void setHintCourse(CourseResult courseResult) {
        String hint = null;
        if(courseResult.getConfirm() == null){
            hint = courseResult.getDateTime() + " 待确认" +   courseResult.getTdb()
                    + "节课 " + courseResult.getMinute() + "分钟";
        }else{
            hint = courseResult.getDateTime() + " 已确认" +   courseResult.getConfirm()
                    + "节课 " + courseResult.getMinute() + "分钟";
        }
        txtClassNumber.setText(hint);
    }

    @Override
    public void setHintLayout(View txtSignHint) {
        txtSignHint.setVisibility(View.GONE);
    }


}
