package com.sinszm.sofa.repository;

import com.sinszm.sofa.enums.EvaluativeStatus;
import com.sinszm.sofa.model.TsEvaluative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 评价存储库
 *
 * @author admin
 */
public interface TsEvaluativeRepository extends JpaRepository<TsEvaluative, String> {
    /**
     * 根据订单ID和评价状态查询待评价记录
     *
     * @param orderId          订单id
     * @param evaluativeStatus 评价状态
     * @return {@link Optional<TsEvaluative>}
     */
    Optional<TsEvaluative> findOneByOrderIdAndEvaluativeStatus(String orderId, EvaluativeStatus evaluativeStatus);

    /**
     * 根据订单id查找评价信息
     *
     * @param orderId 订单id
     * @return {@link Optional<TsEvaluative>}
     */
    Optional<TsEvaluative> findOneByOrderId(String orderId);
}
