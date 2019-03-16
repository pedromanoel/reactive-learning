package com.pedromanoel.reactive;

import io.reactivex.Observable;

import java.util.List;

import static java.util.Arrays.asList;

public class OnErrorAndOnCompleted extends Example{

    OnErrorAndOnCompleted() {
        super("OnError and OnCompleted");
    }

    @Override
    protected void example() {
        List<String> listWithError = asList("1", "2", "three", "4", "5");

        Observable
                .fromIterable(listWithError)
                .subscribe(
                        this::onNext,
                        this::onError
                );

        List<String> list = asList("1", "2", "3", "4", "5");
        Observable
                .fromIterable(list)
                .subscribe(
                        this::onNext,
                        e -> {},
                        this::onCompleted
                );
    }

    private void onCompleted() {
        System.out.println("We've finished");
    }

    private void onError(Throwable e) {
        System.out.println("Error on observer: " + e);
    }

    private void onNext(String element) {
        int number = Integer.parseInt(element);
        System.out.println(number);
    }
}
