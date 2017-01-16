package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.coach.R;
import com.leyuan.coach.bean.MessageInfo;

import java.util.ArrayList;


/**
 * Created by user on 2016/12/29.
 */
public class MessageCenterAdapter extends RecyclerView.Adapter<MessageCenterAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MessageInfo> messageInfos = new ArrayList<>();
    private OnItemClickListener listener;

    public MessageCenterAdapter(Context context) {
        this.context = context;
    }

    public void refreshData(ArrayList<MessageInfo> messageInfos) {
        this.messageInfos.clear();
        this.messageInfos.addAll(messageInfos);
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_message_center, parent, false);
        View.inflate(context, R.layout.item_message_center, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MessageInfo messageInfo = messageInfos.get(position);

        if (messageInfo.getMsgStatus() == MessageInfo.MsgStatus.UNREAD) {
            holder.imgMessage.setImageResource(R.drawable.icon_massage_remind);
        } else {
            holder.imgMessage.setImageResource(R.drawable.icon_massage);
        }
        holder.txtMessageDate.setText(messageInfo.getMsgDate());
        holder.txtMessageTime.setText(messageInfo.getMsgTime());
        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onMessageClick(messageInfo.getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return messageInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout layoutRoot;
        private ImageView imgMessage;
        private TextView txtMessageType;
        private TextView txtMessageDesc;
        private TextView txtMessageDate;
        private TextView txtMessageTime;
        private ImageView imgDivider;

        public ViewHolder(View view) {
            super(view);

            layoutRoot = (RelativeLayout) view.findViewById(R.id.layout_root);
            imgMessage = (ImageView) view.findViewById(R.id.img_message);
            txtMessageType = (TextView) view.findViewById(R.id.txt_message_type);
            txtMessageDesc = (TextView) view.findViewById(R.id.txt_message_desc);
            txtMessageDate = (TextView) view.findViewById(R.id.txt_message_date);
            txtMessageTime = (TextView) view.findViewById(R.id.txt_message_time);
            imgDivider = (ImageView) view.findViewById(R.id.img_divider);

        }
    }

    public interface OnItemClickListener {

        void onMessageClick(int messageId);
    }
}
