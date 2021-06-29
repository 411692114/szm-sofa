package com.sinszm.sofa;

import cn.hutool.core.util.SerializeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 对象序列化器
 *
 * @author chenjianbo
 */
@Slf4j
public class ObjectSerializer<V> implements RedisSerializer<V> {

    private static final byte[] EMPTY_ARRAY = new byte[0];

    @Override
    public V deserialize(byte[] bytes) {
        if (isEmpty(bytes)) {
            return null;
        }
        try {
            return SerializeUtil.deserialize(bytes);
        } catch (Exception ex) {
            log.error("反序列化异常",ex);
            return null;
        }
    }

    @Override
    public byte[] serialize(V object) {
        try {
            return SerializeUtil.serialize(object);
        } catch (Exception ex) {
            log.error("序列化异常",ex);
            return EMPTY_ARRAY;
        }
    }

    private boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }
}