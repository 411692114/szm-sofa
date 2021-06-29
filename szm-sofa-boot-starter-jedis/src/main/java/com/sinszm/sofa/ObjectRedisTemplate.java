package com.sinszm.sofa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * Redis操作模板
 *
 * @author chenjianbo
 */
@Primary
@Component
@ConditionalOnClass({RedisAutoConfiguration.class, RedisTemplate.class})
public class ObjectRedisTemplate<V> extends RedisTemplate<String,V> {

    @Autowired
    public ObjectRedisTemplate(final RedisConnectionFactory redisConnectionFactory) {
        RedisSerializer<String> string = new StringRedisSerializer();
        RedisSerializer<V> object = new ObjectSerializer<>();
        setEnableDefaultSerializer(true);
        setDefaultSerializer(string);
        setKeySerializer(string);
        setValueSerializer(object);
        setHashKeySerializer(string);
        setHashValueSerializer(object);
        setConnectionFactory(redisConnectionFactory);
        afterPropertiesSet();
    }

}