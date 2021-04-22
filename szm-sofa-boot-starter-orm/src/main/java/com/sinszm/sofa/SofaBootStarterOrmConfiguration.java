package com.sinszm.sofa;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.sinszm.sofa.annotation.EnableORM;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.datasource.platform:other}")
    private String dbType;

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

    /**
     * 分页插件属性配置
     * @return  拦截器
     */
    private PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOverflow(false);
        paginationInnerInterceptor.setMaxLimit(500L);
        paginationInnerInterceptor.setDbType(DbType.getDbType("all".equals(dbType) ? "other" : dbType));
        return paginationInnerInterceptor;
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //分页
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        //乐观锁
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.setUseGeneratedShortKey(true);
            configuration.setUseDeprecatedExecutor(false);
        };
    }

}
