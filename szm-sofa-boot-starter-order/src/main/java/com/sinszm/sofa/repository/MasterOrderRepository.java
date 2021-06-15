package com.sinszm.sofa.repository;

import com.sinszm.sofa.model.MasterOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 主订单信息存储库
 *
 * @author admin
 */
public interface MasterOrderRepository extends JpaRepository<MasterOrder, String> {

    /**
     * 获取单个订单信息
     *
     * @param orderId 订单id
     * @param orderNo 商户订单号
     * @return {@link Optional<MasterOrder>}
     */
    Optional<MasterOrder> findOneByIdOrOrderNo(String orderId, String orderNo);

}
