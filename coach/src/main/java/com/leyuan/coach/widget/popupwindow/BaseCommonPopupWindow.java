package com.leyuan.coach.widget.popupwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by user on 2017/1/13.
 */

public abstract class BaseCommonPopupWindow extends PopupWindow {
    protected Activity context;

    public BaseCommonPopupWindow(Activity context) {
        this(context, null);
    }

    public BaseCommonPopupWindow(Activity context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.setContentView(createView());
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
//        this.setAnimationStyle(R.style.popupStyle);

        ColorDrawable background = new ColorDrawable(0x77000000);
        this.setBackgroundDrawable(background);
        initData();
    }

    protected void showAtBottom() {
        showAtLocation(((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0), Gravity.BOTTOM, 0, 0);
    }

    public void setHeightReal(int height) {
        this.setHeight(height);
    }

    protected abstract void initData();

    protected abstract View createView();


}
