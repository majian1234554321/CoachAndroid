package com.leyuan.coach.widget.popupwindow;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.coach.R;
import com.leyuan.coach.page.adapter.PopupWindowSignInAdapter;

import java.util.ArrayList;

/**
 * Created by user on 2017/1/17.
 */
public class PopupWindowSignInMonth extends BaseCommonPopupWindow {

    private PopupWindowSignInAdapter adapter;

    public PopupWindowSignInMonth(Activity context, PopupWindowSignInAdapter.OnSignItemClickListener listener) {
        super(context);
        RecyclerView recyclerView = (RecyclerView) getContentView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new PopupWindowSignInAdapter(context, listener);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected View createView() {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_window_sign_in_record, null, false);
        return view;
    }

    @Override
    protected void initData() {
        setAnimationStyle(R.style.popupDownStyle);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        getContentView().findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        getContentView().findViewById(R.id.bt_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setData(ArrayList<String> months) {
        adapter.refreshData(months);
    }

}
