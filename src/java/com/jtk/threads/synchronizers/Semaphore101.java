package com.jtk.threads.synchronizers;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by jubin on 5/10/2017.
 */
public class Semaphore101 {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3, true);
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Thread t1 = new Thread(() -> {
                try {
                    semaphore.acquire(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(finalI + " thread Acquired 1 permits...");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                semaphore.release();
                System.out.println(finalI + " thread released 1 permits...");
            });
            //t1.start();
            threads.add(t1);
        }
        threads.stream().forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Negative Semaphore");

        Semaphore negativeSemaPhore = new Semaphore(-3);
        new Thread(() -> {
            System.out.println("negativeSemaPhore = " + negativeSemaPhore.availablePermits());
            while (negativeSemaPhore.availablePermits() < 0) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ;
            System.out.println(" No more negative semaphore... ");
        }).start();

        for (;negativeSemaPhore.availablePermits()<0;) {

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            negativeSemaPhore.release();
        }
    }
}
