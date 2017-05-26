package com.chichaykin.testapp;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;
import timber.log.Timber;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class EventProviderTest {
    EventProvider eventProvider;
    private TestScheduler testScheduler;
    private Utils utils;

    @Before
    public void init() {
        testScheduler = Schedulers.test();
        utils = new Utils(testScheduler);
        eventProvider = new EventProvider(testScheduler, testScheduler, utils);
    }

    @Test
    public void testNoItems() {
        TestSubscriber<String> testSubscriber = TestSubscriber.create();
        eventProvider.observe().subscribe(testSubscriber);

        testSubscriber.assertNoValues();
        testSubscriber.assertNoErrors();
        testSubscriber.assertNotCompleted();
    }

    @Test
    public void testNoItemsOn100() {
        TestSubscriber<String> testSubscriber = TestSubscriber.create();
        eventProvider.observe().subscribe(testSubscriber);

        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS);

        testSubscriber.assertNoValues();
        testSubscriber.assertNoErrors();
        testSubscriber.assertNotCompleted();
    }

    @Test
    public void testFirstItem() {
        TestSubscriber<String> testSubscriber = TestSubscriber.create();
        eventProvider.observe().subscribe(testSubscriber);

        testScheduler.advanceTimeBy(200, TimeUnit.MILLISECONDS);

        testSubscriber.assertValueCount(1);
        testSubscriber.assertNotCompleted();
        testSubscriber.assertNoErrors();
    }

    @Test
    public void testTwoItems() {
        TestSubscriber<Object> testSubscriber = TestSubscriber.create();
        SubscriberStub stub = new SubscriberStub();
        eventProvider.observe().compose(utils.loggy("interval: ")).subscribe(stub);
        utils.log("advanceTimeBy");
        testScheduler.advanceTimeBy(400, TimeUnit.MILLISECONDS);
        //testScheduler.advanceTimeBy(200, TimeUnit.MILLISECONDS);
        testScheduler.triggerActions();
        utils.log("end advanceTimeBy");
        assertEquals(2, stub.called);
//        testSubscriber.assertValueCount(2);
//        testSubscriber.assertNotCompleted();
//        testSubscriber.assertNoErrors();
        utils.log("end test");
    }


    static class SubscriberStub<T> extends Subscriber<T> {

        public int called;


        @Override
        public void onCompleted() {
            System.out.println("onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            System.out.println("onError");
        }

        @Override
        public void onNext(T s) {
            called++;
        }
    }



}
