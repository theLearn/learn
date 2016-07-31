package com.example.hongcheng.thelearn.presenter.impl;

import com.example.hongcheng.common.util.RxUtils;
import com.example.hongcheng.thelearn.presenter.BasePresenter;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by hongcheng on 16/3/30.
 */
public class BasePresenterImpl implements BasePresenter {
    protected CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    public void onCreate() {
        mSubscriptions = RxUtils.getCompositeSubscription(mSubscriptions);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        RxUtils.unsubscribe(mSubscriptions);
    }
}
