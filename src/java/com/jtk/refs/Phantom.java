package com.jtk.refs;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * Created by jubin on 5/10/2017.
 */
public class Phantom {
    public static void main(String[] args) {
        String jubinStr = new String("jubin");
        String febaStr = new String("feba");
        String benStr = new String("ben");

        Weak.Person jubin = new Weak.Person(jubinStr, 2222);
        Weak.Person feba = new Weak.Person(febaStr, 2344);
        Weak.Person ben = new Weak.Person(benStr, 23111);

        ReferenceQueue<Weak.Person> personReferenceQueue = new ReferenceQueue<>();
        PhantomReference<Weak.Person> personPhantomReference = new PhantomReference<>(jubin, personReferenceQueue);
        jubin = null;
        // Invoke garbage collector        
        printMessage(personPhantomReference, "Invoking gc() first time:") ;
        System.gc();
        printMessage(personPhantomReference, "After invoking gc() first time:");
        // Invoke garbage collector again        
        printMessage(personPhantomReference, "Invoking gc() second time:") ;
        System.gc();
        printMessage(personPhantomReference, "After invoking gc() second time:");
    }

    public static void printMessage(PhantomReference<Weak.Person> pr, String msg) {
        System.out.println(msg);
        System.out.println("pr.isEnqueued = " + pr.isEnqueued());
        System.out.println("pr.get() = " + pr.get());
        // We will check if pr is queued. If it has been queued,         
        // we will clear its referent's reference        
        if (pr.isEnqueued()) {
            pr.clear();
            System.out.println("Cleared the referent's reference");
        }
        System.out.println("-----------------------");
    }

}
