package com.sinszm.sofa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sinszm.sofa.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 主订单表
 *
 * @author admin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ts_order", indexes = {
        @Index(name = "ts_order_order_no",columnList = "order_no"),
        @Index(name = "ts_order_pay_no",columnList = "pay_no"),
        @Index(name = "ts_order_goods_id",columnList = "goods_id"),
        @Index(name = "ts_order_seller_id",columnList = "seller_id"),
        @Index(name = "ts_order_buyer_id",columnList = "buyer_id"),
        @Index(name = "ts_order_order_status",columnList = "order_status")
})
public class MasterOrder {

    /**
     * id
     */
    @Id
    @Column(name = "id", length = 32, nullable = false)
    private String id;

    /**
     * 商户订单号
     */
    @Column(name = "order_no", length = 64, nullable = false, unique = true)
    private String orderNo;

    /**
     * 支付号
     */
    @Column(name = "pay_no", length = 64)
    private String payNo;

    /**
     * 商品id
     */
    @Column(name = "goods_id", length = 64)
    private String goodsId;

    /**
     * 商品名称
     */
    @Column(name = "goods_name", length = 200)
    private String goodsName;

    /**
     * 规格
     */
    @Column(name = "specs", length = 200)
    private String specs;

    /**
     * 描述
     */
    @Column(name = "describes", length = 200)
    private String describes;

    /**
     * 单价
     */
    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    /**
     * 商品数量
     */
    @Column(name = "goods_num", nullable = false)
    private Integer goodsNum;

    /**
     * 总价格
     */
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    /**
     * 折扣金额
     */
    @Column(name = "discount_amount", nullable = false)
    private Double discountAmount;

    /**
     * 支付金额
     */
    @Column(name = "pay_amount", nullable = false)
    private Double payAmount;

    /**
     * 卖方用户ID
     */
    @Column(name = "seller_id", length = 64)
    private String sellerId;

    /**
     * 买家用户ID
     */
    @Column(name = "buyer_id", length = 64)
    private String buyerId;

    /**
     * 订单状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 64)
    private OrderStatus orderStatus;

    /**
     * 创建用户id
     */
    @Column(name = "create_user_id", length = 64, nullable = false)
    private String createUserId;

    /**
     * 创建日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_date_time", nullable = false)
    private Date createDateTime;

    /**
     * 更新用户id
     */
    @Column(name = "update_user_id", length = 64, nullable = false)
    private String updateUserId;

    /**
     * 更新日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_date_time", nullable = false)
    private Date updateDateTime;

}
