package com.sinszm.sofa.annotation;

import com.sinszm.sofa.condition.ConditionalOnOrderDataSource;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;


/**
 * 是否启用SQLite3数据源
 *
 * @author admin
 */
@Target({ ElementType.TYPE , ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ConditionalOnOrderDataSource.class)
public @interface EnableOrderDataSource {

}
