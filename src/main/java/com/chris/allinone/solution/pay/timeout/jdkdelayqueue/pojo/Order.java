package com.chris.allinone.solution.pay.timeout.jdkdelayqueue.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Order {
    /**
     * 主键
     */
    private Long id;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 订单状态：0-初始化；1-完成；2-取消；3-删除
     */
    private Short orderStatus;
    /**
     * 支付状态：0-未支付；1-已支付
     */
    private Short payStatus;
    /**
     * 订单创建时间
     */
    private Date createTime;
    /**
     * 订单更新时间
     */
    private Date updateTime;
}