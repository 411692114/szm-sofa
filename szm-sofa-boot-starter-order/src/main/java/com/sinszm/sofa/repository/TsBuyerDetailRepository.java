package com.sinszm.sofa.repository;

import com.sinszm.sofa.model.TsBuyerDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 买家详细
 *
 * @author admin
 */
public interface TsBuyerDetailRepository extends JpaRepository<TsBuyerDetail, String> {
    /**
     * 根据订单ID获取买家信息
     *
     * @param orderId 订单id
     * @return {@link Optional<TsBuyerDetail>}
     */
    Optional<TsBuyerDetail> findOneByOrderId(String orderId);
}
