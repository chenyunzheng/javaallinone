package com.chris.allinone.javacore.juc.lock.threadactiveness;

/**
 * @author chrischen
 * 哲学家
 */
public class Philosopher extends Thread {

    private Chopstick left;
    private Chopstick right;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (left) {
                System.out.println(Thread.currentThread().getName() + "，获取左筷子-" + left.getNumber());
//                try {
//                    left.wait(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                synchronized (right) {
                    System.out.println(Thread.currentThread().getName() + "，获取右筷子-" + right.getNumber());
                    eat();
                }
            }
            System.out.println(Thread.currentThread().getName() + "，放下筷子");
            think();
        }
    }

    private void think() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "，思考。。");
    }

    private void eat() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "，吃饭。。");
    }
}
