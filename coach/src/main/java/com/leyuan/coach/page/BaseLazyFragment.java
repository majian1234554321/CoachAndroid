package com.leyuan.coach.page;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.coach.R;
import com.leyuan.coach.utils.LogUtil;

public abstract class BaseLazyFragment extends Fragment {
    private java.lang.String TAG = this.getClass().getSimpleName();
    protected int pageSize = 5;
    protected View rootView;
    public Context context;
    protected boolean isVisible;
    private boolean isPrepared;
    private boolean haveLazyLoad;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            if (!haveLazyLoad) {
                lazyLoad();
            }
        } else {
            isVisible = false;
            haveLazyLoad = false;
            onInvisible();
        }
        LogUtil.i(TAG, "setUserVisibleHint visible = " + isVisible);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        setHasOptionsMenu(true);
        LogUtil.i(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = initView();
        }
        LogUtil.i(TAG, "onCreateView");
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        if (!haveLazyLoad) {
            lazyLoad();
        }
        LogUtil.i(TAG, "onActivityCreated");
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        haveLazyLoad = true;
        initData();
        LogUtil.i(TAG, "lazyLoad initData()");
    }

    protected void onInvisible() {

    }

    public abstract View initView();

    public abstract void initData();

    protected void setColorSchemeResources(SwipeRefreshLayout refreshLayout) {
        refreshLayout.setColorSchemeResources(R.color.black, R.color.red, R.color.orange, R.color.gray);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(TAG, "onResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "onDestroy");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.i(TAG, "onHiddenChanged " + hidden);
    }
}
