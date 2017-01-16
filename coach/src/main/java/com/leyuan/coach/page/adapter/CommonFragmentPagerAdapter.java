package com.leyuan.coach.page.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/16.
 */
public class CommonFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;

    public CommonFragmentPagerAdapter(FragmentManager manager, ArrayList<Fragment> fragments) {
        super(manager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
