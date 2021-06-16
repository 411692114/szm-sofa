package com.sinszm.sofa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 订单买家信息表
 *
 * @author admin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ts_buyer_detail", indexes = {
        @Index(name = "ts_buyer_order_id",columnList = "order_id"),
        @Index(name = "ts_buyer_user_id",columnList = "user_id"),
        @Index(name = "ts_buyer_open_id",columnList = "open_id"),
        @Index(name = "ts_buyer_union_id",columnList = "union_id"),
        @Index(name = "ts_buyer_mobile",columnList = "mobile")
})
public class TsBuyerDetail {

    /**
     * id
     */
    @Id
    @Column(name = "id", length = 32, nullable = false)
    private String id;

    /**
     * 订单id
     */
    @Column(name = "order_id", length = 32, nullable = false)
    private String orderId;

    /**
     * 用户id
     */
    @Column(name = "user_id", length = 64, nullable = false)
    private String userId;

    /**
     * 用户名
     */
    @Column(name = "user_name", length = 200)
    private String userName;

    /**
     * 开放id
     */
    @Column(name = "open_id", length = 64)
    private String openId;

    /**
     * 聚合ID
     */
    @Column(name = "union_id", length = 64)
    private String unionId;

    /**
     * 电话
     */
    @Column(name = "mobile", length = 13)
    private String mobile;

    /**
     * 地址
     */
    @Column(name = "address", length = 200)
    private String address;

    /**
     * 收货地址
     */
    @Column(name = "receiving_address", length = 200)
    private String receivingAddress;

}
