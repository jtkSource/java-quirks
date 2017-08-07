package com.jtk.threads.synchronizers;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by jubin on 5/10/2017.
 */
public class Latch {

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(5);
        final Random r = new Random();
        for (int i = 1; i <= 5; i++) {
            int finalI = i;

            new Thread(() -> {
                try {
                    startGate.await();
                    System.out.println(" starting thread " + finalI);

                    TimeUnit.SECONDS.sleep(r.nextInt(5));
                    System.out.println(" Done.. " + finalI);
                    endGate.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        startGate.countDown();
        endGate.await();

        System.out.println("End Initialising...");
    }
}
