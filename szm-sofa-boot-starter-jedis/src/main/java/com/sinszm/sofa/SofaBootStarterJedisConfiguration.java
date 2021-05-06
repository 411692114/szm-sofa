package com.sinszm.sofa;


import cn.hutool.core.util.StrUtil;
import com.sinszm.sofa.annotation.EnableJedis;
import com.sinszm.sofa.enums.JedisModel;
import com.sinszm.sofa.util.BaseUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_EVICTION_POLICY_CLASS_NAME;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_JMX_NAME_BASE;

/**
 * 配置加载中心
 * @author sinszm
 */
@EnableConfigurationProperties(JedisProperties.class)
public class SofaBootStarterJedisConfiguration {

    @Resource
    private JedisProperties jedisProperties;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(jedisProperties.getMaxTotal());
        config.setMaxIdle(jedisProperties.getMaxIdle());
        config.setMinIdle(jedisProperties.getMinIdle());
        config.setLifo(jedisProperties.isLifo());
        config.setFairness(jedisProperties.isFairness());
        config.setMaxWaitMillis(jedisProperties.getMaxWaitMillis());
        config.setMinEvictableIdleTimeMillis(jedisProperties.getMinEvictableIdleTimeMillis());
        config.setEvictorShutdownTimeoutMillis(jedisProperties.getEvictorShutdownTimeoutMillis());
        config.setSoftMinEvictableIdleTimeMillis(jedisProperties.getSoftMinEvictableIdleTimeMillis());
        config.setNumTestsPerEvictionRun(jedisProperties.getNumTestsPerEvictionRun());
        config.setEvictionPolicyClassName(BaseUtil.isEmpty(jedisProperties.getEvictionPolicyClassName()) ?
                DEFAULT_EVICTION_POLICY_CLASS_NAME : jedisProperties.getEvictionPolicyClassName());
        config.setTestOnCreate(jedisProperties.isTestOnCreate());
        config.setTestOnBorrow(jedisProperties.isTestOnBorrow());
        config.setTestOnReturn(jedisProperties.isTestOnReturn());
        config.setTestWhileIdle(jedisProperties.isTestWhileIdle());
        config.setTimeBetweenEvictionRunsMillis(jedisProperties.getTimeBetweenEvictionRunsMillis());
        config.setBlockWhenExhausted(jedisProperties.isBlockWhenExhausted());
        config.setJmxEnabled(jedisProperties.isJmxEnabled());
        config.setJmxNamePrefix(BaseUtil.isEmpty(jedisProperties.getJmxNamePrefix()) ? "pool" : jedisProperties.getJmxNamePrefix());
        config.setJmxNameBase(BaseUtil.isEmpty(jedisProperties.getJmxNameBase()) ? DEFAULT_JMX_NAME_BASE : jedisProperties.getJmxNameBase());
        return config;
    }

    @Resource
    private JedisPoolConfig jedisPoolConfig;

    private int timeout(int num) {
        return num <= 0 ? 2000 : num;
    }

    @ConditionalOnMissingBean
    @EnableJedis(JedisModel.CLUSTER)
    @Bean(destroyMethod = "close")
    @Scope("prototype")
    public JedisCluster jedisCluster() {
        Set<HostAndPort> sets = jedisProperties.getAddress()
                .stream()
                .map(HostAndPort::parseString)
                .collect(Collectors.toSet());
        return new JedisCluster(
                sets,
                timeout(jedisProperties.getTimeout()),
                timeout(jedisProperties.getTimeout()),
                5,
                StrUtil.isEmpty(jedisProperties.getUser()) ? null : jedisProperties.getUser(),
                StrUtil.isEmpty(jedisProperties.getPassword()) ? null : jedisProperties.getPassword(),
                StrUtil.isEmpty(jedisProperties.getClientName()) ? null : jedisProperties.getClientName(),
                jedisPoolConfig
        );
    }

    @ConditionalOnMissingBean
    @EnableJedis({JedisModel.STANDALONE, JedisModel.SENTINEL})
    @Bean(destroyMethod = "close")
    @Scope("prototype")
    public Jedis jedis() {
        if (jedisProperties.getModel() == JedisModel.STANDALONE) {
            List<HostAndPort> sets = jedisProperties.getAddress()
                    .stream()
                    .map(HostAndPort::parseString)
                    .collect(Collectors.toList());
            JedisClientConfig clientConfig = DefaultJedisClientConfig.builder()
                    .connectionTimeoutMillis(timeout(jedisProperties.getTimeout()))
                    .socketTimeoutMillis(timeout(jedisProperties.getTimeout()))
                    .clientName(StrUtil.isEmpty(jedisProperties.getClientName()) ? null : jedisProperties.getClientName())
                    .password(StrUtil.isEmpty(jedisProperties.getPassword()) ? null : jedisProperties.getPassword())
                    .user(StrUtil.isEmpty(jedisProperties.getUser()) ? null : jedisProperties.getUser())
                    .database(jedisProperties.getDatabase() < 0 || jedisProperties.getDatabase() > 15 ? 0 : jedisProperties.getDatabase())
                    .build();
            JedisPool jedisPool = new JedisPool(jedisPoolConfig, sets.get(0) , clientConfig);
            return jedisPool.getResource();
        }
        JedisSentinelPool sentinelPool = new JedisSentinelPool(
                StrUtil.trimToEmpty(jedisProperties.getMasterName()),
                jedisProperties.getAddress(),
                jedisPoolConfig,
                timeout(jedisProperties.getTimeout()),
                timeout(jedisProperties.getTimeout()),
                StrUtil.isEmpty(jedisProperties.getUser()) ? null : jedisProperties.getUser(),
                StrUtil.isEmpty(jedisProperties.getPassword()) ? null : jedisProperties.getPassword(),
                jedisProperties.getDatabase() < 0 || jedisProperties.getDatabase() > 15 ? 0 : jedisProperties.getDatabase(),
                StrUtil.isEmpty(jedisProperties.getClientName()) ? null : jedisProperties.getClientName()
        );
        return sentinelPool.getResource();
    }

}
