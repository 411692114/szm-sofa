package com.sinszm.sofa.vo;

import com.sinszm.sofa.model.MasterOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单的详细信息
 *
 * @author sinszm
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {

    /**
     * 订单基本信息
     */
    private MasterOrder basicInfo;

    /**
     * 买家
     */
    private Buyer buyer;

    /**
     * 卖方
     */
    private Seller seller;
}
