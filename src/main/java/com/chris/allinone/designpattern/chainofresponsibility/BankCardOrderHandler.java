package com.chris.allinone.designpattern.chainofresponsibility;

/**
 * @author chrischen
 */
public class BankCardOrderHandler implements OrderHandler{

    @Override
    public void handle(Order order, OrderHandlerChain orderHandlerChain) {
        System.out.println("BankCardOrderHandler executed");
        orderHandlerChain.next();
    }

    @Override
    public int sort() {
        return 3;
    }
}
