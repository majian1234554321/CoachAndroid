package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.coach.R;
import com.leyuan.coach.bean.AppointmentBean;
import com.leyuan.coach.page.activity.mine.AppointmentDetailActivity;
import com.leyuan.coach.utils.FormatUtil;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;


public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder> {
    private static final String UN_PAID = "0";          //待支付
    private static final String UN_JOIN= "1";           //待参加
    private static final String CLOSE = "2";            //已关闭
    private static final String JOINED = "3";           //已参加
    private static final String REFUNDING = "4";        //退款中
    private static final String REFUNDED = "5";         //已退款

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
        holder.timer.start(Long.parseLong(bean.getLittleTime()) * 1000);

        //与订单状态有关
        if (TextUtils.isEmpty(bean.getStatus())) return;
        switch (bean.getStatus()) {
            case UN_PAID:           //待付款
                holder.state.setText(context.getString(R.string.un_paid));
                holder.date.setVisibility(View.GONE);
                holder.timerLayout.setVisibility(View.VISIBLE);
                holder.tvCancel.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.VISIBLE);
                holder.tvDelete.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                break;
            case UN_JOIN:           //待参加
                holder.state.setText(context.getString(R.string.appointment_un_joined));
                holder.date.setText(String.format(context.getString(R.string.order_train_time),
                        bean.getStartDate(),bean.getStartTime()));
                holder.date.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.tvCancel.setVisibility(FormatUtil.parseDouble(bean.getPayAmount()) == 0 ?
                        View.VISIBLE : View.GONE);
                holder.tvConfirm.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.GONE);
                break;
            case JOINED:            //已参加
                holder.state.setText(context.getString(R.string.appointment_joined));
                holder.date.setText(String.format(context.getString(R.string.order_train_time),
                        bean.getStartDate(),bean.getStartTime()));
                holder.date.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                break;
            case CLOSE:             //已关闭
                holder.state.setText(context.getString(R.string.order_close));
                holder.date.setText(String.format(context.getString(R.string.order_train_time),
                        bean.getStartDate(),bean.getStartTime()));
                holder.date.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                break;
            case REFUNDING:           //退款中
                holder.state.setText(context.getString(R.string.order_refunding));
                holder.date.setText(String.format(context.getString(R.string.order_train_time),
                        bean.getStartDate(),bean.getStartTime()));
                holder.date.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                break;
            case REFUNDED:             //已退款
                holder.state.setText(context.getString(R.string.order_refunded));
                holder.date.setText(String.format(context.getString(R.string.order_train_time),
                        bean.getStartDate(),bean.getStartTime()));
                holder.date.setVisibility(View.VISIBLE);
                holder.timerLayout.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvPay.setVisibility(View.GONE);
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvConfirm.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        holder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getString(R.string.un_join_confirm))
                        .setCancelable(true)
                        .setPositiveButton(context.getString(R.string.sure), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(orderHandleListener != null){
                                    orderHandleListener.onCancelJoin(bean.getOrderId());
                                }
                            }
                        })
                        .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderHandleListener != null) {
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

        holder.timer.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                if(orderHandleListener != null){
                    orderHandleListener.onRefreshOrderStatus();
                }
            }
        });
    }


    class AppointmentHolder extends RecyclerView.ViewHolder {
        TextView state;
        TextView date;
        LinearLayout timerLayout;
        CountdownView  timer;
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
            date = (TextView) itemView.findViewById(R.id.tv_id_or_time);
            timerLayout = (LinearLayout) itemView.findViewById(R.id.ll_timer);
            timer = (CountdownView) itemView.findViewById(R.id.timer);
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
        void onRefreshOrderStatus();
    }

    public static class  SimpleOrderHandleListener implements OrderHandleListener{

        @Override
        public void onDeleteOrder(String orderId) {

        }

        @Override
        public void onConfirmJoin(String orderId) {

        }

        @Override
        public void onCancelJoin(String orderId) {

        }

        @Override
        public void onRefreshOrderStatus() {

        }
    }
}
