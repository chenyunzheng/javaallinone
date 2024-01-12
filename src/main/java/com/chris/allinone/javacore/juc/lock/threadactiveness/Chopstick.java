package com.chris.allinone.javacore.juc.lock.threadactiveness;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author chrischen
 */
@Data
@AllArgsConstructor
public class Chopstick {
    private int number;

    @Override
    public String toString() {
        return "筷子{" + number + '}';
    }
}
