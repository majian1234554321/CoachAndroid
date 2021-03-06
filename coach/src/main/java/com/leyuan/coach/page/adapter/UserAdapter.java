package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.coach.R;
import com.leyuan.coach.bean.AppointUserBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 爱动同道适配器
 * Created by song on 2016/8/29.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>{
    private Context context;
    private List<AppointUserBean> data = new ArrayList<>();

    public UserAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<AppointUserBean> data) {
        if(data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        AppointUserBean bean = data.get(position);
        holder.cover.setImageURI(bean.getAvatar());
        holder.nickname.setText(bean.getName());
    }

    class UserHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView cover;
        TextView nickname;

        public UserHolder(View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_user_cover);
            nickname = (TextView)itemView.findViewById(R.id.tv_nickname);
        }
    }
}
