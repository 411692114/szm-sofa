package com.sinszm.sofa.annotation;

import com.sinszm.sofa.condition.ConditionalOnDfs;
import com.sinszm.sofa.enums.DfsType;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * DFS启用开关
 *
 * @author fh411
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ConditionalOnDfs.class)
public @interface EnableDFS {

    /**
     * 分布式文件服务类型指定
     * @return  支持需要
     */
    DfsType value() default DfsType.MINIO;

}
