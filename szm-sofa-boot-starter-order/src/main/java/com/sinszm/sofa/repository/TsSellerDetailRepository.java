package com.sinszm.sofa.repository;

import com.sinszm.sofa.model.TsSellerDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 卖家详细
 *
 * @author admin
 */
public interface TsSellerDetailRepository extends JpaRepository<TsSellerDetail, String> {

    /**
     * 根据订单ID获取卖家详情
     *
     * @param orderId 订单id
     * @return {Optional<TsSellerDetail>}
     */
    Optional<TsSellerDetail> findOneByOrderId(String orderId);
}
