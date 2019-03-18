package com.pedromanoel.reactive;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

class ReactiveSum implements Observer<Double> {
    private double sum = 0;

    ReactiveSum(Observable<Double> a, Observable<Double> b) {
        Observable.combineLatest(a, b, Double::sum).subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        System.out.println("Reactive Sum: Type 'a: <number>' and 'b: " +
                "<number>'a: to try it.'");
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
