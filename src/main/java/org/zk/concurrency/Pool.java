package org.zk.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by zhangkang on 2019/6/23.
 */
public class Pool<T> {
    private int size;
    private List<T> items = new ArrayList<T>();
    private volatile boolean[] checkout;
    private Semaphore semaphore;

    public Pool(Class<T> classObject, int size) {
        this.size = size;
        checkout = new boolean[size];
        semaphore = new Semaphore(size, true);
        for (int i = 0; i < size; i++) {
            try {
                items.add(classObject.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public T checkout() {
        try {
            semaphore.acquire();
            return getItem();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void checkIn(T item) {
        if (releaseItem(item)) {
            semaphore.release();
        }
    }

    private T getItem() {
        for (int i = 0; i < size; i++) {
            if (!checkout[i]) {
                checkout[i] = true;
                return items.get(i);
            }
        }
        return null;
    }

    private boolean releaseItem(T item) {
        int index = items.indexOf(item);
        if (index == -1) {
            return false;
        }
        if (checkout[index]) {
            checkout[index] = true;
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Pool<String> pool = new Pool<>(String.class, 5);
        for (int i = 0; i < 10; i++) {
            String s = pool.checkout();
            System.out.println("checkout: " + s);
            pool.checkIn(s);

        }
    }


}
