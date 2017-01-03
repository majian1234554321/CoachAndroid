package com.leyuan.coach.page.activity.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.MyCalendar;
import com.leyuan.coach.config.Constant;
import com.leyuan.coach.config.StringConstant;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.page.adapter.CalendarAdapter;
import com.leyuan.coach.widget.CommonTitleLayout;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/2.
 */

public class CalendarActivity extends BaseActivity {
    private CommonTitleLayout titleLayout;
    private RecyclerView recyclerView;

    private int positionClicked;
    ArrayList<MyCalendar> myCalendars;
    private CalendarAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
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

        int temp = -1;
        int monthClickedIndex = 0;
        int monthClickedPosition = 0;
        for (; monthClickedIndex < myCalendars.size(); monthClickedIndex++) {
            temp += myCalendars.get(monthClickedIndex).getDayList().length;
            if (positionClicked <= temp) {
                monthClickedPosition = positionClicked - temp;
                break;
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new CalendarAdapter(this, myCalendars, positionClicked);
        adapter.setOnCalendarClickListener(calendarListener);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishMyself(positionClicked);
    }

    private CalendarAdapter.OnCalendarClickListener calendarListener = new CalendarAdapter.OnCalendarClickListener() {
        @Override
        public void onClick(int itemPosition) {
            finishMyself(itemPosition);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void finishMyself(int positionClicked) {
        Intent intent = new Intent();
        intent.putExtra(Constant.SELECT_CALENDAR_DAY, positionClicked);
        setResult(Constant.RESULT_CALENDAR, intent);
        finish();
    }
}
