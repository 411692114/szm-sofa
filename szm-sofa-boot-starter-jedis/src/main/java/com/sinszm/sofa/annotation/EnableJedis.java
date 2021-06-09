package com.sinszm.sofa.annotation;

import com.sinszm.sofa.condition.ConditionalOnJedis;
import com.sinszm.sofa.enums.JedisModel;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * Jedis启用开关
 *
 * @author fh411
 */
@Target({ ElementType.TYPE , ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ConditionalOnJedis.class)
public @interface EnableJedis {

    /**
     * Jedis模式
     * @return  模式
     */
    JedisModel[] value() default {JedisModel.STANDALONE};

}
