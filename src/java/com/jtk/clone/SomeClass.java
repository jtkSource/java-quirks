package com.jtk.clone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jubin on 7/8/2017.
 */
public class SomeClass implements Cloneable{
    private final String someClassString;
    private List<String> stringList;
    public SomeClass(String s1) {
        this.someClassString = s1;
        stringList = new ArrayList<>();
        System.out.println("SomeClass constructor is called!");
    }

    public void addToStringList(String s) {
        stringList.add(s);
    }

    public List<String> getStringList() {
        return stringList;
    }

    @Override
    protected SomeClass clone() throws CloneNotSupportedException {
        SomeClass someClass = (SomeClass) super.clone();
        someClass.stringList = (List<String>) ((ArrayList)stringList).clone();
        return someClass;
    }

    @Override
    public String toString() {
        return "SomeClass{" +
                "someClassString='" + someClassString + '\'' +
                ", stringList=" + stringList +
                '}';
    }
}
