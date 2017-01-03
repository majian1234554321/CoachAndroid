package com.leyuan.coach.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.page.adapter.PAgerAdapterTakeClass;
import com.leyuan.coach.page.mvp.presenter.TakeOverClassPresenter;
import com.leyuan.coach.page.mvp.view.TakeOverClassViewListener;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/27.
 */

public class PopupWindowClassNotify extends PopupWindow implements TakeOverClassViewListener {

    private Activity context;
    private ViewPager viewPager;
    private LinearLayout llPointGroup;
    private Button btRefuse;
    private Button btKnowed;
    private ArrayList<View> views = new ArrayList<>();
    private PAgerAdapterTakeClass adapter;

    private TakeOverClassPresenter presenter;
    private ArrayList<ClassSchedule> arrayList = new ArrayList<>();

    public PopupWindowClassNotify(Activity context) {
        super(context);
        this.context = context;
        presenter = new TakeOverClassPresenter(this, context);
        initView();
        initData();
    }

    private void initView() {

        View view = View.inflate(context, R.layout.popup_window_class_notify, null);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        llPointGroup = (LinearLayout) view.findViewById(R.id.ll_point_group);
        btRefuse = (Button) view.findViewById(R.id.bt_refuse);
        btKnowed = (Button) view.findViewById(R.id.bt_knowed);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.popuStyle);

        ColorDrawable background = new ColorDrawable(0x77000000);
        this.setBackgroundDrawable(background);
    }

    private void initData() {
        btRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item = viewPager.getCurrentItem();
                if (arrayList.isEmpty())
                    return;
                if (item > arrayList.size() - 1)
                    item = arrayList.size() - 1;
                int id = arrayList.get(item).getTimetableId();

                presenter.takeOverClassRefuse(String.valueOf(id));
            }
        });

        btKnowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void showAtBottom(ArrayList<ClassSchedule> arrayList) {
        presenter.getReplaceCourseList();
        this.showAtLocation(((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0), Gravity.BOTTOM, 0, 0);
    }


    @Override
    public void onGetRepalceCourseList(ArrayList<ClassSchedule> arrayList) {
        this.arrayList.clear();
        this.arrayList.addAll(arrayList);
        adapter = new PAgerAdapterTakeClass(context, arrayList);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onAgreeResult(boolean success) {

    }

    @Override
    public void onRefuseResult(boolean success) {

    }
}
