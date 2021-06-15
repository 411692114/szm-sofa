package com.sinszm.sofa.repository;

import com.sinszm.sofa.model.TsAftermarket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 售后存储库
 *
 * @author admin
 */
public interface TsAftermarketRepository extends JpaRepository<TsAftermarket, String> {
    /**
     * 根据售后ID和订单ID查询售后信息
     *
     * @param id      id
     * @param orderId 订单id
     * @return {@link Optional<TsAftermarket>}
     */
    Optional<TsAftermarket> findOneByIdOrOrderId(String id, String orderId);

}
