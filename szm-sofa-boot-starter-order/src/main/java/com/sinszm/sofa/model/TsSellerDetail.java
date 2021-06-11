package com.sinszm.sofa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 订单卖家信息表
 *
 * @author admin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ts_seller_detail", indexes = {
        @Index(name = "ts_seller_order_id",columnList = "order_id"),
        @Index(name = "ts_seller_user_id",columnList = "user_id"),
        @Index(name = "ts_seller_shop_id",columnList = "shop_id"),
        @Index(name = "ts_seller_shop_mobile",columnList = "shop_mobile")
})
public class TsSellerDetail {

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
     * 店铺ID
     */
    @Column(name = "shop_id", length = 64)
    private String shopId;

    /**
     * 店铺名称
     */
    @Column(name = "shop_name", length = 200)
    private String shopName;

    /**
     * 店铺电话
     */
    @Column(name = "shop_mobile", length = 13)
    private String shopMobile;

    /**
     * 店铺地址
     */
    @Column(name = "shop_address", length = 200)
    private String shopAddress;

}
