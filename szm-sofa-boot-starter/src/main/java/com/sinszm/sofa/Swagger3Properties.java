package com.sinszm.sofa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 默认配置
 * @author sinszm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "swagger")
public class Swagger3Properties {

    /**
     * 是否启用文档
     */
    private boolean enable = false;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档描述
     */
    private String description;

    /**
     * 作者
     */
    private String author;

    /**
     * 网站
     */
    private String site;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 版本
     */
    private String version;

}
