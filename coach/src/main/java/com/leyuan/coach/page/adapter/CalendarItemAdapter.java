package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.commonlibrary.util.MyDateUtils;

/**
 * Created by user on 2017/1/3.
 */
public class CalendarItemAdapter extends RecyclerView.Adapter<CalendarItemAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private int fristWeekDay;
    private Context context;
    private int[] dayList;
    private int currentDay = -1;
    private int positionClicked = -1;
    private OnCalendarClickListener listener;

    public CalendarItemAdapter(Context context, int[] dayList, String month) {
        this.context = context;
        this.dayList = dayList;
        fristWeekDay = MyDateUtils.getFirstWeekDayByMonth(month);
        currentDay = MyDateUtils.getCurrentDay(month);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public CalendarItemAdapter(Context context, int[] dayList, String month, int positionClicked) {
        this.context = context;
        this.dayList = dayList;
        fristWeekDay = MyDateUtils.getFirstWeekDayByMonth(month);
        currentDay = MyDateUtils.getCurrentDay(month);
        this.positionClicked = positionClicked;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public CalendarItemAdapter(Context context, int[] dayList
            , int fristWeekDay, int currentDay, int positionClicked) {
        this.context = context;
        this.dayList = dayList;
        this.fristWeekDay = fristWeekDay;
        this.currentDay = currentDay;
        this.positionClicked = positionClicked;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.item_calendar_old, parent, false);
        return new CalendarItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (position < fristWeekDay) {
            holder.layoutRoot.setVisibility(View.INVISIBLE);
            return;
        }

        holder.layoutRoot.setVisibility(View.VISIBLE);
        final int realPosition = position - fristWeekDay;
        holder.txtDate.setText("" + (1 + realPosition));
        if (realPosition == currentDay) {
            holder.txtDate.setText("今天");
        }
        holder.txtCourseNumber.setText(dayList[realPosition] + "节课");

        if (realPosition == positionClicked) {
            holder.layoutRoot.setBackgroundColor(context.getResources().getColor(R.color.red_origin));
        } else {
            holder.layoutRoot.setBackgroundColor(Color.TRANSPARENT);
        }

        if (realPosition < currentDay) {
            holder.txtDate.setTextColor(context.getResources().getColor(R.color.text_color_common));
            holder.txtCourseNumber.setTextColor(context.getResources().getColor(R.color.text_color_common));
        } else if (realPosition == currentDay || position % 7 == 0 || position % 7 == 6) {
            holder.txtDate.setTextColor(context.getResources().getColor(R.color.red_origin));
            holder.txtCourseNumber.setTextColor(context.getResources().getColor(R.color.sixsixsix));
        } else {
            holder.txtDate.setTextColor(context.getResources().getColor(R.color.black));
            holder.txtCourseNumber.setTextColor(context.getResources().getColor(R.color.sixsixsix));
        }

        if (realPosition == positionClicked) {
            holder.txtDate.setTextColor(context.getResources().getColor(R.color.black));
            holder.txtCourseNumber.setTextColor(context.getResources().getColor(R.color.black));
        }

        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(context.getResources().getColor(R.color.red_origin));
                if (listener != null) {
                    listener.onClick(realPosition);
                }
            }
        });
    }

    public void refreshSelcetedState(int positionClicked) {
        this.positionClicked = positionClicked;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dayList.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout layoutRoot;
        private TextView txtDate;
        private TextView txtCourseNumber;

        public ViewHolder(View view) {
            super(view);
            layoutRoot = (LinearLayout) view.findViewById(R.id.layout_root);
            txtDate = (TextView) view.findViewById(R.id.txt_date);
            txtCourseNumber = (TextView) view.findViewById(R.id.txt_course_number);
        }
    }

    public void setOnCalendarClickListener(OnCalendarClickListener listener) {
        this.listener = listener;
    }

    public interface OnCalendarClickListener {
        void onClick(int position);
    }
}
