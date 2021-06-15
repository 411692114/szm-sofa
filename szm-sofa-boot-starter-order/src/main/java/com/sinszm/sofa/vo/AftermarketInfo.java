package com.sinszm.sofa.vo;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import com.sinszm.sofa.util.BaseUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.RoundingMode;

import static com.sinszm.sofa.support.Constant.error;

/**
 * 售后信息
 *
 * @author admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "售后信息")
public class AftermarketInfo implements Serializable {

    private static final long serialVersionUID = 571135634691485280L;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单ID")
    private String orderId;

    public AftermarketInfo checkOrderId() {
        Assert.notNull(this.orderId, error("订单ID不能为空"));
        Assert.isFalse(BaseUtil.trim(this.orderId).length() > 32, error("订单ID不能超过32个字符"));
        this.orderId = BaseUtil.trim(this.orderId);
        return this;
    }

    /**
     * 售后发起人用户ID
     */
    @ApiModelProperty(value = "售后执行者ID")
    private String userId;

    public AftermarketInfo checkUserId() {
        Assert.notNull(this.userId, error("买家用户ID不能为空"));
        Assert.isFalse(BaseUtil.trim(this.userId).length() > 64, error("买家用户ID不能超过64个字符"));
        this.userId = BaseUtil.trim(this.userId);
        return this;
    }

    /**
     * 退费金额
     */
    @ApiModelProperty(value = "退款金额，精度元")
    private Double refundFee;

    public AftermarketInfo checkRefundFee() {
        this.refundFee = this.refundFee == null ? 0.0 : NumberUtil.round(this.refundFee, 2, RoundingMode.HALF_EVEN).doubleValue();
        return this;
    }

    /**
     * 退货数量
     */
    @ApiModelProperty(value = "退货数量")
    private Integer refundGoodsNum;

    public AftermarketInfo checkRefundGoodsNum() {
        this.refundGoodsNum = this.refundGoodsNum == null ? 0 : this.refundGoodsNum;
        return this;
    }

    /**
     * 退款原因
     */
    @ApiModelProperty(value = "退款或退货原因")
    private String refundReasons;

    public AftermarketInfo checkRefundReasons() {
        if (!BaseUtil.isEmpty(this.refundReasons)) {
            Assert.isFalse(BaseUtil.trim(this.refundReasons).length() > 200, error("退款原因不能超过200个字符"));
        }
        this.refundReasons = BaseUtil.trim(this.refundReasons);
        return this;
    }

    /**
     * 检查所有
     *
     * @return {@link AftermarketInfo}
     */
    public AftermarketInfo checkAll() {
        return this.checkOrderId()
                .checkUserId()
                .checkRefundFee()
                .checkRefundGoodsNum()
                .checkRefundReasons();
    }

}
