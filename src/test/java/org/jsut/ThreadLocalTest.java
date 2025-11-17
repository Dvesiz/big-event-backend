package org.jsut;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {

    @Test
    public void testThreadLocalSetAndGet() {
        // 提供一个线程局部变量
        ThreadLocal tl = new ThreadLocal();

        // 开启两个线程
        new Thread(() -> {
            tl.set("祁小俊");
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
        }, "蓝色").start();

        new Thread(() -> {
            tl.set("祁大俊");
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
        }, "绿色").start();
    }
}
