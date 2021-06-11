package com.sinszm.sofa.repository;

import com.sinszm.sofa.model.MasterOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 主订单信息存储库
 *
 * @author admin
 */
public interface MasterOrderRepository extends JpaRepository<MasterOrder, String> {
}
