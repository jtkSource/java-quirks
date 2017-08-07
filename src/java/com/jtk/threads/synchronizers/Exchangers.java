package com.jtk.threads.synchronizers;

import java.util.UUID;
import java.util.concurrent.Exchanger;

/**
 * Created by jubin on 5/10/2017.
 */
public class Exchangers {
    public static void main(String[] args) {

        final Exchanger<String> token = new Exchanger<>();

        new Thread(() -> {
            String msg = "thread1:" + UUID.randomUUID();
            System.out.println("transferring: " + msg);
            try {
                String received = token.exchange(msg);
                System.out.println(" thread1 :" + received);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            String msg = "thread2:" + UUID.randomUUID();
            System.out.println("transferring: " + msg);
            try {
                String received = token.exchange(msg);
                System.out.println(" thread2 :" + received);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            String msg = "thread3:" + UUID.randomUUID();
            System.out.println("transferring: " + msg);
            try {
                String received = token.exchange(msg);
                System.out.println(" thread3 :" + received);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
