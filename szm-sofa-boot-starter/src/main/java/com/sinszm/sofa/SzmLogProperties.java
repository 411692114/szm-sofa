package com.sinszm.sofa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 补充日志配置
 * @author sinszm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "logging")
public class SzmLogProperties {
    /**
     * 默认的日志目录，主要是sofaBoot的日志记录
     */
    private String path = "./logs";

}
