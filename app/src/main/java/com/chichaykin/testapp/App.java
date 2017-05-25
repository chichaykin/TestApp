package com.chichaykin.testapp;

import android.app.Application;
import android.util.Log;

public class App extends Application {
  private static final String TAG = "App";
  //TODO use dagger to inject this field
  private EventProvider evenProvider = new EventProvider();

  @Override public void onCreate() {
    super.onCreate();

    //evenProvider.observe()
    //  .subscribe(s -> {
    //      Log.d(TAG, "Listener 1: " + s);
    //    },
    //    e -> Log.e(TAG, e.getMessage()));
    //
    //evenProvider.observe()
    //  .subscribe(s -> Log.d(TAG, "Listener 2: " + s),
    //    e -> Log.e(TAG, e.getMessage()));
    //
    //evenProvider.observe()
    //  .subscribe(s -> Log.d(TAG, "Listener 3: " + s),
    //    e -> Log.e(TAG, e.getMessage()));
  }
}
