package com.sinszm.sofa.lock;

import cn.hutool.extra.spring.SpringUtil;
import com.sinszm.sofa.exception.ApiException;
import com.sinszm.sofa.response.StatusCode;
import com.sinszm.sofa.util.BaseUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 分布式锁调用类
 *
 * @author admin
 */
@Slf4j
public class Lock implements AutoCloseable {

    private final String key;
    private final String value;
    private final int expiresTime;

    public Lock(String key) {
        this(key, BaseUtil.uuid());
    }

    public Lock(String key, String value) {
        this(key, value, 120);
    }

    public Lock(String key, String value, int expiresTime) {
        this.key = key;
        this.value = value;
        this.expiresTime = expiresTime;
    }

    /**
     * 锁实例
     *
     * @return 调用实例
     */
    private RedisLock redisLock() {
        try {
            return SpringUtil.getBean(RedisLock.class);
        }catch (Exception e) {
            throw new ApiException(StatusCode.SYSTEM_ERROR);
        }
    }

    /**
     * 加锁
     *
     * @return boolean
     */
    public boolean lock() {
        return this.redisLock().lock(this.key, this.value, this.expiresTime);
    }

    /**
     * 自动释放锁
     */
    @SneakyThrows
    @Override
    public void close() {
        boolean result = this.redisLock().releaseLock(this.key, this.value);
        log.info("释放锁：" + (result ? "success" : "fail"));
    }
}
