package com.sinszm.sofa;

import com.sinszm.sofa.enums.JedisModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.Set;

import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_EVICTION_POLICY_CLASS_NAME;
import static org.apache.commons.pool2.impl.BaseObjectPoolConfig.DEFAULT_JMX_NAME_BASE;

/**
 * Jedis配置参数
 * @author fh411
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "jedis")
public class JedisProperties {

    /**
     * 模式
     */
    private JedisModel model = JedisModel.STANDALONE;

    /**
     * 节点，格式：ip:port，多个节点参照springboot集合配置要求
     */
    private Set<String> address = Collections.emptySet();

    /**
     * 连接与响应超时时间
     */
    private int timeout = 2000;

    /**
     * 访问用户
     */
    private String user;

    /**
     * 访问密码
     */
    private String password;

    /**
     * 客户端名称
     */
    private String clientName = "szmJedis";

    /**
     * 数据点，范围：0~15
     */
    private int database = 0;

    /**
     * 哨兵模式的master名称
     */
    private String masterName;

    private int maxTotal = 8;

    private int maxIdle = 8;

    private int minIdle = 0;

    private boolean lifo = true;

    private boolean fairness = false;

    private long maxWaitMillis = -1L;

    private long minEvictableIdleTimeMillis = 1800000L;

    private long evictorShutdownTimeoutMillis = 10000L;

    private long softMinEvictableIdleTimeMillis = -1L;

    private int numTestsPerEvictionRun = 3;

    private String evictionPolicyClassName = DEFAULT_EVICTION_POLICY_CLASS_NAME;

    private boolean testOnCreate = false;

    private boolean testOnBorrow = false;

    private boolean testOnReturn = false;

    private boolean testWhileIdle = false;

    private long timeBetweenEvictionRunsMillis = -1L;

    private boolean blockWhenExhausted = true;

    private boolean jmxEnabled = true;

    private String jmxNamePrefix = "pool";

    private String jmxNameBase = DEFAULT_JMX_NAME_BASE;

}
