package com.sinszm.sofa;


import com.sinszm.sofa.exception.ApiException;
import com.sinszm.sofa.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSourceInitializer;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 配置加载中心
 * @author sinszm
 */
@Slf4j
@EnableConfigurationProperties(SzmQuartzProperties.class)
public class SofaBootStarterQuartzConfiguration {

    @Resource
    private ApplicationContext context;

    @Resource
    private SzmQuartzProperties szmQuartzProperties;

    /**
     * 数据源
     *
     * @return {DataSource}
     */
    private DataSource dataSource() {
        try {
            return context.getBean(szmQuartzProperties.checkAll().getDataSource(), DataSource.class);
        } catch (BeansException e) {
            log.error("数据源读取异常!", e);
            throw new ApiException(StatusCode.SYSTEM_ERROR);
        }
    }

    /**
     * 任务调度表结构数据源初始化
     *
     * @param resourceLoader 资源加载器
     * @param properties     属性
     * @return {QuartzDataSourceInitializer}
     */
    @Bean
    @ConditionalOnMissingBean
    public QuartzDataSourceInitializer quartzDataSourceInitializer(ResourceLoader resourceLoader, QuartzProperties properties) {
        properties.getJdbc().setInitializeSchema(szmQuartzProperties.checkAll().getMode());
        properties.getJdbc().setSchema("classpath:org/quartz/impl/jdbcjobstore/tables_@@platform@@.sql");
        return new QuartzDataSourceInitializer(dataSource(), resourceLoader, properties);
    }

    /**
     * quartz调度器
     *
     * @return {SchedulerFactoryBean}
     */
    @Primary
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setDataSource(dataSource());
        bean.setJobFactory(new SpringBeanJobFactory());
        bean.setConfigLocation(new ClassPathResource("/META-INF/quartz.properties"));
        return bean;
    }

}