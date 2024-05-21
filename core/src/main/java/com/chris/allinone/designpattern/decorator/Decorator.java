package com.chris.allinone.designpattern.decorator;

public class Decorator implements Behavior {

    private Behavior behavior;

    public void decorator(Behavior behavior) {
        this.behavior = behavior;
    }

    @Override
    public void show() {
        if (behavior != null) {
            behavior.show();
        }
    }
}
