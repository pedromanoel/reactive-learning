package com.pedromanoel.reactive;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.junit.Test;

import static java.util.Arrays.asList;

public class ObservableStaticFactories {

    @Test
    public void fromIterable() {
        Observable<String> observable = Observable.fromIterable(
                asList("blue", "red", "green"));

        TestObserver<String> test = observable.test();

        test.assertResult("blue", "red", "green");
    }

    @Test
    public void fromArray() {
        Observable<String> observable = Observable.fromArray("blue", "red", "green");

        TestObserver<String> test = observable.test();

        test.assertResult("blue", "red", "green");
    }
}
