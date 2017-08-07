package com.jtk.threads.async;

import java.util.Random;
import java.util.concurrent.*;

public class Basics {
    public final static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //blockingThreads(executorService);
        //nonBlockingThreads(executorService);
        //chaining(executorService);
        //compose();
        //combine();
        CompletableFuture<Double> doubleCompletableFuture =
                CompletableFuture.supplyAsync(() -> {
                    Double random = new Random(2).nextDouble();
                    System.out.format("called doubleCompletableFuture on %s%d %d \n",
                            Thread.currentThread().getName(), random);
                    sleep();
                    return random;
                }, executorService);

        doubleCompletableFuture
                .thenCompose(Basics::getSineFromSomewhereBad)
                .thenCompose(Basics::getCosineFromSomewhereBad)
                .thenCompose(Basics::getTangentFromSomewhereBad)
                .thenApply(String::valueOf)
                .thenApply(s -> s + " Is the double value ")
                .thenAccept(System.out::println);

        //
         executorService.shutdown();
        System.out.println("\n Main exited");
    }

    private static CompletableFuture<Double> getSineFromSomewhereBad(Double d) {
        System.out.format("called getSineFromSomewhereBad on %s for value %s%d", Thread.currentThread().getName(), d.toString());
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("called getSineFromSomewhereBad supplyAsync on " + Thread.currentThread().getName());
            sleep();
            return Math.asin(d);
        }, executorService);
    }

    private static CompletableFuture<Double> getCosineFromSomewhereBad(Double d) {
        System.out.format("called getCosineFromSomewhereBad on %s for value %s%d", Thread.currentThread().getName(), d.toString());
        return CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("called getCosineFromSomewhereBad supplyAsync on " + Thread.currentThread().getName());
                    sleep();
                    if(true)
                        throw new RuntimeException("Exception in the Cosine() ");
                    return Math.acos(d);
                }, executorService)
                .handle((aDouble, throwable) -> {
                    if(throwable!=null){
                        System.out.println("Exception in Cosine function  returning default 1.0");
                        return 1.0;
                    }
                    return aDouble;
                });
    }

    private static CompletableFuture<Double> getTangentFromSomewhereBad(Double d) {
        System.out.format("called getTangentFromSomewhereBad on %s for value %s%d", Thread.currentThread().getName(), d.toString());

        return CompletableFuture.supplyAsync(() -> {
            System.out.println("called getTangentFromSomewhereBad supplyAsync on " + Thread.currentThread().getName());
            sleep();
            return Math.atan(d);
        }, executorService);
    }

    private static void combine() {
        CompletableFuture<Double> doubleCompletableFuture =
                CompletableFuture.supplyAsync(() -> {
                    System.out.println("called doubleCompletableFuture on "
                            + Thread.currentThread().getName());
                    sleep();
                    return new Random(2).nextDouble();
                }, executorService);
        CompletableFuture<Double> doubleCompletableFuture2 =
                CompletableFuture.supplyAsync(() -> {
                    System.out.println("called doubleCompletableFuture2 on "
                            + Thread.currentThread().getName());
                    sleep();
                    return new Random(2).nextDouble();
                }, executorService);

        CompletableFuture<Double> addFuture = doubleCompletableFuture
                .thenCombine(doubleCompletableFuture2, (aDouble, aDouble2) -> aDouble + aDouble2);

        addFuture.thenApply(String::valueOf)
                .thenApply(s -> s + " Is arc sine of a double value ")
                .thenAccept(System.out::println);
    }

    private static void compose() {
        CompletableFuture<Double> doubleCompletableFuture =
                CompletableFuture.supplyAsync(() -> {
                    System.out.println("called doubleCompletableFuture on " + Thread.currentThread().getName());
                    sleep();
                    return new Random(2).nextDouble();
                }, executorService);

        doubleCompletableFuture.thenCompose(Basics::getSineFromSomewhereBad)
                .thenApply(String::valueOf)
                .thenApply(s -> s + " Is arc sine of a double value ")
                .thenAccept(System.out::println);
    }

    private static void chaining(ExecutorService executorService) throws InterruptedException, ExecutionException {
        CompletableFuture<Double> doubleCompletableFuture =
                CompletableFuture.supplyAsync(() -> {
                    sleep();
                    return new Random(2).nextDouble();
                }, executorService);

        CompletableFuture<String> stringFuture =
                doubleCompletableFuture.thenApply(Math::asin)
                        .thenApply(String::valueOf)
                        .thenApply(s -> s + " Is arc sine of a double value ");

        System.out.println("stringFuture.get() = " + stringFuture.get());
    }

    private static void nonBlockingThreads(ExecutorService executorService) {
        CompletableFuture<String> completableFutureString =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "Hello from " + Thread.currentThread().getName();
                }, executorService);
        //System.out.println("completableFutureString.get() = " + completableFutureString.get());
        completableFutureString.thenAccept(System.out::println);
    }

    private static void blockingThreads(ExecutorService executorService) throws InterruptedException, ExecutionException {

        Callable<String> callString = () -> {
            Thread.sleep(300);
            return "Hello from " + Thread.currentThread().getName();
        };

        Future<String> futureString = executorService.submit(callString);
        System.out.println("futureString.get() = " + futureString.get());//blocking
    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
