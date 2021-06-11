package com.sinszm.sofa.service.impl;

import com.sinszm.sofa.repository.MasterOrderRepository;
import com.sinszm.sofa.service.OrderApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.sinszm.sofa.support.Constant.TRANSACTION_MANAGER;

/**
 * 订单应用服务impl
 *
 * @author admin
 * @date 2021/06/11
 */
@Transactional(transactionManager = TRANSACTION_MANAGER, rollbackFor = Exception.class)
@Service
public class OrderApplicationServiceImpl implements OrderApplicationService {

    @Resource
    private MasterOrderRepository masterOrderRepository;

}
