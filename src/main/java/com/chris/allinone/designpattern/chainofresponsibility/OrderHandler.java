package com.chris.allinone.designpattern.chainofresponsibility;

/**
 * @author chrischen
 */
public interface OrderHandler {

    /**
     * 处理订单逻辑
     * @param order 订单实体
     * @param orderHandlerChain 订单处理器调用链
     */
    void handle(Order order, OrderHandlerChain orderHandlerChain);

    /**
     * 执行顺序
     * @return 执行顺序
     */
    int sort();
}
