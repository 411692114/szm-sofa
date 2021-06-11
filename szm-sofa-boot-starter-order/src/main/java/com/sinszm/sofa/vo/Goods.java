package com.sinszm.sofa.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品信息
 *
 * @author admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Goods implements Serializable {

    private static final long serialVersionUID = 709361594869050702L;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 规格
     */
    private String specs;

    /**
     * 描述
     */
    private String describe;

    /**
     * 单价
     */
    private Double unitPrice;

    /**
     * 商品数量
     */
    private Integer goodsNum;

    /**
     * 总价格
     */
    private Double totalPrice;

    /**
     * 折扣金额
     */
    private Double discountAmount;

    /**
     * 支付金额
     */
    private Double payAmount;

}
