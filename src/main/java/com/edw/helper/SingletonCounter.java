package com.edw.helper;

import javax.ejb.Singleton;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 *     com.edw.helper.SingletonCounter
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 17 Mei 2021 22:25
 */
@Singleton
public class SingletonCounter {

    private AtomicInteger counter = new AtomicInteger(0);

    public int getCounter() {
        return counter.get();
    }

    public void setCounter(int value) {
        counter = new AtomicInteger(value);
    }

    public void increment() {
        counter.incrementAndGet();
    }
}
