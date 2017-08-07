package com.jtk.inner;

import java.io.*;
import java.util.concurrent.DelayQueue;

/**
 * Created by jubin on 5/12/2017.
 */
public class Inner {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //System.out.println("OutterClass.Inn = " + OutterClass.InnerStatic);
        System.out.println("new OutterClass().new  = " + new OutterClass().new InnerClass());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
        out.writeObject(new ClassB(3));
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        //System.out.println("objectInputStream.readObject() = " + objectInputStream.readObject());
    }
}

class OutterClass {

    private static int outterStatic;
    private int outter = 21;

    public OutterClass() {
        System.out.println(InnerStatic.numberStatic);
        System.out.println(new InnerStatic().number);
        System.out.println("InnerClass.numberStatic = " + InnerClass.numberStatic);
        System.out.println("new InnerClass().number = " + new InnerClass().number);
    }

    public static class InnerStatic {
        private static int numberStatic = 10;
        private int number = 20;

        {
            System.out.println(outterStatic);
        }

        public InnerStatic() {
            //OutterClass.this.outter;
            System.out.println("outterStatic = " + outterStatic);
        }
    }

    public class InnerClass {
        // private static int numberStatic=888;
        private final static int numberStatic = 888;
        private int number = 999;

        public InnerClass() {
            System.out.println("outterStatic = " + outterStatic);
            System.out.println("number = " + number);
            System.out.println("outter = " + outter);
        }
    }
}


class ClassA {
    private int number;
    public ClassA(int number){
        this.number=number;
    }
}

class ClassB extends ClassA implements Serializable{
    private int bNumber=2;
    public ClassB(int number) {
        super(number);
    }
}