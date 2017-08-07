package com.jtk.threads.forkjoin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Forking {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        System.out.println("forkJoinPool.getParallelism() = " + forkJoinPool.getParallelism());

        // number of processor cores will be used
        System.out.println("ForkJoinPool.getCommonPoolParallelism() = " + ForkJoinPool.getCommonPoolParallelism());
        FileCountTask fileCountTask = new FileCountTask(Paths.get("C:\\Users\\jubin").toFile());
        long startTime = System.currentTimeMillis();
        int count = forkJoinPool.invoke(fileCountTask);
        long endTime = System.currentTimeMillis();
        System.out.println("file count = " + count + " in " + (endTime - startTime) / 1_000 + " s");
        System.out.println("forkJoinPool.getStealCount() = " + forkJoinPool.getStealCount());

    }

    public static class FileCountTask extends RecursiveTask<Integer> {
        private File rootFile;

        public FileCountTask(File rootFile) {
            this.rootFile = rootFile;
        }

        @Override
        protected Integer compute() {
            List<FileCountTask> subTasks = new ArrayList<>();
            int count = 0;
            try {
                for (File file : rootFile.listFiles()) {
                    if (file.isFile())
                        ++count;
                    else if (file.isDirectory() && !Files.isSymbolicLink(Paths.get(rootFile.toURI()))) {
                       // FileCountTask fileCountTask =  new FileCountTask(file);
                        subTasks.add(new FileCountTask(file));
                        //invokeAll(fileCountTask);
                    }
                }
            } catch (Exception e) {
                // doesn't access
                return count;
            }
            if (subTasks.size() > 0) {

                invokeAll(subTasks);
                for (FileCountTask fileCountTask : subTasks) {
                    count += fileCountTask.join();
                }
            }
            return count;
        }
    }
}
