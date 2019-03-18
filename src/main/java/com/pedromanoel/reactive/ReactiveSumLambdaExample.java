package com.pedromanoel.reactive;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReactiveSumLambdaExample extends Example {

    ReactiveSumLambdaExample() {
        super("Reactive Sum Lambda Edition");
    }

    @Override
    protected void example() {
        ConnectableObservable<String> input = from(System.in);

        Observable<Double> a = varStream("a", input);
        Observable<Double> b = varStream("b", input);

        reactiveSum(a, b);

        input.connect();
    }

    private void reactiveSum(Observable<Double> a, Observable<Double> b) {
        Consumer<Double> printSum = sum -> System.out.println("update: a + b = " + sum);
        Consumer<Throwable> printError = error -> {
            System.out.println("Got an error!");
            error.printStackTrace();
        };
        Action printExit = () -> System.out.println("Exiting...");

        Observable
                .combineLatest(a, b, Double::sum)
                .subscribe(printSum, printError, printExit);
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

}
