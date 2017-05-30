package com.chichaykin.testapp;

import rx.Observable;
import rx.Scheduler;

import java.util.concurrent.TimeUnit;

/**
 * Emits events every 100ms and delay each item to 200ms
 */

public class EventProvider {

  private Scheduler computationScheduler;

  public EventProvider(Scheduler computationScheduler) {
    this.computationScheduler = computationScheduler;
  }

  Observable<String> observe() {
    return Observable.interval(0, 100, TimeUnit.MILLISECONDS, computationScheduler)
      .onBackpressureLatest()
      .map(l -> Long.toString(l))
      .concatMap(i -> Observable.just(i).delay(200, TimeUnit.MILLISECONDS, computationScheduler));
  }
}
