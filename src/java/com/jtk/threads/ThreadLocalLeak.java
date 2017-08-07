package com.jtk.threads;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.TimeUnit;

/**
 * Created by jubin on 6/4/2017.
 */


class CustomClassLoader extends URLClassLoader {
    public CustomClassLoader(URL... urls) {
        super(urls, null);
    }

    @Override
    protected void finalize() {
        System.out.println("*** CustomClassLoader finalized!");
    }
}

public class ThreadLocalLeak {

    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException, InterruptedException {
        new  ThreadLocalLeak().loadFoo();
        while (true) {
            System.gc();
            TimeUnit.SECONDS.sleep(1);
        }
    }

    private void loadFoo() throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        CustomClassLoader customClassLoader = new CustomClassLoader(new URL("file:/C:/cygwin64/home/jubin/git/JavaQuirks/build/classes/main/"));
        Class<?> clazz =  customClassLoader.loadClass("com.jtk.threads.Foo");
        clazz.newInstance();// will not work for friendly classes
        customClassLoader=null;
    }
}


