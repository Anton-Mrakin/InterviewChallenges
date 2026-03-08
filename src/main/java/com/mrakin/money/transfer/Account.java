package com.mrakin.money.transfer;

import java.util.concurrent.locks.ReentrantLock;

public final class Account {
    private final int id;
    private long balance;
    private final ReentrantLock lock = new ReentrantLock();

    public Account(int id, long initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public int getId() {
        return id;
    }

    public long getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    private void depositUnsafe(long amount) {
        balance += amount;
    }

    private void withdrawUnsafe(long amount) {
        balance -= amount;
    }

    private boolean hasEnoughUnsafe(long amount) {
        return balance >= amount;
    }

    public static boolean transfer(Account from, Account to, long amount) {
        if (amount <= 0 || from == to || from.getId() == to.getId()) {
            return false;
        }

        Account first = from.getId() < to.getId() ? from : to;
        Account second = from.getId() < to.getId() ? to : from;

        first.lock.lock();
        try {
            second.lock.lock();
            try {
                if (!from.hasEnoughUnsafe(amount)) {
                    return false;
                }

                from.withdrawUnsafe(amount);
                to.depositUnsafe(amount);
                return true;
            } finally {
                second.lock.unlock();
            }
        } finally {
            first.lock.unlock();
        }
    }
}