package com.jtk.threads.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.concurrent.ThreadPoolExecutor.*;

/**
 * Created by jubin on 5/6/2017.
 */
public class Executors101 {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        threadFactoryBuilder.setDaemon(false);
        threadFactoryBuilder.setNameFormat("jubin-pool-%d");
        threadFactoryBuilder.setUncaughtExceptionHandler((t, e) -> System.out.println("UncaughtExceptionHandler: " + e.getMessage()));

        ExecutorService executorService = Executors.newFixedThreadPool(5, threadFactoryBuilder.build());

        BlockingQueue blockingQueue = new ArrayBlockingQueue(7);
        ThreadPoolExecutor tpe =
                new ThreadPoolExecutor(3, 5, 10000, TimeUnit.MINUTES, blockingQueue) {
                    @Override
                    protected void afterExecute(Runnable r, Throwable t) {
                        System.out.println("After Execute Throwable " + t);
                        System.out.println("After Execute Runnable " + r);
                    }
                };


        executorService = tpe;


        List<Callable<String>> tasks = IntStream.range(0, 3).mapToObj(value -> (Callable<String>) () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                return "hello";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "bye";
            }
        }).collect(Collectors.toList());

        ThreadPoolExecutor threadPoolExecutor = ((ThreadPoolExecutor) executorService);

        threadPoolExecutor.setRejectedExecutionHandler(new AbortPolicy() {
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("rejected task");
                System.out.println("Number of tasks          (RejectedExecutionHandler): " + executor.getTaskCount());
                System.out.println("Number of tasks running  (RejectedExecutionHandler): " + executor.getActiveCount());
                System.out.println("Number of tasks on Queue (RejectedExecutionHandler): " + executor.getQueue().size());
                System.out.println(r.toString());
            }
        });
        executorService.invokeAll(tasks);

        System.out.println("Number of tasks " + threadPoolExecutor.getTaskCount());
        System.out.println("Number of tasks running " + threadPoolExecutor.getActiveCount());
        System.out.println("Number of tasks on Queue " + threadPoolExecutor.getQueue().size());
        System.out.println("Number of completed tasks running " + threadPoolExecutor.getCompletedTaskCount());

        Future f4 = submitExceptionSleepTasks(executorService);

        while (threadPoolExecutor.getActiveCount() != 0) {
            //futureString.get(1,TimeUnit.SECONDS); //will throw exception
            System.out.println("Waiting for task to complete...");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Number of tasks " + threadPoolExecutor.getTaskCount());
            System.out.println("Number of tasks running " + threadPoolExecutor.getActiveCount());
            System.out.println("Number of tasks on Queue " + threadPoolExecutor.getQueue().size());
        }
        executorService.shutdown();
        /*try {
            System.out.println("f4: " + f4.get());
        } catch (ExecutionException e) {
            Throwable rootException = e.getCause();
            System.out.println(rootException.getMessage());
        }
        System.out.println("Number of completed tasks running " + ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
*/
    }

    private static Future<?> submitExceptionSleepTasks(ExecutorService executorService) {
        return executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("throwing exception");
                throw new RuntimeException("Yo Exception!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static Future<String> submitSleepTasks(ExecutorService executorService) {
        return executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                return "Hello";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "bye";
            }
        });
    }
}
