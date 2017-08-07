package com.jtk.covariance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jubin on 6/2/2017.
 */
public class Main {
    public static void main(String[] args) {
        Number[] numbers = new Number[3];
        numbers[0] = new Integer(10);
        numbers[1] = new Double(2.9);
        numbers[2] = new Float(0);

        Integer[] myInts = {1,2,3,4};
        Number[] myNumber = myInts;
        //myNumber[0]=9.08; //Exception in thread "main" java.lang.ArrayStoreException: java.lang.Double
        //System.out.println("myNumber = " + myNumber);
        List<Integer> integers = new ArrayList<>();
        //List<Number> numberList = integers; //incompatible types.

        List<? extends Number> myNums = new ArrayList<Integer>();
        List<? extends Number> myNums1 = new ArrayList<Float>();
        List<? extends Number> myNums2 = new ArrayList<Double>();

        //myNums2.add(0.9); //compile error

    }
}
