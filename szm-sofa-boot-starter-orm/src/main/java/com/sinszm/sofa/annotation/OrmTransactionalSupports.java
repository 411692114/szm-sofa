package com.sinszm.sofa.annotation;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

import static com.sinszm.sofa.SofaBootStarterOrmConfiguration.TRANSACTION_MANAGER;

/**
 * ORM事务封装注解
 *
 * @author chenjianbo
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Transactional(rollbackFor = Exception.class, transactionManager = TRANSACTION_MANAGER, propagation = Propagation.SUPPORTS)
public @interface OrmTransactionalSupports {
}
