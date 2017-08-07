package com.jtk.threads;

public class Foo {

    private static ThreadLocal<Foo> tl = new ThreadLocal<>();
    public Foo() {
        tl.set(this);
        System.out.println("Classloader: " + this.getClass().getClassLoader());
    }
    public static void nullThreadLocal(){
        tl=null;
    }
}