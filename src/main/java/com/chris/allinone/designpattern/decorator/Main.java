package com.chris.allinone.designpattern.decorator;

public class Main {

    public static void main(String[] args) {
        Person person = new Person("chris");

        Decorator decorator = new Decorator();
        ShirtDecorator shirtDecorator = new ShirtDecorator();
        TrouserDecorator trouserDecorator = new TrouserDecorator();

        decorator.decorator(person);
        shirtDecorator.decorator(decorator);
        trouserDecorator.decorator(shirtDecorator);
        trouserDecorator.show();
    }
}
