package com.leyuan.coach.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.leyuan.coach.R;

import java.util.LinkedList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    protected  int pageSize = 5;
    public final static List<BaseActivity> mActivities = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (mActivities) {
            mActivities.add(this);
        }
        Log.w("currActivity : ",getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
    }

    public void exitApp(){
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
    }

    /**
     * 设置下拉刷新颜色
     */
    protected void setColorSchemeResources(SwipeRefreshLayout refreshLayout){
        refreshLayout.setColorSchemeResources(R.color.black, R.color.red, R.color.orange,R.color.gray);
    }

}
