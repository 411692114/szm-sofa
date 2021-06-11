package com.sinszm.sofa.service.impl;

import cn.hutool.core.lang.Assert;
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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

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
                .describe(goods.getDescribe())
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
                .describe("")
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
                .describe("创建了一笔订单, 商户订单号为: " + order.getOrderNo())
                .build();
        tsOperationRecordRepository.save(record);
        //处理返回信息
        OrderDetailVo vo = new OrderDetailVo();
        BeanUtils.copyProperties(order, vo);
        vo.setSeller(seller);
        vo.setBuyer(buyer);
        return vo;
    }

}
