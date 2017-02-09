package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.WithdrawDetail;

import java.util.ArrayList;

/**
 * Created by user on 2016/12/29.
 */
public class WithDrawRecordAdapter extends RecyclerView.Adapter<WithDrawRecordAdapter.ViewHolder> {
    private Context context;
    private ArrayList<WithdrawDetail> withdrawDetails;
    private OnItemClickListener listener;

    public WithDrawRecordAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void refreshData(ArrayList<WithdrawDetail> withdrawDetails) {
        this.withdrawDetails = withdrawDetails;
        notifyDataSetChanged();
    }

    public void addMoreData(ArrayList<WithdrawDetail> withdrawDetails) {
        this.withdrawDetails.addAll(withdrawDetails);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_withdraw_record, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final WithdrawDetail detail = withdrawDetails.get(position);

        switch (detail.getStatus()) {
            case 0:
                holder.txtType.setText("待处理");
                break;
            case 1:
                holder.txtType.setText("提现中");
                break;
            case 2:
                holder.txtType.setText("提现成功");
                break;
            case 3:
                holder.txtType.setText("提现失败");
                break;

        }
        holder.txtDate.setText(detail.getWithdrawDate());
        holder.txtMoney.setText(String.valueOf(detail.getCash()));

        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(detail);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (withdrawDetails == null)
            return 0;
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
            txtDate = (TextView) view.findViewById(R.id.tv_title);
            txtMoney = (TextView) view.findViewById(R.id.txt_money);
        }
    }

    public interface OnItemClickListener {


        void onClick(WithdrawDetail detail);
    }
}
