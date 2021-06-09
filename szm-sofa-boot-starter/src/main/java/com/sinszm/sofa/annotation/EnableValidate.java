package com.sinszm.sofa.annotation;


import com.sinszm.sofa.condition.ConditionalOnStarter;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * 验证必要参数
 *
 * @author fh411
 */
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ConditionalOnStarter.class)
public @interface EnableValidate {
}
