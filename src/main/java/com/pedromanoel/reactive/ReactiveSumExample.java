package com.pedromanoel.reactive;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReactiveSumExample extends Example {

    ReactiveSumExample() {
        super("Reactive Sum");
    }

    @Override
    protected void example() {
        ConnectableObservable<String> input = from(System.in);

        Observable<Double> a = varStream("a", input);
        Observable<Double> b = varStream("b", input);

        new ReactiveSum(a, b);

        input.connect();
    }

    private Observable<Double> varStream(String name, ConnectableObservable<String> input) {
        String assignment = "\\s*[:=]\\s*";
        String number = "-?\\d+(?:\\.\\d+)?";
        Pattern pattern = Pattern.compile("^" + name + assignment + "(?<value>" + number + ")$");

        return input
                .map(String::trim)
                .map(pattern::matcher)
                .filter(Matcher::matches)
                .map(matcher -> matcher.group("value"))
                .map(Double::parseDouble);
    }

    private ConnectableObservable<String> from(InputStream in) {
        return from(new BufferedReader(new InputStreamReader(in)));
    }

    private ConnectableObservable<String> from(BufferedReader reader) {
        return Observable.<String>create(emitter -> {
            String line;
            while (!emitter.isDisposed() && (line = reader.readLine()) != null) {
                if ("exit".equals(line)) {
                    break;
                }

                emitter.onNext(line);
            }

            if (emitter.isDisposed()) {
                return;
            }

            emitter.onComplete();
        }).publish();
    }

    private class ReactiveSum implements Observer<Double> {
        private double sum = 0;

        ReactiveSum(Observable<Double> a, Observable<Double> b) {
            Observable.combineLatest(a, b, Double::sum).subscribe(this);
        }

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Double sum) {
            this.sum = sum;
            System.out.println("update: a + b = " + this.sum);
        }

        @Override
        public void onError(Throwable e) {
            System.err.println("Got an error");
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            System.out.println("completed: a + b = " + sum);
        }
    }
}
