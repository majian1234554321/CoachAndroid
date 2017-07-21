package com.leyuan.coach.widget.popupwindow;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.ClassSchedule;
import com.leyuan.coach.page.adapter.PagerAdapterTakeCourse;
import com.leyuan.coach.page.mvp.presenter.ClassNotifyPresenter;
import com.leyuan.coach.page.mvp.view.ClassNotifyViewListener;
import com.leyuan.coach.utils.ToastGlobal;
import com.leyuan.commonlibrary.manager.DisplayManager;
import com.leyuan.commonlibrary.util.DialogUtils;
import com.leyuan.commonlibrary.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/27.
 */

public abstract class PopupWindowClassNotify extends BaseCommonPopupWindow implements ClassNotifyViewListener {


    protected ViewPager viewPager;
    protected LinearLayout llPointGroup;
    protected Button btRefuse;
    protected Button btKnowed;
    protected PagerAdapterTakeCourse adapter;
    protected ClassNotifyPresenter presenter;

    protected ArrayList<ClassSchedule> arrayList = new ArrayList<>();
    protected RelativeLayout layoutTakeOverClass;
    protected Button btKnowSuspend;
    private int widthPoint;
    private int pointMargin;

    public PopupWindowClassNotify(Activity context) {
        super(context);
    }

    protected View createView() {
        View view = View.inflate(context, R.layout.popup_window_class_notify, null);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        llPointGroup = (LinearLayout) view.findViewById(R.id.ll_point_group);
        btRefuse = (Button) view.findViewById(R.id.bt_refuse);
        btKnowed = (Button) view.findViewById(R.id.bt_knowed);
        layoutTakeOverClass = (RelativeLayout) view.findViewById(R.id.layout_take_over_class);
        btKnowSuspend = (Button) view.findViewById(R.id.bt_know_suspend);
        this.setOutsideTouchable(false);
        return view;
    }

    protected void initData() {
        widthPoint = DisplayManager.dp2px(context, 8);
        pointMargin = DisplayManager.dp2px(context, 15);
        presenter = createPresenter(this, context);

        setNotifyLayoutVisible();

        btRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDialog(context, "", false);

                presenter.courseRefuse(getCurrentId(), viewPager.getCurrentItem());
            }
        });

        btKnowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDialog(context, "", false);
                presenter.courseAgree(getCurrentId(), viewPager.getCurrentItem());
            }
        });

        btKnowSuspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDialog(context, "", false);
                presenter.courseAgree(getCurrentId(), viewPager.getCurrentItem());
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < llPointGroup.getChildCount(); i++) {
                    //还原背景
                    View indicatorView = llPointGroup.getChildAt(i);
                    indicatorView.setBackgroundResource(R.drawable.shape_point_indicator_normal);

                    if (i == position) {
                        indicatorView.setBackgroundResource(R.drawable.shape_point_indicator_selector);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


//        setOutsideTouchable(false);
//        setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return event.getAction() == MotionEvent.ACTION_OUTSIDE;
//            }
//        });
//        getContentView().setFocusable(true);
//        getContentView().setFocusableInTouchMode(true);
//
//        getContentView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    return true;
//                }
//                return false;
//            }
//        });
    }


    private String getCurrentId() {
        int item = viewPager.getCurrentItem();
        if (arrayList.isEmpty())
            return null;
        if (item > arrayList.size() - 1)
            item = arrayList.size() - 1;
        int id = arrayList.get(item).getTimetableId();
        return String.valueOf(id);
    }

    public void showAtBottom(ArrayList<ClassSchedule> arrayList) {
        if (arrayList == null)
            return;
        this.arrayList = arrayList;
        adapter = new PagerAdapterTakeCourse(context, arrayList);
        viewPager.setAdapter(adapter);
        llPointGroup.removeAllViews();
        for (int i = 0; i < arrayList.size(); i++) {
            View point = new View(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthPoint, widthPoint);
            if (i > 0) {
                params.leftMargin = pointMargin;
                point.setBackgroundResource(R.drawable.shape_point_indicator_normal);
            } else {
                point.setBackgroundResource(R.drawable.shape_point_indicator_selector);
            }
            point.setLayoutParams(params);
            llPointGroup.addView(point);
        }
        showAtBottom();
    }

    public void showAtBottomAddData(ArrayList<ClassSchedule> arrayList){
        if (arrayList == null) return;
        this.arrayList.addAll(arrayList);
        adapter = new PagerAdapterTakeCourse(context, this.arrayList);
        viewPager.setAdapter(adapter);
        llPointGroup.removeAllViews();
        for (int i = 0; i < arrayList.size(); i++) {
            View point = new View(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthPoint, widthPoint);
            if (i > 0) {
                params.leftMargin = pointMargin;
                point.setBackgroundResource(R.drawable.shape_point_indicator_normal);
            } else {
                point.setBackgroundResource(R.drawable.shape_point_indicator_selector);
            }
            point.setLayoutParams(params);
            llPointGroup.addView(point);
        }
        showAtBottom();
    }

    @Override
    public void onAgreeResult(boolean success, int currentItem) {

//新版改成无论成功还是失败 都给关掉
//        if (success) {
        arrayList.remove(currentItem);
        llPointGroup.removeViewAt(currentItem);
        adapter.notifyDataSetChanged();
        ToastUtil.showLong(context, "已接受");
        if (arrayList.isEmpty())
            this.dismiss();
//        }
        DialogUtils.dismissDialog();

    }

    @Override
    public void onRefuseResult(boolean success, int currentItem) {
        //新版改成无论成功还是失败 都给关掉
        arrayList.remove(currentItem);
        llPointGroup.removeViewAt(currentItem);
        adapter.notifyDataSetChanged();
        if (arrayList.isEmpty())
            this.dismiss();
        DialogUtils.dismissDialog();
        if(success){
            ToastGlobal.showLong("已拒绝");
        }
    }

    protected abstract ClassNotifyPresenter createPresenter(PopupWindowClassNotify popupWindowClassNotify, Activity context);

    protected abstract void setNotifyLayoutVisible();

}
