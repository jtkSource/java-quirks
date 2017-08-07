package com.jtk.nio;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTest {
    static {
        Path path = Paths.get("C:\\Users\\jubin");
        System.out.format("toString: %s%n", path.toString());
        System.out.format("getFileName: %s%n", path.getFileName());
        System.out.format("getName(0): %s%n", path.getName(0));
        System.out.format("getNameCount: %d%n", path.getNameCount());
        System.out.format("subpath(0,2): %s%n", path.subpath(0, 2));
        System.out.format("getParent: %s%n", path.getParent());
        System.out.format("getRoot: %s%n", path.getRoot());
        System.out.format("%s%n", path.toUri());

    }
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        int count = getCountOfFiles(Paths.get("C:\\Users\\jubin"),0);
        long endTime = System.currentTimeMillis();
        System.out.println("file count = " + count + " in " + (endTime - startTime) / 1_000 + " s");
    }

    private static int getCountOfFiles(Path path,int count) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path p : stream) {
                if (p.toFile().isDirectory())
                    count = getCountOfFiles (p,count);
                else ++count;
            }
        }catch (Exception e ){
          //  System.out.println("Couldn't access dir " + path.toUri());
        }
        return count;
    }

}
