package com.jtk.threads.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jubin on 6/4/2017.
 */
class Shared {
    private final Lock lock;
    private final Condition condition;
    private volatile char c;
    private boolean available;

    public Shared() {
        c = '\u0000';
        available = false;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public char getSharedChar() {
        lock.lock();
        try {
            while (!available) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            available = false;
            condition.signal();
        } finally {
            lock.unlock();
            return c;
        }
    }

    public void setSharedChar(char c) {
        lock.lock();
        try {
            while (available) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.c = c;
            available = true;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public Lock getLock() {
        return lock;
    }
}

class Producer extends Thread {
    private final Lock lock;
    private final Shared shared;

    public Producer(Shared s) {
        this.shared = s;
        lock = s.getLock();
    }

    @Override
    public void run() {
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            lock.lock();
            shared.setSharedChar(ch);
            System.out.println(ch + " produced by producer");
            lock.unlock();
        }
    }
}

class Consumer extends Thread {
    private final Lock lock;
    private final Shared shared;

    public Consumer(Shared s) {
        this.shared = s;
        lock = s.getLock();
    }

    @Override
    public void run() {
        char ch;
        do {
            lock.lock();
            ch = shared.getSharedChar();
            System.out.println(ch + " consumed by consumer.");
            lock.unlock();
        } while (ch != 'Z');
    }
}

public class ConditionDemo {
    public static void main(String[] args) {
        Shared s = new Shared();
        new Producer(s).start();
        new Consumer(s).start();
    }
}
