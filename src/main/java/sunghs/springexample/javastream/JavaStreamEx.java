package sunghs.springexample.javastream;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaStreamEx {

    private static final Set<String> worker = new HashSet<>();

    public static void main(String[] args) {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "15");
        ForkJoinPool customPool = new ForkJoinPool(20);

        List<CompletableFuture<Void>> list = IntStream.rangeClosed(1, 100)
            .boxed()
            .map(integer -> CompletableFuture.runAsync(() ->
                something(integer), customPool))
            .collect(Collectors.toList());

        list.stream()
            .map(CompletableFuture::join)
            .collect(Collectors.toList());

        log.info("core size   {}", Runtime.getRuntime().availableProcessors());
        log.info("real worker {}", worker.size());
        log.info("custom pool {}", customPool.getPoolSize());
        log.info("common pool {}", ForkJoinPool.commonPool().getPoolSize());
    }

    private static void something(int i) {
        try {
            Thread.sleep(250);
            System.out.printf("[%s] %d %s%n", Thread.currentThread().getName(), i, LocalTime.now());
            worker.add(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
