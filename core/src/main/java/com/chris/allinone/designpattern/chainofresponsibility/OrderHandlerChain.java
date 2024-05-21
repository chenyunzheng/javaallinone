package com.chris.allinone.designpattern.chainofresponsibility;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chrischen
 */
public class OrderHandlerChain {

    private final List<OrderHandler> orderHandlerList;
    private int position;

    public OrderHandlerChain() {
        this.orderHandlerList = Lists.newArrayList(
            new RepeatOrderHandler(), new ValidOrderHandler(), new BankCardOrderHandler()
        ).stream().sorted(Comparator.comparingInt(OrderHandler::sort)).collect(Collectors.toList());
        this.position = 0;
    }

    public void execute(Order order) {
        int size = this.orderHandlerList.size();
        for (int i = 0; i < size && i <= this.position; i++) {
            this.orderHandlerList.get(i).handle(order, this);
        }
        if (this.position < size - 1) {
            System.out.println("chain stop earlier at " + this.orderHandlerList.get(this.position));
        }
    }

    public void next() {
        this.position++;
    }
}
