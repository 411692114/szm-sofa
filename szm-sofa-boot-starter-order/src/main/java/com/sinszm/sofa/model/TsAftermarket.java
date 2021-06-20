package com.sinszm.sofa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sinszm.sofa.enums.AftermarketStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 售后表
 *
 * @author admin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ts_aftermarket", indexes = {
        @Index(name = "ts_aftermarket_order_id",columnList = "order_id"),
        @Index(name = "ts_aftermarket_user_id",columnList = "user_id"),
        @Index(name = "ts_aftermarket_aftermarket_status",columnList = "aftermarket_status")
})
@ApiModel(value = "售后记录表")
public class TsAftermarket {

    /**
     * id
     */
    @ApiModelProperty(value = "主键，售后ID")
    @Id
    @Column(name = "id", length = 32, nullable = false)
    private String id;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单ID")
    @Column(name = "order_id", length = 32, nullable = false)
    private String orderId;

    /**
     * 售后发起人用户ID
     */
    @ApiModelProperty(value = "售后创建者ID")
    @Column(name = "user_id", length = 64, nullable = false)
    private String userId;

    /**
     * 退费金额
     */
    @ApiModelProperty(value = "退款金额，精度元")
    @Column(name = "refund_fee", nullable = false)
    private Double refundFee;

    /**
     * 退货数量
     */
    @ApiModelProperty(value = "退货数量")
    @Column(name = "refund_goods_num", nullable = false)
    private Integer refundGoodsNum;

    /**
     * 退款原因
     */
    @ApiModelProperty(value = "退货原因")
    @Column(name = "refund_reasons", length = 200)
    private String refundReasons;

    /**
     * 售后状态
     */
    @ApiModelProperty(value = "售后状态")
    @Enumerated(EnumType.STRING)
    @Column(name = "aftermarket_status", nullable = false, length = 64)
    private AftermarketStatus aftermarketStatus;

    /**
     * 创建用户id
     */
    @ApiModelProperty(value = "创建者ID")
    @Column(name = "create_user_id", length = 64, nullable = false)
    private String createUserId;

    /**
     * 创建日期时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_date_time", nullable = false)
    private Date createDateTime;

    /**
     * 更新用户id
     */
    @ApiModelProperty(value = "变更者ID")
    @Column(name = "update_user_id", length = 64, nullable = false)
    private String updateUserId;

    /**
     * 更新日期时间
     */
    @ApiModelProperty(value = "变更时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_date_time", nullable = false)
    private Date updateDateTime;

}
