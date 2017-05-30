package com.chichaykin.testapp;

import android.app.Application;
import android.util.Log;
import rx.android.schedulers.AndroidSchedulers;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

public class App extends Application {
  private static final String TAG = "App";
  //TODO use dagger to inject this field
  private EventProvider evenProvider = new EventProvider(
    Schedulers.computation());

  @Override public void onCreate() {
    super.onCreate();

    /**
     * a. On launch of the application a timer is started and emits a sequential integer
     * event every 100ms.
     * b. Each event is then mapped to a String value of the integer.
     * c. Add three listeners that receive the same event sequence and each display
     * (with some identifier) the events they receive.
     * d. Each event is delayed by 200ms before being displayed by the listeners.
     */
    ConnectableObservable<String> connectedObservable = evenProvider.observe().publish();
    connectedObservable.subscribe(s -> Log.d(TAG, "Listener 1: " + s),
        e -> Log.e(TAG, e.getMessage()));

    connectedObservable.subscribe(s -> Log.d(TAG, "Listener 2: " + s),
        e -> Log.e(TAG, e.getMessage()));

    connectedObservable.subscribe(s -> Log.d(TAG, "Listener 3: " + s),
        e -> Log.e(TAG, e.getMessage()));

    connectedObservable.connect(); //start emitting items
  }
}
