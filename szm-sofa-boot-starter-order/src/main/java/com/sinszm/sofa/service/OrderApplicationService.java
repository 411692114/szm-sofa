package com.sinszm.sofa.service;

import com.sinszm.sofa.vo.Buyer;
import com.sinszm.sofa.vo.Goods;
import com.sinszm.sofa.vo.OrderDetailVo;
import com.sinszm.sofa.vo.Seller;

/**
 * 订单应用服务
 *
 * @author admin
 */
public interface OrderApplicationService {

    // Fixme com.sinszm.sofa.model 和 com.sinszm.sofa.vo 包中需要添加swagger支持

    /**
     * 生成订单
     *
     * @param orderNo 商户订单号
     * @param goods   货物
     * @param seller  卖方
     * @param buyer   买家
     * @return {@link OrderDetailVo}
     */
    OrderDetailVo generateOrder(String orderNo, Goods goods, Seller seller, Buyer buyer);

    // TODO: 2021/6/11 支付订单，将订单状态变更为进行中，强制需要支付参数payNo

    // TODO: 2021/6/11 完成订单，一般是订单处理到一定阶段之后业务触发订单为已完成

    // TODO: 2021/6/11 取消订单，订单未支付才可以直接取消订单，如果是已经支付在进行中或已完成的订单均只能走售后流程。

    // TODO 根据订单ID或商户订单号获取订单详情信息




    // TODO: 2021/6/11 评价订单，只有已完成的订单才可以进行评价操作

    // TODO 根据订单ID或商户订单号获取订单评价信息，当订单没有完成时查询的评价信息详情设置为空信息返回




    // TODO: 2021/6/11 创建售后，订单已支付且订单状态在进行中或已完成时才可以创建售后

    // TODO: 2021/6/11 触发售后到进行中，该状态一般情况是在审核机制的保证下才能变更到进行中，普通场景时可以创建和进行中在一个流程步骤中

    // TODO: 2021/6/11 处理售后已完成，在售后处理到一定阶段时可以触发售后流程为已完成

    // TODO: 2021/6/11 取消售后，只有在售后未变更到进行中之前才可以取消售后。

    // TODO 根据订单ID或商户订单号获取订单售后信息
}
