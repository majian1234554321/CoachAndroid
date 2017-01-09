package com.leyuan.commonlibrary.manager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by user on 2017/1/7.
 */

public class LinearLayoutManagerNoScroll extends LinearLayoutManager {

    private boolean isScrollVerticalEnabled = true;
    private boolean isScrollHorizontalEnabled = true;

    public LinearLayoutManagerNoScroll(Context context) {
        super(context);
    }

    public LinearLayoutManagerNoScroll(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LinearLayoutManagerNoScroll(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setScrollVerticalEnabled(boolean scrollVerticalEnabled) {
        isScrollVerticalEnabled = scrollVerticalEnabled;
    }

    public void setScrollHorizontalEnabled(boolean scrollHorizontalEnabled) {
        isScrollHorizontalEnabled = scrollHorizontalEnabled;
    }

    @Override
    public boolean canScrollHorizontally() {
        return isScrollHorizontalEnabled && super.canScrollHorizontally();
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollVerticalEnabled && super.canScrollVertically();
    }
}
