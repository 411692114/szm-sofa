package com.sinszm.sofa.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 分布式任务锁
 *
 * @author fh411
 */
@Slf4j
@Service
@ConditionalOnClass({RedisAutoConfiguration.class, RedisTemplate.class})
public class RedisLockImpl implements RedisLock {

    private static final String LOCK_LUA = "if redis.call('setnx',KEYS[1],ARGV[1]) == 1 then  return redis.call('expire',KEYS[1],ARGV[2])  else return 0 end";

    private static final String RELEASE_LOCK_LUA = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";

    private final RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    public RedisLockImpl(RedisTemplate<String, String> stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean lock(String key, String requestId, int expiresTime) {
        DefaultRedisScript<Long> longDefaultRedisScript = new DefaultRedisScript<>(LOCK_LUA, Long.class);
        Long result = stringRedisTemplate.execute(longDefaultRedisScript, Collections.singletonList(key), requestId,String.valueOf(expiresTime));
        return result != null && result == 1;
    }

    @Override
    public boolean releaseLock(String key, String requestId) {
        DefaultRedisScript<Long> longDefaultRedisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA, Long.class);
        Long result = stringRedisTemplate.execute(longDefaultRedisScript, Collections.singletonList(key), requestId);
        return result != null && result == 1;
    }

}
