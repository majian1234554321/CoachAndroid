package com.leyuan.coach.page.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.coach.R;
import com.leyuan.coach.bean.AppointUserBean;

import java.util.ArrayList;
import java.util.List;




public class ApplicantAdapter extends RecyclerView.Adapter<ApplicantAdapter.ApplicantHolder>{
    private List<AppointUserBean> data = new ArrayList<>();

    public void setData(List<AppointUserBean> data) {
        if(data != null){
            this.data = data;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public ApplicantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_campaign_applicant,null);
        return new ApplicantHolder(view);
    }

    @Override
    public void onBindViewHolder(ApplicantHolder holder, int position) {
        AppointUserBean bean = data.get(position);
        holder.cover.setImageURI(bean.getAvatar());
    }

    class  ApplicantHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView cover;
        public ApplicantHolder(View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_user_cover);
        }
    }
}
