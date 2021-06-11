package com.sinszm.sofa.vo;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import com.sinszm.sofa.util.BaseUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.RoundingMode;

import static com.sinszm.sofa.support.Constant.error;

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
        if (!BaseUtil.isEmpty(this.goodsId)) {
            Assert.isFalse(BaseUtil.trim(this.goodsId).length() > 64, error("商品ID不能超过64个字符"));
        }
        this.goodsId = BaseUtil.trim(this.goodsId);
        return this;
    }

    /**
     * 商品名称
     */
    private String goodsName;

    public Goods checkGoodsName() {
        if (!BaseUtil.isEmpty(this.goodsName)) {
            Assert.isFalse(BaseUtil.trim(this.goodsName).length() > 200, error("商品名称不能超过200个字符"));
        }
        this.goodsName = BaseUtil.trim(this.goodsName);
        return this;
    }

    /**
     * 规格
     */
    private String specs;

    public Goods checkSpecs() {
        if (!BaseUtil.isEmpty(this.specs)) {
            Assert.isFalse(BaseUtil.trim(this.specs).length() > 200, error("商品规格不能超过200个字符"));
        }
        this.specs = BaseUtil.trim(this.specs);
        return this;
    }

    /**
     * 描述
     */
    private String describe;

    public Goods checkDescribe() {
        if (!BaseUtil.isEmpty(this.describe)) {
            Assert.isFalse(BaseUtil.trim(this.describe).length() > 200, error("商品描述不能超过200个字符"));
        }
        this.describe = BaseUtil.trim(this.describe);
        return this;
    }

    /**
     * 单价（精度：元）
     */
    private Double unitPrice;

    public Goods checkUnitPrice() {
        Assert.notNull(this.unitPrice, error("商品单价不能为空"));
        this.unitPrice = NumberUtil.round(this.unitPrice, 2, RoundingMode.HALF_EVEN).doubleValue();
        return this;
    }

    /**
     * 商品数量
     */
    private Integer goodsNum;

    public Goods checkGoodsNum() {
        Assert.notNull(this.goodsNum, error("商品数量不能为空"));
        return this;
    }

    /**
     * 总价格
     */
    private Double totalPrice;

    public Goods checkTotalPrice() {
        Assert.notNull(this.totalPrice, error("商品总价不能为空"));
        this.totalPrice = NumberUtil.round(this.totalPrice, 2, RoundingMode.HALF_EVEN).doubleValue();
        return this;
    }

    /**
     * 折扣金额
     */
    private Double discountAmount;

    public Goods checkDiscountAmount() {
        Assert.notNull(this.discountAmount, error("商品折扣金额不能为空"));
        this.discountAmount = NumberUtil.round(this.discountAmount, 2, RoundingMode.HALF_EVEN).doubleValue();
        return this;
    }

    /**
     * 支付金额
     */
    private Double payAmount;

    public Goods checkPayAmount() {
        Assert.notNull(this.payAmount, error("商品支付金额不能为空"));
        this.payAmount = NumberUtil.round(this.payAmount, 2, RoundingMode.HALF_EVEN).doubleValue();
        return this;
    }

    /**
     * 检查所有
     *
     * @return {@link Goods}
     */
    public Goods checkAll() {
        return this.checkGoodsId()
                .checkGoodsName()
                .checkSpecs()
                .checkDescribe()
                .checkUnitPrice()
                .checkGoodsNum()
                .checkTotalPrice()
                .checkDiscountAmount()
                .checkPayAmount();
    }

}
