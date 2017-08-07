package com.jtk.threads;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ThreadFactory;

/**
 * Created by jubin on 5/6/2017.
 */
public class ExceptionFromMonitor {

    private static Object lock = new Object();
    public static void main(String[] args) {
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        threadFactoryBuilder.setDaemon(false);
        threadFactoryBuilder.setNameFormat("jubin-pool-%d");
//        threadFactoryBuilder.setUncaughtExceptionHandler((t, e) -> System.out.println("UncaughtExceptionHandler: " + e.getMessage()));

        ThreadFactory threadFactory = threadFactoryBuilder.build();
        threadFactory.newThread(() -> {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                System.out.println("wait threw exception because notify threw exception...");
            }

        }).start();

        threadFactory.newThread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("Exception from Notify");
        }).start();
    }
}
