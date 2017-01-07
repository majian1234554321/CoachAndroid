package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.coach.R;
import com.leyuan.coach.bean.CampaignBean;
import com.leyuan.coach.page.activity.train.TrainDetailActivity;

import java.util.ArrayList;
import java.util.List;



/**
 * 活动列表适配器
 * Created by song on 2016/8/19.
 */
public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.ViewHolder>{
    private Context context;
    private List<CampaignBean> data = new ArrayList<>();

    public CampaignAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CampaignBean> data) {
        this.data = data;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_campaign,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CampaignBean bean = data.get(position);
        holder.cover.setImageURI(bean.getCamImg());
        holder.address.setText(bean.getAreaName());
        holder.date.setText(bean.getStartDate());
        holder.time.setText(bean.getStartTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainDetailActivity.start(context,bean.getCampaignId());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView cover;
        public TextView address;
        public TextView date;
        public TextView time;

        public ViewHolder (View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_campaign_cover);
            address = (TextView)itemView.findViewById(R.id.tv_campaign_address);
            date = (TextView) itemView.findViewById(R.id.tv_campaign_date);
            time = (TextView)itemView.findViewById(R.id.tv_campaign_time);
        }
    }
}