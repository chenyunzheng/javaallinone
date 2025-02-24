package com.chris;

/**
 * @author chrischen
 * @date 2025/2/19
 * @desc TODO描述主要功能
 */
public class Warpper {

    private final String data;

    public Warpper(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "this is " + data;
    }
}
