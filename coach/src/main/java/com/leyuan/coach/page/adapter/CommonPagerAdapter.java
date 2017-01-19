package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/18.
 */
public class CommonPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<View> views;

    public CommonPagerAdapter(Context context, ArrayList<View> views) {

        this.context = context;
        this.views = views;
    }

    @Override
    public int getCount() {
        if (views == null)
            return 0;
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }
}
