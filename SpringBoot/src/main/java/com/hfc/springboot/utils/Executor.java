package com.hfc.springboot.utils;

import org.springframework.context.Lifecycle;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hfc on 2022/5/11.
 */
public class Executor implements Lifecycle {

    private static final ThreadPoolExecutor executor;

    static {
        int coreCount = Runtime.getRuntime().availableProcessors();
        int timeout = 60;
        int maxBlockSize = 1000;
        AtomicInteger counter = new AtomicInteger();
        executor = new ThreadPoolExecutor(
                coreCount,
                coreCount * 2,
                timeout,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(maxBlockSize),
                r -> new Thread(r, "executor-thread-" + counter.getAndIncrement()),
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static void execute(Runnable command) {
        executor.execute(command);
    }

    @Override
    public void start() {}

    @Override
    public void stop() {
        Executor.executor.shutdown();
    }

    @Override
    public boolean isRunning() {
        return executor.isShutdown();
    }
}
