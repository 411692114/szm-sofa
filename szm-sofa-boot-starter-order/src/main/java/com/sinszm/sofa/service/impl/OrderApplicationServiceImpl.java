package com.sinszm.sofa.service.impl;

import cn.hutool.core.lang.Assert;
import com.sinszm.sofa.enums.Evaluative;
import com.sinszm.sofa.enums.EvaluativeStatus;
import com.sinszm.sofa.enums.OrderStatus;
import com.sinszm.sofa.model.*;
import com.sinszm.sofa.repository.*;
import com.sinszm.sofa.service.OrderApplicationService;
import com.sinszm.sofa.util.BaseUtil;
import com.sinszm.sofa.vo.Buyer;
import com.sinszm.sofa.vo.Goods;
import com.sinszm.sofa.vo.OrderDetailVo;
import com.sinszm.sofa.vo.Seller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.sinszm.sofa.support.Constant.TRANSACTION_MANAGER;
import static com.sinszm.sofa.support.Constant.error;

/**
 * 订单应用服务impl
 *
 * @author admin
 */
@Transactional(
        transactionManager = TRANSACTION_MANAGER,
        rollbackFor = Exception.class,
        propagation= Propagation.NESTED
)
@Service
public class OrderApplicationServiceImpl implements OrderApplicationService {

    @Resource
    private MasterOrderRepository masterOrderRepository;
    @Resource
    private TsAftermarketRepository tsAftermarketRepository;
    @Resource
    private TsBuyerDetailRepository tsBuyerDetailRepository;
    @Resource
    private TsEvaluativeRepository tsEvaluativeRepository;
    @Resource
    private TsOperationRecordRepository tsOperationRecordRepository;
    @Resource
    private TsSellerDetailRepository tsSellerDetailRepository;

    /**
     * 验证参数
     *
     * @param orderNo 商户订单号
     * @param goods   货物
     * @param seller  卖方
     * @param buyer   买家
     */
    private void validateOrderParam(String orderNo, Goods goods, Seller seller, Buyer buyer) {
        Assert.notEmpty(BaseUtil.trim(orderNo), error("商户订单号不能为空"));
        Assert.notNull(goods, error("商品信息不能为空"));
        Assert.notNull(seller, error("卖家信息不能为空"));
        Assert.notNull(buyer, error("买家信息不能为空"));
        goods.checkAll();
        seller.checkAll();
        buyer.checkAll();
    }

    /**
     * 生成订单
     *
     * @param orderNo 商户订单号
     * @param goods   货物
     * @param seller  卖方
     * @param buyer   买家
     * @return {@link OrderDetailVo}
     */
    @Override
    public OrderDetailVo generateOrder(String orderNo, Goods goods, Seller seller, Buyer buyer) {
        validateOrderParam(orderNo, goods, seller, buyer);
        String orderId = BaseUtil.uuid();
        Date createTime = new Date();
        //订单信息
        MasterOrder order = MasterOrder.builder()
                .id(orderId)
                .orderNo(BaseUtil.trim(orderNo))
                .payNo("")
                .goodsId(goods.getGoodsId())
                .goodsName(goods.getGoodsName())
                .specs(goods.getSpecs())
                .describes(goods.getDescribe())
                .unitPrice(goods.getUnitPrice())
                .goodsNum(goods.getGoodsNum())
                .totalPrice(goods.getTotalPrice())
                .discountAmount(goods.getDiscountAmount())
                .payAmount(goods.getPayAmount())
                .sellerId(seller.getUserId())
                .buyerId(buyer.getUserId())
                .orderStatus(OrderStatus.WAIT_PAY)
                .createUserId(buyer.getUserId())
                .createDateTime(createTime)
                .updateUserId(buyer.getUserId())
                .updateDateTime(createTime)
                .build();
        masterOrderRepository.save(order);
        //卖家信息详情
        TsSellerDetail sellerDetail = TsSellerDetail.builder()
                .id(BaseUtil.uuid())
                .orderId(order.getId())
                .userId(seller.getUserId())
                .userName(seller.getUserName())
                .shopId(seller.getShopId())
                .shopName(seller.getShopName())
                .shopMobile(seller.getShopMobile())
                .shopAddress(seller.getShopAddress())
                .build();
        tsSellerDetailRepository.save(sellerDetail);
        //买家信息详情
        TsBuyerDetail buyerDetail = TsBuyerDetail.builder()
                .id(BaseUtil.uuid())
                .orderId(order.getId())
                .userId(buyer.getUserId())
                .userName(buyer.getUserName())
                .openId(buyer.getOpenId())
                .unionId(buyer.getUnionId())
                .mobile(buyer.getMobile())
                .address(buyer.getAddress())
                .receivingAddress(buyer.getReceivingAddress())
                .build();
        tsBuyerDetailRepository.save(buyerDetail);
        //新增评价初始记录信息
        TsEvaluative evaluative = TsEvaluative.builder()
                .id(BaseUtil.uuid())
                .orderId(order.getId())
                .userId(buyer.getUserId())
                .evalLevel(null)
                .describes("")
                .evaluativeStatus(EvaluativeStatus.TO_BE_EVALUATED)
                .createUserId(buyer.getUserId())
                .createDateTime(createTime)
                .updateUserId(buyer.getUserId())
                .updateDateTime(createTime)
                .build();
        tsEvaluativeRepository.save(evaluative);
        //创建日志
        TsOperationRecord record = TsOperationRecord.builder()
                .id(BaseUtil.uuid())
                .orderId(order.getId())
                .userId(order.getCreateUserId())
                .createTime(order.getCreateDateTime())
                .describes("创建了一笔订单, 商户订单号为: " + order.getOrderNo())
                .build();
        tsOperationRecordRepository.save(record);
        //处理返回信息
        OrderDetailVo vo = new OrderDetailVo();
        vo.setBasicInfo(order);
        vo.setSeller(seller);
        vo.setBuyer(buyer);
        return vo;
    }

