package com.leyuan.coach.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.utils.ToastGlobal;


public class DialogEditCourseNumIdentify extends Dialog implements View.OnClickListener {


    private ClassSchedule course;
    private TextView txtCourseName;
    private TextView txtCourseTime;
    private OnCourseJoinNumListner listner;
//    private String courseId;

    public DialogEditCourseNumIdentify(Context context, ClassSchedule course) {
        super(context, R.style.MyDialog);
        this.course = course;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_course_num);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        //初始化界面控件
        initView();
        //初始化界面数据
        initData();

    }

    private void initView() {
        txtCourseName = (TextView) findViewById(R.id.txt_course_name);
        txtCourseTime = (TextView) findViewById(R.id.txt_course_time);
        findViewById(R.id.bt_confirm).setOnClickListener(this);
        findViewById(R.id.bt_close).setOnClickListener(this);
    }

    private void initData() {
        txtCourseName.setText(course.getCourseName());
        txtCourseTime.setText(course.getCourseTime());
        getEditNum().setText(course.getAppointed()+"");
    }
//
//    public DialogEditCourseNumIdentify setData(String courseName, String courseTime, String id) {
//        txtCourseName.setText(courseName);
//        txtCourseTime.setText(courseTime);
//        courseId = id;
//        return this;
//    }

    public DialogEditCourseNumIdentify setOnCourseJoinNumListner(OnCourseJoinNumListner listner) {
        this.listner = listner;
        return this;
    }


    private EditText getEditNum() {
        return (EditText) findViewById(R.id.edit_num);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_confirm:
                if (listner != null) {
                    String num = getEditNum().getText().toString().trim();
                    if (TextUtils.isEmpty(num)) {
                        ToastGlobal.showShortConsecutive("请输入上课人数");
                    } else {
                        dismiss();
                        listner.onConfirmNum(course.getTimetableId() + "", num);
                    }
                }
                break;
            case R.id.bt_close:
                dismiss();

                break;
        }
    }

    public interface OnCourseJoinNumListner {
        void onConfirmNum(String id, String attendance);

    }

}

