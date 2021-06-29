package com.sinszm.sofa.lock;

/**
 * 分布式任务锁
 *
 * @author fh411
 */
public interface RedisLock {

    /**
     * 加锁
     *
     * @param key         关键
     * @param requestId   请求id
     * @param expiresTime 到期时间,精度秒
     * @return boolean
     */
    boolean lock(String key, String requestId, int expiresTime);

    /**
     * 释放锁
     *
     * @param key       关键
     * @param requestId 请求id
     * @return boolean
     */
    boolean releaseLock(String key, String requestId);

}
