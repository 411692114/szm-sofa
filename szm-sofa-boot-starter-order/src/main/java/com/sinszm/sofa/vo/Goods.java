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

    public Goods checkGoodsId() {
        return this;
    }

    /**
     * 商品名称
     */
    private String goodsName;

    public Goods checkGoodsName() {
        return this;
    }

    /**
     * 规格
     */
    private String specs;

    public Goods checkSpecs() {
        return this;
    }

    /**
     * 描述
     */
    private String describe;

    public Goods checkDescribe() {
        return this;
    }

    /**
     * 单价
     */
    private Double unitPrice;

    public Goods checkUnitPrice() {
        return this;
    }

    /**
     * 商品数量
     */
    private Integer goodsNum;

    public Goods checkGoodsNum() {
        return this;
    }

    /**
     * 总价格
     */
    private Double totalPrice;

    public Goods checkTotalPrice() {
        return this;
    }

    /**
     * 折扣金额
     */
    private Double discountAmount;

    public Goods checkDiscountAmount() {
        return this;
    }

    /**
     * 支付金额
     */
    private Double payAmount;

    public Goods checkPayAmount() {
        return this;
    }

}
