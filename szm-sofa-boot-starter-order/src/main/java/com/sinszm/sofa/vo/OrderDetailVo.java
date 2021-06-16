package com.sinszm.sofa.vo;

import com.sinszm.sofa.model.MasterOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "订单明细信息")
public class OrderDetailVo {

    /**
     * 订单基本信息
     */
    @ApiModelProperty(value = "订单基本信息")
    private MasterOrder basicInfo;

    /**
     * 买家
     */
    @ApiModelProperty(value = "买家信息")
    private Buyer buyer;

    /**
     * 卖方
     */
    @ApiModelProperty(value = "卖家信息")
    private Seller seller;
}
