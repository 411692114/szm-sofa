package com.sinszm.sofa.annotation;

import com.sinszm.sofa.condition.ConditionalOnOrm;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * ORM启用开关
 *
 * @author fh411
 */
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ConditionalOnOrm.class)
public @interface EnableORM {
}
