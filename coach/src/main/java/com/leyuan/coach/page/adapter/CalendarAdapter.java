package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.MyCalendar;
import com.leyuan.coach.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/2.
 */
public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<MyCalendar> myCalendars = new ArrayList<>();
    private OnCalendarClickListener listener;
    int monthClickedIndex = 0;
    int monthClickedPosition = 0;

    public CalendarAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public CalendarAdapter(Context context, ArrayList<MyCalendar> calendars) {
        this.context = context;
        this.myCalendars.addAll(calendars);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CalendarAdapter(Context context, ArrayList<MyCalendar> calendars, int positionClicked) {
        this.context = context;
        this.myCalendars.addAll(calendars);

        int temp = 0;
        for (; monthClickedIndex < myCalendars.size(); monthClickedIndex++) {
            temp += myCalendars.get(monthClickedIndex).getDayList().length;
            if (positionClicked < temp) {
                monthClickedPosition = positionClicked - temp + myCalendars.get(monthClickedIndex).getDayList().length;
                break;
            }
        }
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void refreshData(ArrayList<MyCalendar> calendars) {
        this.myCalendars.clear();
        this.myCalendars.addAll(calendars);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_calendar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MyCalendar calendar = myCalendars.get(position);
        holder.txtMonth.setText(calendar.getTimeMouth() + "");

        int clickedPosition = monthClickedIndex == position ? monthClickedPosition : -1;
        CalendarItemAdapter adapter = new CalendarItemAdapter(context, calendar.getDayList(),
                calendar.getTimeMouth(), clickedPosition);
        adapter.setOnCalendarClickListener(new CalendarItemAdapter.OnCalendarClickListener() {
            @Override
            public void onClick(int itemPosition) {

                if (listener != null) {
                    for (int i = 0; i < position; i++) {
                        itemPosition += myCalendars.get(i).getDayList().length;
                    }

                    LogUtil.i("CalendarActivity", "item positionClicked  = " + itemPosition);
                    listener.onClick(itemPosition);
                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7);
        holder.recyclerView.setLayoutManager(gridLayoutManager);
        holder.recyclerView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return myCalendars.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtMonth;
        private RecyclerView recyclerView;

        public ViewHolder(View view) {
            super(view);
            txtMonth = (TextView) view.findViewById(R.id.txt_month);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        }
    }

    public void setOnCalendarClickListener(OnCalendarClickListener listener) {
        this.listener = listener;
    }

    public interface OnCalendarClickListener {
        void onClick(int itemPosition);
    }
}
