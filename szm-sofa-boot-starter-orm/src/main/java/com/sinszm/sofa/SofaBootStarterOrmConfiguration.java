package com.sinszm.sofa;


import com.sinszm.sofa.annotation.EnableORM;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 配置加载中心
 * @author sinszm
 */
@EnableORM
@MapperScan("${orm.base-package:}")
@PropertySource("classpath:settings.properties")
public class SofaBootStarterOrmConfiguration implements TransactionManagementConfigurer {

    public static final String TRANSACTION_MANAGER = "mdbManager";

    @Resource
    private DataSource dataSource;

    @Bean(TRANSACTION_MANAGER)
    @Primary
    public PlatformTransactionManager mdbManager() {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        manager.setRollbackOnCommitFailure(true);
        return manager;
    }

    @Resource
    @Qualifier(TRANSACTION_MANAGER)
    private PlatformTransactionManager mdbManager;

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return mdbManager;
    }

}
