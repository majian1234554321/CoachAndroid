package com.leyuan.coach.page.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.leyuan.coach.R;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.MainActivity;
import com.leyuan.coach.page.adapter.CommonPagerAdapter;
import com.leyuan.coach.utils.SharePrefUtils;
import com.leyuan.commonlibrary.manager.UiManager;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/18.
 */
public class GuideActivity extends BaseActivity {
    private ViewPager viewPager;
    private boolean autoLoginSuccess;

    private int[] guideImageIds = new int[]{
            R.drawable.guide_first, R.drawable.guide_second, R.drawable.guide_third,
            R.drawable.guide_fourth, R.drawable.guide_fifth
    };

    private ArrayList<View> views = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        for (int i = 0; i < guideImageIds.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_gudie_viewpager, null, false);
            ImageView imgGuide = (ImageView) view.findViewById(R.id.img_guide);
            imgGuide.setImageResource(guideImageIds[i]);
            if (i == (guideImageIds.length - 1)) {
                view.findViewById(R.id.bt_start_app).setVisibility(View.VISIBLE);
                view.findViewById(R.id.bt_start_app).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharePrefUtils.putIsFirstOpenApp(GuideActivity.this);
                        UiManager.activityJump(GuideActivity.this, MainActivity.class);
                        finish();
                    }
                });
            }
            views.add(view);
        }

        viewPager.setAdapter(new CommonPagerAdapter(this, views));
    }
}
