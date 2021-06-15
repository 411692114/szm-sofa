package com.sinszm.sofa.repository;

import com.sinszm.sofa.enums.AftermarketStatus;
import com.sinszm.sofa.model.TsAftermarket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 售后存储库
 *
 * @author admin
 */
public interface TsAftermarketRepository extends JpaRepository<TsAftermarket, String> {

    /**
     * 根据订单ID和状态集合查找售后单记录
     *
     * @param orderId 订单id
     * @param list    状态列表
     * @return {@link Optional<TsAftermarket>}
     */
    Optional<TsAftermarket> findOneByOrderIdAndAftermarketStatusIn(String orderId, List<AftermarketStatus> list);

    /**
     * 根据订单ID查找售后单列表
     *
     * @param orderId 订单id
     * @return {@link List<TsAftermarket>}
     */
    List<TsAftermarket> findByOrderIdOrderByUpdateDateTimeDesc(String orderId);
}
