package com.example.hongcheng.common.util;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by hongcheng on 16/3/30.
 */
public class RxBus {
    public static RxBus mBus;

    private static PublishSubject<BusEvent> publishSubject = PublishSubject.create();
    private static Subject<BusEvent, BusEvent> mSubject = new SerializedSubject<BusEvent, BusEvent>(publishSubject);

    private RxBus(){

    }

    public static RxBus getInstance(){
        if(mBus == null){
            mBus = new RxBus();
        }
        return mBus;
    }

    public Observable<BusEvent> getObservable(){
        return mSubject;
    }

    public void send(BusEvent event){
        mSubject.onNext(event);
    }

    public boolean hasObservers(){
        return mSubject.hasObservers();
    }

    public static class BusEvent {

    }
}
