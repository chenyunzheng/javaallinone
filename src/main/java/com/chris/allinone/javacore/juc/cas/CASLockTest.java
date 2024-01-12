package com.chris.allinone.javacore.juc.cas;

import java.util.concurrent.TimeUnit;

/**
 * @author chrischen
 */
public class CASLockTest {

    static int sum = 0;

    public static void main(String[] args) throws InterruptedException {
        CASLock casLock = new CASLock();
        int tNum = 10;
        for (int i = 0; i < tNum; i++) {
            new Thread(() -> {
                //抢占锁，成功则业务执行完后释放；失败则继续抢占
                boolean spin = true;
                while (spin) {
                    if (casLock.getLockState() == 0 && casLock.lock()) {
                        int j = 0;
                        while (j < 10000) {
                            sum++;
                            j++;
                        }
                        casLock.unlock();
                        spin = false;
                    }
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println("sum = " + sum);
    }
}
