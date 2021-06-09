package com.sinszm.sofa;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.SerializeUtil;
import com.sinszm.sofa.enums.JedisModel;
import com.sinszm.sofa.exception.ApiException;
import com.sinszm.sofa.util.SpringHelper;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * Jedis缓存操作
 * <p>
 *     常用操作
 * </p>
 * @author fh411
 */
@Component
public class JedisUtil<T extends Serializable> {

    @Resource
    private JedisProperties jedisProperties;

    /**
     * 集群实例
     * @return  实例
     */
    public JedisCluster cluster() {
        if (isCluster()) {
            return SpringHelper.instance().getBean(JedisCluster.class);
        }
        throw new ApiException("-1", "Jedis不是集群模式");
    }

    /**
     * 单节点或哨兵实例
     * @return  实例
     */
    public Jedis jedis() {
        if (!isCluster()) {
            return SpringHelper.instance().getBean(Jedis.class);
        }
        throw new ApiException("-1", "Jedis不是单节点模式或哨兵模式");
    }

    private boolean isCluster() {
        return jedisProperties.getModel() == JedisModel.CLUSTER;
    }

    /**
     * 基本
     * @param key   键
     * @param data  数据
     */
    public void set(String key, T data) {
        Assert.notEmpty(key, () -> new ApiException("-1", "键不能为空"));
        Assert.notNull(data, () -> new ApiException("-1", "数据不能为空"));
        if (isCluster()) {
            cluster().set(key.getBytes(StandardCharsets.UTF_8), SerializeUtil.serialize(data));
        } else {
            jedis().set(key.getBytes(StandardCharsets.UTF_8), SerializeUtil.serialize(data));
        }
    }

    /**
     * 基本
     * @param key       键
     * @param data      数据
     * @param expire    过期时间，精度：秒
     */
    public void set(String key, T data, long expire) {
        set(key, data);
        expire(key, expire < 0 ? 0 : expire);
    }

    /**
     * 获取数据
     * @param key   键
     * @return      数据
     */
    public T get(String key) {
        Assert.notEmpty(key, () -> new ApiException("-1", "键不能为空"));
        byte[] bytes;
        if (isCluster()) {
            bytes = cluster().get(key.getBytes(StandardCharsets.UTF_8));
        } else {
            bytes = jedis().get(key.getBytes(StandardCharsets.UTF_8));
        }
        try {
            return SerializeUtil.deserialize(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 删除键
     * @param key   键
     * @return      删除成功的个数
     */
    public Long del(String... key) {
        if (isCluster()) {
            return cluster().del(key);
        } else {
            return jedis().del(key);
        }
    }

    /**
     * 判断键是否存在
     * @param key   键
     * @return      结果，true存在，false不存在
     */
    public Boolean exists(String key) {
        Assert.notEmpty(key, () -> new ApiException("-1", "键不能为空"));
        if (isCluster()) {
            return cluster().exists(key);
        } else {
            return jedis().exists(key);
        }
    }

    /**
     * 清空缓存
     * @return  总是返回OK
     */
    public String flushDb() {
        if (isCluster()) {
            throw new ApiException("-1", "此方法不支持");
        } else {
            return jedis().flushDB();
        }
    }

    /**
     * 设置过期
     * @param key       键
     * @param expire    过期时间，精度：秒
     * @return          结果
     */
    public Long expire(String key, long expire) {
        Assert.notEmpty(key, () -> new ApiException("-1", "键不能为空"));
        if (isCluster()) {
            return cluster().expire(key, expire < 0 ? 0 : expire);
        } else {
            return jedis().expire(key, expire < 0 ? 0 : expire);
        }
    }

    /**
     * 查询缓存剩余生成时间
     * @param key   键
     * @return      剩余过期时间，精度：秒
     */
    public Long ttl(String key) {
        Assert.notEmpty(key, () -> new ApiException("-1", "键不能为空"));
        if (isCluster()) {
            return cluster().ttl(key);
        } else {
            return jedis().ttl(key);
        }
    }

}
