package com.chris.allinone.javacore.skills.proxy;

/**
 * @author chrischen
 */
public class AClass implements AInterface{
    @Override
    public String say(String name) {
        return "hello " + name;
    }
}
