package com.sinszm.sofa.service.impl;

import com.sinszm.sofa.repository.MasterOrderRepository;
import com.sinszm.sofa.service.OrderApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.sinszm.sofa.support.Constant.TRANSACTION_MANAGER;

/**
 * 订单应用服务impl
 *
 * @author admin
 */
@Transactional(
        transactionManager = TRANSACTION_MANAGER,
        rollbackFor = Exception.class,
        propagation= Propagation.NESTED
)
@Service
public class OrderApplicationServiceImpl implements OrderApplicationService {

    @Resource
    private MasterOrderRepository masterOrderRepository;

}
