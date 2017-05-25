package com.chichaykin.testapp;

import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Emits events every 100ms and delay each item to 200ms
 */

public class EventProvider {

  Observable<String> observe() {
    return Observable.interval(100, TimeUnit.MILLISECONDS)
      .onBackpressureLatest()
      .map(l -> Long.toString(l))
      .concatMap(i -> Observable.just(i).delay(200, TimeUnit.MILLISECONDS))
      .observeOn(AndroidSchedulers.mainThread())
      .share();
  }
}
