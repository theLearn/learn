package com.example.hongcheng.common.util;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by hongcheng on 16/3/30.
 */
public class RxUtils {
    private RxUtils(){

    }

    public static void unsubscribe(Subscription subscription){
        if(subscription != null){
            subscription.unsubscribe();
        }
    }

    public static CompositeSubscription getCompositeSubscription(CompositeSubscription subscription){
        if(subscription == null || subscription.isUnsubscribed()){
            return new CompositeSubscription();
        }
        return subscription;
    }
}