    private void assertOrderIdAndOrderNo(String orderId, String orderNo, String opUserId) {
        if (BaseUtil.isEmpty(orderId) && BaseUtil.isEmpty(orderNo)) {
            throw error("订单ID与商户订单号不能同时为空").get();
        }
        if (opUserId != null) {
            Assert.notEmpty(BaseUtil.trim(opUserId), error("操作用户ID不能为空"));
            Assert.isFalse(BaseUtil.trim(opUserId).length() > 64, error("操作用户ID不能超过64个字符"));
        }
    }

    @Override
    public boolean payOrder(String orderId, String orderNo, String payNo, String payUserId) {
        assertOrderIdAndOrderNo(orderId, orderNo, null);
        Assert.notEmpty(BaseUtil.trim(payNo), error("支付号不能为空"));
        Assert.isFalse(BaseUtil.trim(payNo).length() > 64, error("支付号不能超过64个字符"));
        Assert.notEmpty(BaseUtil.trim(payUserId), error("付款用户ID不能为空"));
        Assert.isFalse(BaseUtil.trim(payUserId).length() > 64, error("付款用户ID不能超过64个字符"));
        //查找并更新订单信息
        Optional<MasterOrder> optional = masterOrderRepository.findOneByIdOrOrderNo(BaseUtil.trim(orderId), BaseUtil.trim(orderNo));
        MasterOrder masterOrder = optional.orElseThrow(error("订单信息不存在"));
        if (masterOrder.getOrderStatus() != OrderStatus.WAIT_PAY) {
            throw error("订单状态不符合待支付条件").get();
        }
        masterOrder.setOrderStatus(OrderStatus.UNDER_WAY);
        masterOrder.setPayNo(BaseUtil.trim(payNo));
        masterOrder.setUpdateUserId(BaseUtil.trim(payUserId));
        masterOrder.setUpdateDateTime(new Date());
        masterOrderRepository.save(masterOrder);
        //创建日志
        TsOperationRecord record = TsOperationRecord.builder()
                .id(BaseUtil.uuid())
                .orderId(masterOrder.getId())
                .userId(masterOrder.getUpdateUserId())
                .createTime(masterOrder.getUpdateDateTime())
                .describes("商户订单号：" + masterOrder.getOrderNo() + "，已完成了支付，订单进行中。")
                .build();
        tsOperationRecordRepository.save(record);
        return true;
    }

    @Override
    public boolean completeOrder(String orderId, String orderNo, String opUserId) {
        assertOrderIdAndOrderNo(orderId, orderNo, opUserId);
        //查找并更新订单信息
        Optional<MasterOrder> optional = masterOrderRepository.findOneByIdOrOrderNo(BaseUtil.trim(orderId), BaseUtil.trim(orderNo));
        MasterOrder masterOrder = optional.orElseThrow(error("订单信息不存在"));
        if (masterOrder.getOrderStatus() != OrderStatus.UNDER_WAY) {
            throw error("订单状态不符合进行中条件").get();
        }
        masterOrder.setOrderStatus(OrderStatus.COMPLETED);
        masterOrder.setUpdateUserId(BaseUtil.trim(opUserId));
        masterOrder.setUpdateDateTime(new Date());
        masterOrderRepository.save(masterOrder);
        //创建日志
        TsOperationRecord record = TsOperationRecord.builder()
                .id(BaseUtil.uuid())
                .orderId(masterOrder.getId())
                .userId(masterOrder.getUpdateUserId())
                .createTime(masterOrder.getUpdateDateTime())
                .describes("商户订单号：" + masterOrder.getOrderNo() + "，订单已完成。")
                .build();
        tsOperationRecordRepository.save(record);
        return false;
    }

