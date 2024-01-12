package com.chris.allinone.designpattern.decorator;

public class TrouserDecorator extends Decorator {

    @Override
    public void show() {
        System.out.print("wear trouser, ");
        super.show();
    }
}
