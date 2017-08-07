package com.jtk.threads.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by jubin on 6/4/2017.
 */
public class RejectionPolicies {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(7);
        //RejectedExecutionHandler handlerPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
        //RejectedExecutionHandler handlerPolicy = new ThreadPoolExecutor.DiscardOldestPolicy();
        //RejectedExecutionHandler handlerPolicy = new ThreadPoolExecutor.DiscardPolicy();
        RejectedExecutionHandler handlerPolicy = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 10,
                TimeUnit.SECONDS, arrayBlockingQueue, handlerPolicy);

        IntStream.range(0, 20).forEach(value -> {

            try {
                threadPoolExecutor.execute(() -> {
                    System.out.println("Executing task " + value +
                            " on Thread Name " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        System.out.println(" thread " + value + " interrupted");
                    }

                    System.out.println("Done executing task " + value);
                });
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        });

        while (threadPoolExecutor.getActiveCount() != 0) {
            TimeUnit.SECONDS.sleep(2);
        }
        threadPoolExecutor.shutdownNow();
    }
}
