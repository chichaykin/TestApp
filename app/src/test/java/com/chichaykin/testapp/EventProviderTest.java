package com.chichaykin.testapp;

import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import java.util.concurrent.TimeUnit;

public class EventProviderTest {
    private TestScheduler testScheduler;
    private TestSubscriber<String> testSubscriber;
    private Observable<String> observable;

    @Before
    public void init() {
        testScheduler = Schedulers.test();
        observable = new EventProvider(testScheduler).observe();
        testSubscriber = TestSubscriber.create();
    }

    @Test
    public void testNoItems() {
        observable.subscribe(testSubscriber);

        testSubscriber.assertNoValues();
        testSubscriber.assertNoErrors();
        testSubscriber.assertNotCompleted();
    }

    @Test
    public void testNoItemsOn100() {
        observable.subscribe(testSubscriber);

        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS);

        testSubscriber.assertNoValues();
        testSubscriber.assertNoErrors();
        testSubscriber.assertNotCompleted();
    }

    @Test
    public void testFirstItem() {
        observable.subscribe(testSubscriber);

        testScheduler.advanceTimeBy(200, TimeUnit.MILLISECONDS);

        testSubscriber.assertValueCount(1);
        testSubscriber.assertNotCompleted();
        testSubscriber.assertNoErrors();
    }

    @Test
    public void testTwoItems() {
        observable.subscribe(testSubscriber);

        testScheduler.advanceTimeBy(400, TimeUnit.MILLISECONDS);

        testSubscriber.assertValueCount(2);
        testSubscriber.assertNotCompleted();
        testSubscriber.assertNoErrors();
    }
}
