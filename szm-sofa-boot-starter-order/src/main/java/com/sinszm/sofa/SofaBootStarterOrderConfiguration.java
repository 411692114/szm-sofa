package com.sinszm.sofa;

import cn.hutool.core.map.MapUtil;
import cn.hutool.system.SystemUtil;
import com.sinszm.sofa.annotation.EnableOrderDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;

/**
 * 配置加载中心
 *
 * @author sinszm
 */
@EnableConfigurationProperties(SzmOrderProperties.class)
public class SofaBootStarterOrderConfiguration {

    @EnableOrderDataSource
    @Bean(name = "sqliteDataSource")
    public DataSource dataSource() {
        String homeDir = SystemUtil.getUserInfo().getCurrentDir();
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("org.sqlite.JDBC");
        ds.setJdbcUrl("jdbc:sqlite:" + homeDir + "/order.db");
        return ds;
    }

    @Configuration
    @EnableJpaRepositories(
            basePackages = "com.sinszm.sofa.repo",
            transactionManagerRef = "jpaTransactionManager",
            entityManagerFactoryRef = "orderEntityManagerFactory"
    )
    @EnableTransactionManagement
    public static class JpaConfiguration {

        @Resource
        private SzmOrderProperties szmOrderProperties;

        @Resource
        private ApplicationContext context;

        /**
         * 数据源
         *
         * @return {@link DataSource}
         */
        private DataSource dataSource() {
            if (!szmOrderProperties.hasDataSource()) {
                return context.getBean("sqliteDataSource", DataSource.class);
            }
            try {
                return context.getBean(szmOrderProperties.getDatasource(), DataSource.class);
            } catch (BeansException e) {
                return context.getBean("sqliteDataSource", DataSource.class);
            }
        }

        @Bean
        public HibernateJpaVendorAdapter hibernateVendorAdapter() {
            HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
            adapter.setShowSql(true);
            adapter.setGenerateDdl(true);
            return adapter;
        }

        @Resource
        private HibernateJpaVendorAdapter hibernateVendorAdapter;

        @Bean
        public LocalContainerEntityManagerFactoryBean orderEntityManagerFactory() {
            LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
            bean.setDataSource(dataSource());
            bean.setJpaVendorAdapter(hibernateVendorAdapter);
            bean.setPackagesToScan("com.sinszm.sofa.model");
            bean.setJpaPropertyMap(
                    MapUtil.builder(new HashMap<String,Object>(0))
                            .put("hibernate.hbm2ddl.auto", "update")
                            .put("hibernate.enable_lazy_load_no_trans:", true)
                            .put("hibernate.show_sql", true)
                            .put("hibernate.dialect", szmOrderProperties.getDialect())
                            .put("hibernate.transaction.flush_before_completion", true)
                            .build()
            );
            return bean;
        }

        /**
         * jpa事务管理器
         *
         * @return {@link JpaTransactionManager}
         */
        @Bean
        public JpaTransactionManager jpaTransactionManager() {
            JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
            jpaTransactionManager.setDataSource(dataSource());
            jpaTransactionManager.setRollbackOnCommitFailure(true);
            return jpaTransactionManager;
        }
    }

}
