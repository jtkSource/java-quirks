package com.jtk.refs;

import java.util.WeakHashMap;

/**
 * Created by jubin on 5/10/2017.
 */
public class Weak {

    public static void main(String[] args) {
        WeakHashMap<Person,String> personWeakHashMap = new WeakHashMap<>();
        String jubinStr = new String("jubin");
        String febaStr = new String("feba");
        String benStr = new String("ben");

        Person jubin = new Person(jubinStr, 2222);
        Person feba = new Person(febaStr, 2344);
        Person ben = new Person(benStr, 23111);
        personWeakHashMap.put(jubin, jubinStr);
        personWeakHashMap.put(feba, febaStr);
        personWeakHashMap.put(ben, benStr);
        System.gc();
        System.out.println("personWeakHashMap = " + personWeakHashMap);
        jubin = null;
        //jubinStr = null;
        feba = null;
        //febaStr = null;
        System.gc();
        for (int i = 0; i < 900_000; i++) {

            if (personWeakHashMap.size() >1 ) {
                System.out.println("At iteration " + i + " the map still holds the reference on Person " + personWeakHashMap.size());
            } else {
                System.out.println("Person has finally been garbage collected at iteration " + i + ", hence the map is now size:" + personWeakHashMap.size());
                System.out.println("personWeakHashMap = " + personWeakHashMap.entrySet().size());
                break;
            }
            System.gc();
        }
        System.out.println("personWeakHashMap = " + personWeakHashMap);
    }

    public static class Person {

        private final String person;
        private final int number;

        public Person(String person, int number) {
            this.person = person;
            this.number = number;
        }

        @Override
        public String toString() {
            return " [" + this.person + ":" + number + "]";
        }
    }
}
