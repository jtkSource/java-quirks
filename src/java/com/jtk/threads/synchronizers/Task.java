package com.jtk.threads.synchronizers;

import java.util.concurrent.*;

/**
 * Created by jubin on 5/10/2017.
 */
public class Task {
    public static void main(String[] args) {


        FutureTask<String> stringFutureTask1 = new FutureTask<String>(() -> {
            TimeUnit.SECONDS.sleep(2);
            return "Hello 2";
        });

        try {
            new Thread(stringFutureTask1).start();
            System.out.println("stringFuture1.get() = " + stringFutureTask1.get());
        }catch (ExecutionException e ){
            System.out.println(e.getCause());
        } catch (InterruptedException e) {
            System.out.println("---");
            e.printStackTrace();
        }


        ExecutorService executorService = Executors.newCachedThreadPool();
        FutureTask<String> stringFutureTask = new FutureTask<String>(() -> {
            TimeUnit.SECONDS.sleep(2);
            return "Hello";
        });
        FutureTask runTask = new FutureTask(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            }catch (Exception e){

            }
        },"Hello Run");

        executorService.submit(runTask);
        Future<String> stringFuture = (Future<String>) executorService.submit(stringFutureTask);
        stringFuture.cancel(false);
        try {
            System.out.println("runTask.get() = " + runTask.get());
            System.out.println("runTask.get() = " + runTask.get());
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            System.out.println("stringFuture.get() = " + stringFuture.get());
        }catch (ExecutionException e ){
            System.out.println(e.getCause());
        } catch (InterruptedException e) {
            System.out.println("---");
            e.printStackTrace();
        }


       executorService.shutdown();

    }
}
