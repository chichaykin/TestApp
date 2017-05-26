package com.chichaykin.testapp;

import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Scheduler;

/**
 * Emits events every 100ms and delay each item to 200ms
 */

public class EventProvider {

  private Scheduler uiScheduler;
  private Scheduler computationScheduler;

  public EventProvider(Scheduler uiScheduler, Scheduler computationScheduler) {
    this.uiScheduler = uiScheduler;
    this.computationScheduler = computationScheduler;
  }

  Observable<String> observe() {
    return Observable.interval(0, 100, TimeUnit.MILLISECONDS, computationScheduler)
      .onBackpressureLatest()
      .map(l -> Long.toString(l))
      .concatMap(i -> Observable.just(i).delay(200, TimeUnit.MILLISECONDS))
      .observeOn(uiScheduler);
  }
}
