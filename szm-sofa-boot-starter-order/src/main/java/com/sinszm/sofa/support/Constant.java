package com.sinszm.sofa.support;

import com.sinszm.sofa.exception.ApiException;
import com.sinszm.sofa.util.BaseUtil;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * 常数
 *
 * @author admin
 */
public class Constant {

    /**
     * 事务管理器名称常量
     */
    public static final String TRANSACTION_MANAGER = "jpaTransactionManager";

    /**
     * 实体管理器工厂参考
     */
    public static final String ENTITY_MANAGER_FACTORY_REF = "masterTsEntityManagerFactory";

    /**
     * 默认的数据源名称
     */
    public static final String DEFAULT_DATASOURCE_NAME = "h2DataSource";

    /**
     * 默认数据源方言
     */
    public static final String DEFAULT_DATASOURCE_DIALECT = "org.hibernate.dialect.H2Dialect";

    /**
     * 验证的错误信息组装
     *
     * @param message 消息
     * @return {Supplier<ApiException>}
     */
    public static Supplier<ApiException> error(String message) {
        String msg = Optional.ofNullable(message).orElse("系统异常");
        return () -> new ApiException("201", BaseUtil.trim(msg));
    }

}
