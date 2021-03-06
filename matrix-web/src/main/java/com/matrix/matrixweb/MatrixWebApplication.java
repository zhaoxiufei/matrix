package com.matrix.matrixweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SpringBootApplication
@RestController
public class MatrixWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatrixWebApplication.class, args);
    }

    @GetMapping("/")
    public String welcome() {
        return "Hello World:";
    }

    @GetMapping("/hello")
    public Mono<String> hello(String name) {
        return Mono.create(new Consumer<MonoSink<String>>() {
            @Override
            public void accept(MonoSink<String> stringMonoSink) {
                stringMonoSink.success("hello, " + name);
            }
        });
    }

    @GetMapping("/hi/{name}")
    public Flux<List<String>> hi(@PathVariable String name) {
        return Flux.create(new Consumer<FluxSink<List<String>>>() {
            @Override
            public void accept(FluxSink<List<String>> stringFluxSink) {
                List<String> stringList1 = new ArrayList<String>();
                stringList1.add("hi 1, " + name);
                List<String> stringList2 = new ArrayList<String>();
                stringList2.add("hi 2, " + name);
                stringFluxSink.next(stringList1).next(stringList2).complete();
            }
        });
    }
}
