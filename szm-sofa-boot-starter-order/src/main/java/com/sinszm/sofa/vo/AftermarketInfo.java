package com.sinszm.sofa.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 售后信息
 *
 * @author admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AftermarketInfo implements Serializable {

    private static final long serialVersionUID = 571135634691485280L;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 售后发起人用户ID
     */
    private String userId;

    /**
     * 退费金额
     */
    private Double refundFee;

    /**
     * 退货数量
     */
    private Integer refundGoodsNum;

    /**
     * 退款原因
     */
    private String refundReasons;


}
