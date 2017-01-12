package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.commonlibrary.util.MyDateUtils;

/**
 * Created by user on 2016/12/26.
 */
public class CalendarAdapterOld extends RecyclerView.Adapter<CalendarAdapterOld.ViewHolder> {

    private int fristWeekDay;
    private Context context;
    private int[] dayList;
    private int currentDay = -1;
    private int positionClicked = -1;
    private OnCalendarClickListener listener;

    public CalendarAdapterOld(Context context, int[] dayList, String month) {
        this.context = context;
        this.dayList = dayList;
        fristWeekDay = MyDateUtils.getFirstWeekDayByMonth(month);
        currentDay = MyDateUtils.getCurrentDay(month);
    }

    public CalendarAdapterOld(Context context, int[] dayList, String month, int positionClicked) {
        this.context = context;
        this.dayList = dayList;
        fristWeekDay = MyDateUtils.getFirstWeekDayByMonth(month);
        this.positionClicked = positionClicked;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.item_calendar_old, null);
        return new ViewHolder(view);
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
        holder.txtCourseNumber.setText(dayList[realPosition] + "节课");

        if(position == positionClicked){
            holder.layoutRoot.setBackgroundColor(context.getResources().getColor(R.color.red_origin));
        }else{
            holder.layoutRoot.setBackgroundColor(Color.TRANSPARENT);
        }

        if (realPosition < currentDay) {
            holder.txtDate.setTextColor(context.getResources().getColor(R.color.text_color_common));
            holder.txtCourseNumber.setTextColor(context.getResources().getColor(R.color.text_color_common));
        } else if (realPosition == currentDay || position % 7 == 0 || position % 7 == 6) {
            holder.txtDate.setTextColor(context.getResources().getColor(R.color.red_origin));
            holder.txtCourseNumber.setTextColor(context.getResources().getColor(R.color.sixsixsix));
        }else{
            holder.txtDate.setTextColor(context.getResources().getColor(R.color.black));
            holder.txtCourseNumber.setTextColor(context.getResources().getColor(R.color.sixsixsix));
        }

        if(realPosition == positionClicked){
            holder.txtDate.setTextColor(context.getResources().getColor(R.color.black));
            holder.txtCourseNumber.setTextColor(context.getResources().getColor(R.color.black));
        }

        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(realPosition);
                }
            }
        });
    }

    public void refreshSelcetedState(int positionClicked ){
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
            txtDate = (TextView) view.findViewById(R.id.tv_title);
            txtCourseNumber = (TextView) view.findViewById(R.id.txt_course_number);
        }
    }

    public void setOnCalendarClickListener(OnCalendarClickListener listener){
        this.listener = listener;
    }

    public interface  OnCalendarClickListener{
        void onClick(int position);
    }
}
