package com.chris.allinone.designpattern.chainofresponsibility;

/**
 * @author chrischen
 */
public class RepeatOrderHandler implements OrderHandler {

    @Override
    public void handle(Order order, OrderHandlerChain orderHandlerChain) {
        String seqId = order.getSeqId();
        System.out.println("Not repeat seqId = " + seqId);
        orderHandlerChain.next();
    }

    @Override
    public int sort() {
        return 1;
    }
}
