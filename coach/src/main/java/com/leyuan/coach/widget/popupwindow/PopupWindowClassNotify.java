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
        return view;
    }

    protected void initData() {
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
        this.arrayList.clear();
        this.arrayList.addAll(arrayList);
        adapter = new PagerAdapterTakeCourse(context, arrayList);
        viewPager.setAdapter(adapter);
        showAtBottom();
    }

    @Override
    public void onAgreeResult(boolean success, int currentItem) {
        if (success) {
            arrayList.remove(currentItem);
            adapter.refreshData(currentItem);
            ToastUtil.showLong(context, "已接受");
            if (arrayList.isEmpty())
                this.dismiss();
        }
        DialogUtils.dismissDialog();

    }

    @Override
    public void onRefuseResult(boolean success, int currentItem) {
        if (success) {
            arrayList.remove(currentItem);
            adapter.refreshData(currentItem);
            ToastUtil.showLong(context, "已拒绝");
            if (arrayList.isEmpty())
                this.dismiss();
        }
        DialogUtils.dismissDialog();
    }

    protected abstract ClassNotifyPresenter createPresenter(PopupWindowClassNotify popupWindowClassNotify, Activity context);

    protected abstract void setNotifyLayoutVisible();

}
