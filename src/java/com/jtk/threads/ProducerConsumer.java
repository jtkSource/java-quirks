package com.jtk.threads;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;

/**
 * Created by jubin on 5/6/2017.
 */
class ProducerConsumer {

    private final Object obj = new Object();
    private List<Integer> integers = new ArrayList<>();

    public static void main(String[] args) {


        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        threadFactoryBuilder.setDaemon(false);
        threadFactoryBuilder.setNameFormat("jubin-pool-%d");
        threadFactoryBuilder.setUncaughtExceptionHandler((t, e) -> System.out.println("UncaughtExceptionHandler: " + e.getMessage()));

        ThreadFactory threadFactory = threadFactoryBuilder.build();
        ProducerConsumer producerConsumer = new ProducerConsumer();

        Thread p1 = threadFactory.newThread(() ->
                IntStream.range(0, 10).forEach(value -> producerConsumer.producer(value)));

        Thread c1 = threadFactory.newThread(() ->  producerConsumer.consumer());
        //c1.start();
        p1.start();

        c1.start();
    }

    public void producer(int i) {

        synchronized (obj) {

            integers.add(i);
            obj.notifyAll();//non - blocking
            System.out.println("PThread.currentThread().getName() = " + Thread.currentThread().getName() + " i = " + i);
           // if (i == 6)
             //   throw new RuntimeException("Yo Exception");

        }
    }

    public void consumer() {
        while (true) {
            synchronized (obj) {
                try {
                    if (integers.isEmpty())
                        obj.wait();
                } catch (InterruptedException e) {
                    System.out.println("Consumer threw exception");
                    throw new RuntimeException("Consumer wait exception");
                }
                //  if (i == 6)
                //    throw new RuntimeException("Yo Exception");
                if (integers.size() > 0) {

                    int i = integers.remove(0);
                    //check after wait to see if the integer array has elements
                    System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName() + "i = " + i);
                    obj.notifyAll();
                }
            }

        }
    }
}
