package com.pedromanoel.reactive;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.junit.Test;

import static java.util.Arrays.asList;

public class ObservableStaticFactoriesTest {

    @Test
    public void fromIterable() {

        TestObserver<String> test = Observable
                .fromIterable(asList("blue", "red", "green"))
                .test();

        test.assertResult("blue", "red", "green");
        test.assertComplete();
    }

    @Test
    public void fromArray() {

        TestObserver<String> test = Observable
                .fromArray("blue", "red", "green")
                .test();

        test.assertResult("blue", "red", "green");
        test.assertComplete();
    }

    @Test
    public void just() {
        TestObserver<Character> test = Observable
                .just('A', 'B', 'C')
                .test();

        test.assertResult('A', 'B', 'C');
    }
}
