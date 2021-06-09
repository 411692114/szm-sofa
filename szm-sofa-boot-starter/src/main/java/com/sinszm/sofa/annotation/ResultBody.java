package com.sinszm.sofa.annotation;

import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;


/**
 * 正确结果简化返回注解
 * @author sinszm
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ResponseBody
public @interface ResultBody {
}
