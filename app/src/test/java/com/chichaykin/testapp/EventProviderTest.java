package com.chichaykin.testapp;

import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

public class EventProviderTest {
  EventProvider eventProvider;
  private TestScheduler testScheduler;

  @Before public void init() {
    testScheduler = Schedulers.test();
    eventProvider = new EventProvider(testScheduler, testScheduler);
  }

  @Test public void test() {
    TestSubscriber<String> testSubscriber = TestSubscriber.create();
    eventProvider.observe().subscribe(testSubscriber);

    testSubscriber.assertNoValues();
    testSubscriber.assertNoErrors();
  }

  @Test public void test2() {
    TestSubscriber<String> testSubscriber = TestSubscriber.create();
    eventProvider.observe().subscribe(testSubscriber);

    testScheduler.advanceTimeBy(200, TimeUnit.MILLISECONDS);
    testSubscriber.assertNoValues();
    testSubscriber.assertNoErrors();
  }

}
