package com.sinszm.sofa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ORM配置参数
 * @author fh411
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "orm")
public class OrmProperties {

    /**
     * 扫描的dao层包
     */
    private String basePackage;

}