    @Override
    public boolean cancelOrder(String orderId, String orderNo, String opUserId) {
        assertOrderIdAndOrderNo(orderId, orderNo, opUserId);
        //查找并更新订单信息
        Optional<MasterOrder> optional = masterOrderRepository.findOneByIdOrOrderNo(BaseUtil.trim(orderId), BaseUtil.trim(orderNo));
        MasterOrder masterOrder = optional.orElseThrow(error("订单信息不存在"));
        if (masterOrder.getOrderStatus() != OrderStatus.WAIT_PAY) {
            throw error("订单状态不符合待支付条件").get();
        }
        masterOrder.setOrderStatus(OrderStatus.CANCEL);
        masterOrder.setUpdateUserId(BaseUtil.trim(opUserId));
        masterOrder.setUpdateDateTime(new Date());
        masterOrderRepository.save(masterOrder);
        //创建日志
        TsOperationRecord record = TsOperationRecord.builder()
                .id(BaseUtil.uuid())
                .orderId(masterOrder.getId())
                .userId(masterOrder.getUpdateUserId())
                .createTime(masterOrder.getUpdateDateTime())
                .describes("商户订单号：" + masterOrder.getOrderNo() + "，订单已取消。")
                .build();
        tsOperationRecordRepository.save(record);
        return false;
    }

    @Override
    public OrderDetailVo getOrderDetail(String orderId, String orderNo) {
        assertOrderIdAndOrderNo(orderId, orderNo, null);
        Optional<MasterOrder> optional = masterOrderRepository.findOneByIdOrOrderNo(BaseUtil.trim(orderId), BaseUtil.trim(orderNo));
        MasterOrder masterOrder = optional.orElseThrow(error("订单信息不存在"));
        TsBuyerDetail buyerDetail = tsBuyerDetailRepository.findOneByOrderId(masterOrder.getId()).orElseThrow(error("未能获取买家信息"));
        TsSellerDetail sellerDetail = tsSellerDetailRepository.findOneByOrderId(masterOrder.getId()).orElseThrow(error("未能获取卖家信息"));
        //处理返回信息
        OrderDetailVo vo = new OrderDetailVo();
        vo.setBasicInfo(masterOrder);
        vo.setSeller(
                Seller.builder()
                        .userId(sellerDetail.getUserId())
                        .userName(sellerDetail.getUserName())
                        .shopId(sellerDetail.getShopId())
                        .shopName(sellerDetail.getShopName())
                        .shopMobile(sellerDetail.getShopMobile())
                        .shopAddress(sellerDetail.getShopAddress())
                        .build()
        );
        vo.setBuyer(
                Buyer.builder()
                        .userId(buyerDetail.getUserId())
                        .userName(buyerDetail.getUserName())
                        .openId(buyerDetail.getOpenId())
                        .unionId(buyerDetail.getUnionId())
                        .mobile(buyerDetail.getMobile())
                        .address(buyerDetail.getAddress())
                        .receivingAddress(buyerDetail.getReceivingAddress())
                        .build()
        );
        return vo;
    }

    @Override
    public void appraiseOrder(String orderId, String orderNo, String opUserId, Evaluative level, String describes) {
        assertOrderIdAndOrderNo(orderId, orderNo, opUserId);
        Assert.notNull(level, error("评价等级不能为空"));
        //查找并更新订单信息
        Optional<MasterOrder> optional = masterOrderRepository.findOneByIdOrOrderNo(BaseUtil.trim(orderId), BaseUtil.trim(orderNo));
        MasterOrder masterOrder = optional.orElseThrow(error("订单信息不存在"));
        TsEvaluative evaluative = tsEvaluativeRepository.findOneByOrderIdAndEvaluativeStatus(masterOrder.getId(), EvaluativeStatus.TO_BE_EVALUATED)
                .orElseThrow(error("待评价记录不存在"));
        evaluative.setEvalLevel(level);
        evaluative.setDescribes(BaseUtil.trim(describes));
        evaluative.setEvaluativeStatus(EvaluativeStatus.EVALUATED);
        evaluative.setUpdateUserId(BaseUtil.trim(opUserId));
        evaluative.setUpdateDateTime(new Date());
        tsEvaluativeRepository.save(evaluative);
        //创建日志
        TsOperationRecord record = TsOperationRecord.builder()
                .id(BaseUtil.uuid())
                .orderId(masterOrder.getId())
                .userId(evaluative.getUpdateUserId())
                .createTime(evaluative.getUpdateDateTime())
                .describes("商户订单号：" + masterOrder.getOrderNo() + "，已完成评价。")
                .build();
        tsOperationRecordRepository.save(record);
    }

    @Override
    public TsEvaluative getOrderEvaluative(String orderId, String orderNo) {
        assertOrderIdAndOrderNo(orderId, orderNo, null);
        MasterOrder masterOrder = masterOrderRepository.findOneByIdOrOrderNo(BaseUtil.trim(orderId), BaseUtil.trim(orderNo))
                .orElseThrow(error("订单信息不存在"));
        if (masterOrder.getOrderStatus() != OrderStatus.COMPLETED) {
            return null;
        }
        return tsEvaluativeRepository.findOneByOrderId(masterOrder.getId()).orElseThrow(error("评价记录不存在"));
    }

    @Override
    public List<TsOperationRecord> logsByOrderId(String orderId, String orderNo) {
        assertOrderIdAndOrderNo(orderId, orderNo, null);
        MasterOrder masterOrder = masterOrderRepository.findOneByIdOrOrderNo(BaseUtil.trim(orderId), BaseUtil.trim(orderNo))
                .orElseThrow(error("订单信息不存在"));
        return tsOperationRecordRepository.findByOrderIdOrderByCreateTimeAsc(masterOrder.getId());
    }

}
