package com.chris.allinone.javacore.exception;

import jodd.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author chrischen
 * @date 2025/6/17
 * @desc Test2
 */
@Slf4j
public class Test2 {
    private static String prefix = "test-";
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
//        ExecutorService executorService = Executors.newFixedThreadPool(1,
//                new ThreadFactoryBuilder().setNameFormat(prefix + "%d")
//                        .setUncaughtExceptionHandler((t, e) -> log.error("Exception occur on thread: {}", t, e)).get());
//        IntStream.rangeClosed(1, 10).forEach(i -> {
//            executorService.execute(() -> {
//                log.info("execute #" + i);
//                if (i == 5) {
//                    throw new RuntimeException("error");
//                }
//            });
//        });

//        ExecutorService executorService = Executors.newFixedThreadPool(1,
//                new ThreadFactoryBuilder().setNameFormat(prefix + "%d").get());
//        IntStream.rangeClosed(1, 10).forEach(i -> {
//            executorService.submit(() -> {
//                log.info("execute #" + i);
//                if (i == 5) {
//                    throw new RuntimeException("error");
//                }
//            });
//        });

        ExecutorService executorService = Executors.newFixedThreadPool(1,
                new ThreadFactoryBuilder().setNameFormat(prefix + "%d").get());
        List<Future<Integer>> futureList = IntStream.rangeClosed(1, 10).mapToObj(i -> {
            return executorService.submit(() -> {
                log.info("execute #" + i);
                if (i == 5) {
                    throw new RuntimeException("error");
                }
                return i;
            });
        }).collect(Collectors.toList());

//        futureList.forEach(future -> {
//            try {
//                log.info(String.valueOf(future.get(1, TimeUnit.MINUTES)));
//            } catch (InterruptedException | ExecutionException | TimeoutException e) {
//                throw new RuntimeException(e);
//            }
//        });
        for (int i = 0; i < futureList.size(); i++) {
            Integer value = null;
            value = futureList.get(i).get();
            log.info(String.valueOf(value));
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);

    }
}
