package com.pedromanoel.reactive.combine;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.PublishSubject;
import org.junit.Test;

public class CombinationTest {

    @Test
    public void zip() {
        PublishSubject<Integer> o1 = PublishSubject.create();
        PublishSubject<Integer> o2 = PublishSubject.create();

        TestObserver<Integer> test = Observable.zip(o1, o2, Integer::sum).test();

        test.assertSubscribed();
        test.assertEmpty();

        o1.onNext(10);
        o1.onNext(20);
        o1.onNext(30);

        test.assertEmpty();

        o2.onNext(100);
        test.assertValues(110);

        o2.onNext(200);
        test.assertValues(110, 220);

        o2.onNext(300);
        test.assertValues(110, 220, 330);
    }
}
