package com.leyuan.coach.page.activity.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.MyCalendar;
import com.leyuan.coach.config.Constant;
import com.leyuan.coach.config.StringConstant;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.adapter.CalendarAdapterOld;
import com.leyuan.coach.widget.CommonTitleLayout;

import java.util.ArrayList;

import static com.leyuan.coach.R.id.txt_month_first;

/**
 * Created by user on 2016/12/22.
 */

public class CalendarActivityOld extends BaseActivity {
    private CommonTitleLayout titleLayout;
    private ScrollView scrollView;
    private TextView txtMonthFirst;
    private RecyclerView recyclerFirst;
    private TextView txtMonthSecond;
    private RecyclerView recyclerSecond;
    private int positionClicked;

    ArrayList<MyCalendar> myCalendars;
    private CalendarAdapterOld adapterFirst;
    private CalendarAdapterOld adapterSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_old);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            positionClicked = bundle.getInt(StringConstant.POSITION);
            myCalendars = bundle.getParcelableArrayList(StringConstant.ARRAY);

        }

        initView();
        initData();
    }

    private void initView() {
        titleLayout = (CommonTitleLayout) findViewById(R.id.title_layout);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        txtMonthFirst = (TextView) findViewById(txt_month_first);
        recyclerFirst = (RecyclerView) findViewById(R.id.recycler_first);
        txtMonthSecond = (TextView) findViewById(R.id.txt_month_second);
        recyclerSecond = (RecyclerView) findViewById(R.id.recycler_second);
    }

    private void initData() {
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishMyself(positionClicked);
            }
        });

        if (myCalendars == null)
            return;
        txtMonthFirst.setText(myCalendars.get(0).getTimeMouth());
        txtMonthFirst.setText(myCalendars.get(1).getTimeMouth());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 7);

        int positionFirstClicked;
        int positionSecondClicked;
        if (positionClicked > myCalendars.get(0).getDayList().length - 1) {
            positionFirstClicked = -1;
            positionSecondClicked = positionClicked - myCalendars.get(0).getDayList().length;
        } else {
            positionFirstClicked = positionClicked;
            positionSecondClicked = -1;
        }

        adapterFirst = new CalendarAdapterOld(this, myCalendars.get(0).getDayList()
                , myCalendars.get(0).getTimeMouth(), positionFirstClicked);
        adapterSecond = new CalendarAdapterOld(this, myCalendars.get(0).getDayList()
                , myCalendars.get(0).getTimeMouth(), positionSecondClicked);

        adapterFirst.setOnCalendarClickListener(firstListener);
        adapterSecond.setOnCalendarClickListener(secondListener);

        recyclerFirst.setLayoutManager(gridLayoutManager);
        recyclerFirst.setAdapter(adapterFirst);

        recyclerSecond.setLayoutManager(gridLayoutManager);
        recyclerSecond.setAdapter(adapterSecond);

    }

    private CalendarAdapterOld.OnCalendarClickListener firstListener = new CalendarAdapterOld.OnCalendarClickListener() {
        @Override
        public void onClick(int position) {
            positionClicked = position;
            finishMyself(positionClicked);

        }
    };

    private CalendarAdapterOld.OnCalendarClickListener secondListener = new CalendarAdapterOld.OnCalendarClickListener() {
        @Override
        public void onClick(int position) {
            positionClicked = position + myCalendars.get(0).getDayList().length;
            finishMyself(positionClicked);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishMyself(positionClicked);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void finishMyself(int positionClicked){
        Intent intent = new Intent();
        intent.putExtra(Constant.SELECT_CALENDAR_DAY,positionClicked);
        setResult(Constant.RESULT_CALENDAR, intent);
        finish();
    }
}
