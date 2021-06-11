package com.sinszm.sofa.repository;

import com.sinszm.sofa.model.TsOperationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 操作记录
 *
 * @author admin
 */
public interface TsOperationRecordRepository extends JpaRepository<TsOperationRecord, String> {
}
