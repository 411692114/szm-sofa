package com.sinszm.sofa.repository;

import com.sinszm.sofa.model.TsOperationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 操作记录
 *
 * @author admin
 */
public interface TsOperationRecordRepository extends JpaRepository<TsOperationRecord, String> {
    /**
     * 根据订单ID顺序查找订单日志列表
     *
     * @param orderId 订单id
     * @return {List<TsOperationRecord>}
     */
    List<TsOperationRecord> findByOrderIdOrderByCreateTimeAsc(String orderId);
}
