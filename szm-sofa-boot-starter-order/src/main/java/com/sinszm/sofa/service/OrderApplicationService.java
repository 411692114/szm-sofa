package com.sinszm.sofa.service;

import cn.hutool.core.lang.Assert;
import com.sinszm.sofa.enums.Evaluative;
import com.sinszm.sofa.model.TsAftermarket;
import com.sinszm.sofa.model.TsEvaluative;
import com.sinszm.sofa.model.TsOperationRecord;
import com.sinszm.sofa.vo.*;

import java.util.List;

import static com.sinszm.sofa.support.Constant.error;

/**
 * 订单应用服务
 *
 * @author admin
 */
public interface OrderApplicationService {

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

    /**
     * 支付订单
     * 将订单状态变更为进行中，强制需要支付参数payNo
     *
     * @param orderId   订单id
     * @param orderNo   商户订单号
     * @param payNo     支付号
     * @param payUserId 支付用户id
     * @return boolean  true表示成功，false表示失败
     */
    boolean payOrder(String orderId, String orderNo, String payNo, String payUserId);

    /**
     * 完成订单，一般是订单处理到一定阶段之后业务触发订单为已完成
     *
     * @param orderId   订单id
     * @param orderNo   商户订单号
     * @param opUserId  操作用户id
     * @return boolean  true表示成功，false表示失败
     */
    boolean completeOrder(String orderId, String orderNo, String opUserId);

    /**
     * 取消订单，订单未支付才可以直接取消订单，如果是已经支付在进行中或已完成的订单均只能走售后流程。
     *
     * @param orderId   订单id
     * @param orderNo   商户订单号
     * @param opUserId  操作用户id
     * @return boolean  true表示成功，false表示失败
     */
    boolean cancelOrder(String orderId, String orderNo, String opUserId);

    /**
     * 根据订单ID或商户订单号获取订单详情信息
     *
     * @param orderId   订单id
     * @param orderNo   商户订单号
     * @return {@link OrderDetailVo}
     */
    OrderDetailVo getOrderDetail(String orderId, String orderNo);

    /**
     * 评价订单，只有已完成的订单才可以进行评价操作
     *
     * @param orderId   订单id
     * @param orderNo   商户订单号
     * @param opUserId  操作用户id
     * @param level     评价等级
     * @param describes 评价描述
     */
    void appraiseOrder(String orderId, String orderNo, String opUserId, Evaluative level, String describes);

    /**
     * 评价订单，只有已完成的订单才可以进行评价操作
     *
     * @param evaluativeInfo 评价信息
     */
    default void appraiseOrder(EvaluativeInfo evaluativeInfo) {
        Assert.notNull(evaluativeInfo, error("评价信息不能为空"));
        evaluativeInfo.checkAll();
        appraiseOrder(evaluativeInfo.getOrderId(), null, evaluativeInfo.getUserId(), evaluativeInfo.getLevel(), evaluativeInfo.getDescribe());
    }

    /**
     * 根据订单ID或商户订单号获取订单评价信息，当订单没有完成时查询的评价信息详情设置为空信息返回
     *
     * @param orderId   订单id
     * @param orderNo   商户订单号
     * @return {@link TsEvaluative}
     */
    TsEvaluative getOrderEvaluative(String orderId, String orderNo);

    /**
     * 根据订单ID或商户订单号获取订单日志列表
     *
     * @param orderId   订单id
     * @param orderNo   商户订单号
     * @return {@link List<TsOperationRecord>}
     */
    List<TsOperationRecord> logsByOrderId(String orderId, String orderNo);

    /**
     * 创建售后，订单已支付且订单状态在进行中或已完成时才可以创建售后
     *
     * @param aftermarketInfo 售后信息
     * @return {@link String}   售后ID
     */
    default String createAftermarket(AftermarketInfo aftermarketInfo) {
        Assert.notNull(aftermarketInfo, error("售后信息不能为空"));
        aftermarketInfo.checkAll();
        return createAftermarket(
                aftermarketInfo.getOrderId(),
                null,
                aftermarketInfo.getUserId(),
                aftermarketInfo.getRefundFee(),
                aftermarketInfo.getRefundGoodsNum(),
                aftermarketInfo.getRefundReasons()
        );
    }

    /**
     * 创建售后，订单已支付且订单状态在进行中或已完成时才可以创建售后
     *
     * @param orderId        订单id
     * @param orderNo        商户订单号
     * @param opUserId       发起售后的用户id
     * @param refundFee      退费
     * @param refundGoodsNum 退货数量
     * @param refundReasons  退款原因
     * @return {@link String}   售后ID
     */
    String createAftermarket(String orderId, String orderNo, String opUserId, Double refundFee, Integer refundGoodsNum, String refundReasons);

    /**
     * 触发售后到进行中，该状态一般情况是在审核机制的保证下才能变更到进行中，普通场景时可以创建和进行中在一个流程步骤中
     * <p>
     *     当售后状态为CREATE时，调用该方法则变更售后状态为AFTER_SALES；
     *     当售后状态为AFTER_SALES时，调用该方法则变更售后状态为COMPLETED；
     * </p>
     *
     * @param aftermarketId 售后id
     * @param opUserId      操作的用户id
     * @return boolean      true表示成功，false表示失败
     */
    boolean executeAftermarket(String aftermarketId, String opUserId);

    /**
     * 取消售后，只有在售后未变更到进行中之前才可以取消售后。
     *
     * @param aftermarketId 售后id
     * @param opUserId      操作的用户id
     * @return boolean      true表示成功，false表示失败
     */
    boolean cancelAftermarket(String aftermarketId, String opUserId);

    /**
     * 根据订单ID、商户订单号或售后ID获取订单售后信息
     *
     * @param aftermarketId 售后id
     * @return {@link TsAftermarket}
     */
    TsAftermarket getAftermarketDetail(String aftermarketId);

    /**
     * 根据订单ID或商户订单号查询售后列表
     *
     * @param orderId 订单id
     * @param orderNo 商户订单号
     * @return {@link List<TsAftermarket>}
     */
    List<TsAftermarket> getAftermarketList(String orderId, String orderNo);
}
