package com.sinszm.sofa.support;

/**
 * 常数
 *
 * @author admin
 * @date 2021/06/11
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

}
