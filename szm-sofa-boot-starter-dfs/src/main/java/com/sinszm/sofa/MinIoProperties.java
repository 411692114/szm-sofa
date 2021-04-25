package com.sinszm.sofa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MinIO配置
 * @author fh411
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "dfs.minio")
public class MinIoProperties {

    /**
     * 端点，endPoint是一个URL，域名，IPv4或者IPv6地址
     */
    private String endpoint;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 如果是true，则用的是https而不是http,默认值是true。
     */
    private boolean secure = false;

    /**
     * 授权账号
     */
    private String accessKey;

    /**
     * 授权密钥
     */
    private String secretKey;

    /**
     * 存储桶
     */
    private String bucket;

}
