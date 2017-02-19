package com.leyuan.coach.http.subscriber;

import android.content.Context;
import android.util.Log;

import com.leyuan.coach.widget.SwitcherLayout;


/**
 * 用于第一次正常加载数据时的Http请求
 * 慎用:该Subscriber需与SwitcherLayout结合使用
 */
public abstract class CommonSubscriber<T> extends BaseSubscriber<T> {

    private SwitcherLayout switcherLayout;

    public CommonSubscriber(SwitcherLayout switcherLayout, Context context) {
        super(context);
        this.switcherLayout = switcherLayout;

    }

    /**
     * 订阅开始时调用显示加载中界面
     */
    @Override
    public void onStart() {
        switcherLayout.showLoadingLayout();
    }


    /**
     * 显示错误提示界面
     * @param e 异常信息
     */
    @Override
    public void onError(Throwable e) {
        switcherLayout.showExceptionLayout();
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     * 通过判断界面来实现显示正常界面还是空值界面的逻辑
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public abstract void onNext(T t);

    @Override
    public void onCompleted() {

    }

}