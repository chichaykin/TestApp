package com.chichaykin.testapp;

import rx.Observable;
import rx.schedulers.TestScheduler;

public class Utils {

    private TestScheduler scheduler;
    private long startTimeStamp;

    public Utils(TestScheduler scheduler) {
        this.scheduler = scheduler;
        startTimeStamp = scheduler.now();
    }

    public void log(String msg, Object... args) {
        long diff = (scheduler.now() - startTimeStamp);
        String message = String.format(msg, args);
        String threadInfo = Thread.currentThread().toString();
        System.out.printf("%4d %40s %s%n", diff, threadInfo, message);
    }


    public Observable.Transformer<? super Object, ?> loggy(String name) {
        return objectObservable -> objectObservable
                .doOnSubscribe(() -> log("%s onSubscribe", name))
                .doOnCompleted(() -> log("%s onCompleted", name))
                .doOnUnsubscribe(() -> log("%s onUnsubscribe", name))
                .doOnNext(o -> log("%s onNext: %s", name, o));
    }

}
