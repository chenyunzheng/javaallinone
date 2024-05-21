package com.chris.allinone.designpattern.chainofresponsibility;

public class Main {

    public static void main(String[] args) {
        Order order = Order.builder().seqId("seqId.111").skuId(22222L).build();
        OrderHandlerChain orderHandlerChain = new OrderHandlerChain();
        orderHandlerChain.execute(order);
    }
}
