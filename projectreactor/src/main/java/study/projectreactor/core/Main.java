package study.projectreactor.core;

import java.time.Duration;
import java.util.List;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {

        // Flux<String> seq1 = Flux.just("foo", "bar", "foobar");
        // seq1.subscribe(s -> System.out.println(s));

        // List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        // Flux<String> seq2 = Flux.fromIterable(iterable);

        // Mono<String> noData = Mono.empty();

        // Mono<String> data = Mono.just("foo");

        // Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3);

        Flux<String> finiteFlux = Flux.generate(() -> 0, (state, sink) -> {
            sink.next("3 x " + state + " = " + 3 * state);
            if (state == 10)
                sink.complete();
            return state + 1;
        }, (state) -> System.out.println("cleanup state: " + state));


        Flux<Long> tickTokFlux = Flux.interval(Duration.ofMillis(1000));

        tickTokFlux.flatMap(i -> finiteFlux).subscribe(System.out::println);

        tickTokFlux.then().block();
//        flux.subscribe(new BaseSubscriber<String>() {
//            @Override
//            protected void hookOnSubscribe(Subscription subscription) {
//                logger.info("On Subscribe");
//                requestUnbounded();
//            }
//
//            @Override
//            protected void hookOnNext(String value) {
//                logger.info("On Next: " + value);
//            }
//
//            @Override
//            protected void hookFinally(SignalType type) {
//                logger.info("On Finally: " + type.toString());
//
//            }
//        });
//
//        flux.then().subscribe(new BaseSubscriber<Void>() {
//
//            @Override
//            protected void hookOnSubscribe(Subscription subscription) {
//                logger.info("On Subscribe");
//                requestUnbounded();
//            }
//
//            @Override
//            protected void hookOnNext(Void value) {
//                logger.info("On Next: " + value);
//            }
//
//            @Override
//            protected void hookFinally(SignalType type) {
//                logger.info("On Finally: " + type.toString());
//            }
//        });



    }
}

interface MyEventListener<T> {
    void onDataChunk(List<T> chunk);

    void processComplete();
}