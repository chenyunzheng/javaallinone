package com.chris.allinone.designpattern.chainofresponsibility;

import lombok.Builder;
import lombok.Data;

/**
 * @author chrischen
 */
@Data
@Builder
public class Order {

    /**
     * 请求唯一序列ID
     */
    private String seqId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 产品skuId
     */
    private Long skuId;

    /**
     * 下单数量
     */
    private Integer amount;

    /**
     * 用户收货地址ID
     */
    private String userAddressId;

}
