package com.leyuan.coach.page.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.coach.R;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/17.
 */
public class PopupWindowSignInAdapter extends RecyclerView.Adapter<PopupWindowSignInAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> array = new ArrayList<>();
    OnSignItemClickListener listener;

    public PopupWindowSignInAdapter(Context context, OnSignItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_popup_window_sign_in, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String month = array.get(position);
        holder.txtMonth.setText(month);
        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onMonthItemClicked(month);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (array == null)
            return 0;
        return array.size();
    }

    public void refreshData(ArrayList<String> months) {
        this.array = months;
        notifyDataSetChanged();

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layoutRoot;
        private TextView txtMonth;

        public ViewHolder(View view) {
            super(view);
            layoutRoot = (RelativeLayout) view.findViewById(R.id.layout_root);
            txtMonth = (TextView) view.findViewById(R.id.txt_month);
        }
    }


    public interface OnSignItemClickListener {
        void onMonthItemClicked(String month);
    }
}
