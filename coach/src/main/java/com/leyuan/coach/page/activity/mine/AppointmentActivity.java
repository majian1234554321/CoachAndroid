package com.leyuan.coach.page.activity.mine;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.fragment.AllAppointmentFragment;
import com.leyuan.coach.page.fragment.JoinedAppointmentFragment;
import com.leyuan.coach.page.fragment.UnJoinedAppointmentFragment;
import com.leyuan.coach.widget.SimpleTitleBar;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

public class AppointmentActivity extends BaseActivity implements SmartTabLayout.TabProvider{
    private SimpleTitleBar titleBar;
    private SmartTabLayout tabLayout;
    private ViewPager viewPager;
    private List<View> allTabView = new ArrayList<>();
    private int currentItem = 0;
    private FragmentPagerItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_appointment);
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);
        FragmentPagerItems pages = new FragmentPagerItems(this);
        AllAppointmentFragment all = new AllAppointmentFragment();
        UnJoinedAppointmentFragment unJoined = new UnJoinedAppointmentFragment();
        JoinedAppointmentFragment joined = new JoinedAppointmentFragment();
        pages.add(FragmentPagerItem.of(null, all.getClass()));
        pages.add(FragmentPagerItem.of(null, unJoined.getClass()));
        pages.add(FragmentPagerItem.of(null, joined.getClass()));
        adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentItem);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setCustomTabView(this);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < allTabView.size(); i++) {
                    View tabAt = tabLayout.getTabAt(i);
                    TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
                    text.setTypeface(i == position ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
                }
                currentItem = position;

            }

        });
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Fragment page = adapter.getPage(currentItem);
        if(page instanceof AllAppointmentFragment){
            ((AllAppointmentFragment)page).initData();
        }else if ( page instanceof JoinedAppointmentFragment){
            ((JoinedAppointmentFragment)page).initData();
        }else if (page instanceof UnJoinedAppointmentFragment){
            ((UnJoinedAppointmentFragment)page).initData();
        }
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_appointment_text, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        String[] campaignTab = getResources().getStringArray(R.array.appointmentTab);
        text.setText(campaignTab[position]);
        if (position == 0) {
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }
}
