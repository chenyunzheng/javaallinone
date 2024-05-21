package com.chris.allinone.javacore.juc.ext.future_disruptor.disruptor.event;


import lombok.Data;

/**
 * @author Fox
 * 消息载体(事件)
 */
@Data
public class OrderEvent {

    private long value;
    private String name;

}
