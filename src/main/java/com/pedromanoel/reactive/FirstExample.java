package com.pedromanoel.reactive;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FirstExample {

    public void run() {
        printStart();

        List<String> list = Arrays.asList("One", "Two", "Three", "Four", "Five");
        printIterating(list);
        printReacting(list);

        printFinish();
    }

    private void printStart() {
        System.out.println("# First example\n");
    }

    private void printFinish() {
        System.out.println("## Finished first example\n");
    }

    private void printReacting(List<String> list) {
        System.out.println("## Print using observer");

        // fromIterable runs on current thread (no specific Scheduler is used)
        Observable<String> observable = Observable.fromIterable(list);

        observable.subscribe(element -> {
            System.out.println(element);
        });

    }

    private void printIterating(List<String> list) {
        System.out.println("## Print using iterator");
        Iterator<String> it = list.iterator();

        while(it.hasNext()) {
            String element = it.next();

            System.out.println(element);
        }
    }
}
