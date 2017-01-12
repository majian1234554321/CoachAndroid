package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.RecentEarningResult;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/10.
 */
public class OtherEarningDetailAdapter extends RecyclerView.Adapter<OtherEarningDetailAdapter.Viewholder> {

    private Context context;
    private ArrayList<RecentEarningResult.OtherEarning> otherList;
    private double welfareIncome;


    public OtherEarningDetailAdapter(Context context, ArrayList<RecentEarningResult.OtherEarning> otherList, double welfareIncome) {
        this.context = context;
        this.otherList = otherList;
        this.welfareIncome = welfareIncome;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course_earning_detail, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        if (position == otherList.size()) {
            holder.txtLeft.setTextColor(context.getResources().getColor(R.color.red_origin));
            holder.txtRight.setTextColor(context.getResources().getColor(R.color.red_origin));
            holder.txtLeft.setText(context.getString(R.string.total_colon));
            String symbol = welfareIncome > 0 ? "+" : "-";
            holder.txtRight.setText(symbol + welfareIncome);
        } else {
            RecentEarningResult.OtherEarning otherEarning = otherList.get(position);
            holder.txtLeft.setTextColor(context.getResources().getColor(R.color.black_gray));
            holder.txtRight.setTextColor(context.getResources().getColor(R.color.black_gray));
            holder.txtLeft.setText(otherEarning.getTitle());
            holder.txtRight.setText(otherEarning.getAmount());
        }
    }

    @Override
    public int getItemCount() {
        return otherList.size() + 1;
    }

    class Viewholder extends RecyclerView.ViewHolder {

        private TextView txtLeft;
        private TextView txtRight;

        public Viewholder(View view) {
            super(view);
            txtLeft = (TextView) view.findViewById(R.id.txt_left);
            txtRight = (TextView) view.findViewById(R.id.txt_right);
        }

    }
}
