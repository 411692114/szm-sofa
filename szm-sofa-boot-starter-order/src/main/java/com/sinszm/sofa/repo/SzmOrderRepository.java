package com.sinszm.sofa.repo;

import com.sinszm.sofa.model.SzmOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 主订单信息存储库
 *
 * @author admin
 */
public interface SzmOrderRepository extends JpaRepository<SzmOrder, String> {
}
