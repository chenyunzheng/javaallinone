package com.chris.allinone.javacore.skills;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chrischen
 */
@Slf4j
public class LockPractice {

    private static final int COUNT = 100 * 10000;

    volatile int a = 0;
    volatile int b = 0;

    public void add() {
        for (int i = 0; i < COUNT; i++) {
            synchronized (this) {
                a++;
                b++;
            }
        }
    }

    public void compare() {
        for (int i = 0; i < COUNT; i++) {
            synchronized (this) {
                if (a == b) {
                    log.info("a: {}, b: {}", a, b);
                } else {
                    throw new RuntimeException("error");
                }
//                if (a < b) {
//                    log.info("a: {}, b: {}, a > b: {}", a, b, a > b);
//                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final LockPractice lockPractice = new LockPractice();
        new Thread(() -> lockPractice.add()).start();
        new Thread(() -> lockPractice.compare()).start();
        TimeUnit.SECONDS.sleep(2);
//        //String data = "(This is a (sample) () string with (parentheses) for (extraction))";
//        //String data = null;
//        String data = "((111 2321,222 3333, 24234 4521),(), (wwwww wer2, r324r2 4085))";
//        Pattern pattern = Pattern.compile("\\(([^()]*)\\)");
//        Matcher matcher = pattern.matcher(data);
//        while (matcher.find()) {
//            String group = matcher.group(1);
//            System.out.println(group);
//        }
    }
}
