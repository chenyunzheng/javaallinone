package com.chris.allinone.solution.pay.timeout.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 * 订单表(Order)实体类
 *
 * @author makejava
 * @since 2023-11-19 11:50:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_order")
public class Order implements Serializable {
    private static final long serialVersionUID = -70509196441704746L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

