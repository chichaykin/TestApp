package com.chichaykin.testapp;

import io.reactivex.Observable;
import io.reactivex.Observer;
import java.util.concurrent.TimeUnit;

/**
 * Emits events every 100ms.
 */

public class EventProvider {

  Observable<String> start() {
    return Observable.interval(100, TimeUnit.MILLISECONDS)
      .map(l -> Long.toString(l));
  }
}
