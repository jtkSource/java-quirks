    package com.jtk.threads.locks;

import java.util.concurrent.locks.StampedLock;

/**
 * Created by jubin on 5/10/2017.
 */
public class Stampd {
}

class BankAccountStampedLock {
    private final StampedLock stampedLock = new StampedLock();
    private long balance;

    public BankAccountStampedLock(long balance) {
        this.balance = balance;
    }

    public void deposit(long amount) {
        long stamp = stampedLock.writeLock();
        try {
            balance += amount;
        } finally {
            stampedLock.unlock(stamp);
        }
    }

    public void withdraw(long amount) {
        long stamp = stampedLock.writeLock();
        try {
            balance -= amount;
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    public long getBalance() {
        long stamp = stampedLock.readLock();
        try {
            return balance;
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    public long getBalanceOptimisticRead(){
        long stamp = stampedLock.tryOptimisticRead();//Returns a stamp that can later be validated, or zero
        long balance = this.balance;
        if(!stampedLock.validate(stamp)){// returns true if the lock has been acquired
            stamp = stampedLock.readLock();//pessimistic lock
            try {
                balance = this.balance;
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }
        return balance;
    }

    public static void main(String[] args) {

    }
}
