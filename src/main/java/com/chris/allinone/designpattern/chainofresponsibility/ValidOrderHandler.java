package com.chris.allinone.designpattern.chainofresponsibility;

/**
 * @author chrischen
 */
public class ValidOrderHandler implements OrderHandler{

    @Override
    public void handle(Order order, OrderHandlerChain orderHandlerChain) {
        Long skuId = order.getSkuId();
        System.out.println("Parameters valid, skuId = " + skuId);
        orderHandlerChain.next();
    }

    @Override
    public int sort() {
        return 2;
    }
}
