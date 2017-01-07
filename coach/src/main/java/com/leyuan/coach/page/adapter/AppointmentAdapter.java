package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.coach.R;
import com.leyuan.coach.bean.AppointmentBean;
import com.leyuan.coach.page.activity.mine.AppointmentDetailActivity;
import com.leyuan.coach.page.activity.train.TrainDetailActivity;

import java.util.ArrayList;
import java.util.List;


public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder> {
    private static final String UN_PAID = "0";          //待付款
    private static final String UN_JOIN= "1";           //待参加
    private static final String CLOSE = "2";            //已关闭
    private static final String JOINED = "3";           //已参加


    private Context context;
    private List<AppointmentBean> data = new ArrayList<>();
    private OrderHandleListener orderHandleListener;

    public AppointmentAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<AppointmentBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public AppointmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_appointment,parent,false);
        return new AppointmentHolder(view);
    }

    @Override
    public void onBindViewHolder(AppointmentHolder holder, int position) {
        final AppointmentBean bean = data.get(position);

        //与订单状态无关
        holder.cover.setImageURI(bean.getCampImg());
        holder.name.setText(bean.getTitle());
        holder.address.setText(bean.getBrandName());
        holder.price.setText(String.format(context.getString(R.string.rmb_price),bean.getPayAmount()));

        //与订单状态有关
        if (TextUtils.isEmpty(bean.getStatus())) return;
        switch (bean.getStatus()) {
            case UN_PAID:           //待付款
                holder.state.setText(context.getString(R.string.un_paid));
                holder.timeTip.setText(String.format(context.getString(R.string.order_pay_time),
                        bean.getLittleTime()));
                holder.tvCancel.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.VISIBLE);
                holder.tvDelete.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                holder.state.setTextColor(context.getResources().getColor(R.color.main_red));
                holder.timeTip.setTextColor(context.getResources().getColor(R.color.main_red));
                break;
            case UN_JOIN:           //待参加
                holder.state.setText(context.getString(R.string.appointment_un_joined));
                holder.timeTip.setText(String.format(context.getString(R.string.order_train_time),
                        bean.getStartDate(),bean.getStartTime()));
                holder.tvCancel.setVisibility(View.VISIBLE);
                holder.tvConfirm.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.GONE);
                holder.state.setTextColor(context.getResources().getColor(R.color.c3));
                holder.timeTip.setTextColor(context.getResources().getColor(R.color.c9));
                break;
            case JOINED:            //已参加
                holder.state.setText(context.getString(R.string.appointment_joined));
                holder.timeTip.setText(String.format(context.getString(R.string.order_train_time),
                        bean.getStartDate(),bean.getStartTime()));
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                holder.state.setTextColor(context.getResources().getColor(R.color.c3));
                holder.timeTip.setTextColor(context.getResources().getColor(R.color.c9));
                break;
            case CLOSE:             //已关闭
                holder.state.setText(context.getString(R.string.order_close));
                holder.timeTip.setText(String.format(context.getString(R.string.order_train_time),
                        bean.getStartDate(),bean.getStartTime()));
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                holder.state.setTextColor(context.getResources().getColor(R.color.c3));
                holder.timeTip.setTextColor(context.getResources().getColor(R.color.c9));
                break;
            default:
                break;
        }

        holder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderHandleListener != null){
                    orderHandleListener.onCancelJoin(bean.getOrderId());
                }
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderHandleListener != null){
                    orderHandleListener.onDeleteOrder(bean.getOrderId());
                }
            }
        });

        holder.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderHandleListener != null){
                    orderHandleListener.onConfirmJoin(bean.getOrderId());
                }
            }
        });

        holder.trainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainDetailActivity.start(context,bean.getCampaignId());
            }
        });

        holder.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointmentDetailActivity.start(context,bean.getOrderId());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointmentDetailActivity.start(context,bean.getOrderId());
            }
        });
    }


    class AppointmentHolder extends RecyclerView.ViewHolder {
        TextView state;
        TextView timeTip;
        RelativeLayout trainLayout;
        SimpleDraweeView cover;
        TextView name;
        TextView address;
        TextView price;
        TextView tvCancel;
        TextView tvConfirm;
        TextView tvDelete;
        TextView tvPay;

        public AppointmentHolder(View itemView) {
            super(itemView);

            state = (TextView) itemView.findViewById(R.id.tv_state);
            timeTip = (TextView) itemView.findViewById(R.id.tv_id_or_time);
            trainLayout = (RelativeLayout) itemView.findViewById(R.id.rl_train);
            cover = (SimpleDraweeView) itemView.findViewById(R.id.dv_goods_cover);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            address = (TextView) itemView.findViewById(R.id.tv_address);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            tvCancel = (TextView) itemView.findViewById(R.id.tv_cancel);
            tvConfirm = (TextView) itemView.findViewById(R.id.tv_confirm);
            tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            tvPay = (TextView) itemView.findViewById(R.id.tv_pay);
        }
    }

    public void setOrderHandleListener(OrderHandleListener orderHandleListener) {
        this.orderHandleListener = orderHandleListener;
    }

    public interface OrderHandleListener{
        void onDeleteOrder(String orderId);
        void onConfirmJoin(String orderId);
        void onCancelJoin(String orderId);
    }
}
