package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.RecentEarningResult;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/29.
 */
public class EarningDetailAdapter extends RecyclerView.Adapter<EarningDetailAdapter.ViewHolder> {
    private Context context;
    private ArrayList<RecentEarningResult> withdrawDetails = new ArrayList<>();
    private OnItemClickListener listener;

    public EarningDetailAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void refreshData(ArrayList<RecentEarningResult> withdrawDetails) {
        this.withdrawDetails.clear();
        this.withdrawDetails.addAll(withdrawDetails);
        notifyDataSetChanged();
    }

    public void addMoreData(ArrayList<RecentEarningResult> withdrawDetails) {
        this.withdrawDetails.addAll(withdrawDetails);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_more_earning_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        RecentEarningResult detail = withdrawDetails.get(position);
        final String months = detail.getMonths();

        holder.txtType.setText(detail.getMonths() + "");
        holder.txtDate.setText(detail.getIncomeDate() + "");

        String symbol = detail.getTotalIncome() > 0 ? "" : "";
        holder.txtMoney.setText(symbol + detail.getTotalIncome());

        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(months);
            }
        });


    }

    @Override
    public int getItemCount() {
        return withdrawDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layoutRoot;
        private TextView txtType;
        private TextView txtDate;
        private TextView txtMoney;

        public ViewHolder(View view) {
            super(view);
            layoutRoot = (RelativeLayout) view.findViewById(R.id.layout_root);
            txtType = (TextView) view.findViewById(R.id.txt_type);
            txtDate = (TextView) view.findViewById(R.id.txt_date);
            txtMoney = (TextView) view.findViewById(R.id.txt_money);
        }
    }

    public interface OnItemClickListener {
        void onClick(String detail);
    }
}
