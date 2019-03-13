package com.pedromanoel.reactive;

import io.reactivex.Observable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ObservableIterableTest {

    @Test
    public void iterator_observable_comparison() {
        List<String> list = asList("one", "two", "three", "four", "five");

        assertThat(getUsingIterator(list)).containsExactly("one", "two", "three", "four", "five");

        assertThat(getUsingObservable(list)).containsExactly("one", "two", "three", "four", "five");
    }

    private List<String> getUsingObservable(List<String> list) {
        List<String> result = new ArrayList<>();

        // observable calls add reacting to the items emitted
        Observable.fromIterable(list).blockingSubscribe(result::add);

        return result;
    }

    private List<String> getUsingIterator(List<String> list) {
        List<String> result = new ArrayList<>();

        // iterator calls add after next returns
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()) {
            result.add(iterator.next());
        }

        return result;
    }
}
