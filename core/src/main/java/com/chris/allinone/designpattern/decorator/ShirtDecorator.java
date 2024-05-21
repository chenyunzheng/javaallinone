package com.chris.allinone.designpattern.decorator;

public class ShirtDecorator extends Decorator {

    @Override
    public void show() {
        System.out.print("wear shirt, ");
        super.show();
    }
}
