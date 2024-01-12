package com.chris.allinone.designpattern.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person implements Behavior {

    String name;

    @Override
    public void show() {
        System.out.println("装饰" + name);
    }
}
