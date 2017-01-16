package com.leyuan.coach.page.activity.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.leyuan.coach.R;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.adapter.CommonFragmentPagerAdapter;
import com.leyuan.coach.page.fragment.mine.WithDrawAlipayFragment;
import com.leyuan.coach.page.fragment.mine.WithDrawBankFragment;
import com.leyuan.coach.widget.CommonTitleLayout;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/28.
 */
public class WithDrawActivity extends BaseActivity implements View.OnClickListener {

    private CommonTitleLayout layoutTitle;
    private RelativeLayout relAlipay;
    private RelativeLayout relBank;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ImageView imgAlipay;
    private ImageView imgBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_withdraw);

        initView();
        initData();
    }

    private void initView() {
        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        relAlipay = (RelativeLayout) findViewById(R.id.rel_alipay);
        relBank = (RelativeLayout) findViewById(R.id.rel_bank);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        imgAlipay = (ImageView) findViewById(R.id.img_alipay);
        imgBank = (ImageView) findViewById(R.id.img_bank);

        layoutTitle.setLeftIconListener(this);
        relAlipay.setOnClickListener(this);
        relBank.setOnClickListener(this);

    }

    private void initData() {
        fragments.add(new WithDrawAlipayFragment());
        fragments.add(new WithDrawBankFragment());
        viewPager.setAdapter(new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(pagerListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.rel_alipay:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rel_bank:
                viewPager.setCurrentItem(1);
                break;

        }
    }

    private ViewPager.OnPageChangeListener pagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    relAlipay.setSelected(true);
                    relBank.setSelected(false);
                    imgAlipay.setVisibility(View.VISIBLE);
                    imgBank.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    relAlipay.setSelected(false);
                    relBank.setSelected(true);
                    imgAlipay.setVisibility(View.INVISIBLE);
                    imgBank.setVisibility(View.VISIBLE);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
