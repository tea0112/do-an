package com.thai.doan;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

class RandomTest {
    @Test
    void reactive() {
        List<Integer> elements = new ArrayList<>();
        Flux.just(1, 2, 3, 4)
            .log()
            .map(i -> i * 2)
            .subscribe(new Subscriber<Integer>() {
                @Override
                public void onSubscribe(Subscription subscription) {
                    subscription.request(Long.MAX_VALUE);
                }

                @Override
                public void onNext(Integer integer) {
                    elements.add(integer);
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onComplete() {
                }
            });

        List<String> elm = new ArrayList<>();
        Flux.just(1, 2, 3, 4)
            .log()
            .map(i -> i * 2)
            .zipWith(Flux.range(0, Integer.MAX_VALUE),
                (one, two) -> String.format("First Flux: %d, Second Flux: %d", one, two))
            .subscribeOn(Schedulers.parallel())
            .subscribe(elm::add);

        assertThat(elm).containsExactly(
            "First Flux: 2, Second Flux: 0",
            "First Flux: 4, Second Flux: 1",
            "First Flux: 6, Second Flux: 2",
            "First Flux: 8, Second Flux: 3");
//        assertThat(elements).containsExactly(1, 2, 3, 4);
    }
}