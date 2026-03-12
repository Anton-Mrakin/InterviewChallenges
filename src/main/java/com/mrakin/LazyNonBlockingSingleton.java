package com.mrakin;

public class LazyNonBlockingSingleton {
    private LazyNonBlockingSingleton() {
    }

    private static class Holder {
        private static final LazyNonBlockingSingleton INSTANCE = new LazyNonBlockingSingleton();
    }

    public static LazyNonBlockingSingleton getInstance() {
        return Holder.INSTANCE;
    }
}
