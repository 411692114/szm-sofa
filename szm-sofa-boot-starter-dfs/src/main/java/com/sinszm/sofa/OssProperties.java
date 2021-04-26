package com.sinszm.sofa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文件存储服务器配置
 * <p>
 *     阿里云OSS
 * </p>
 * @author sinszm
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("dfs.oss")
public class OssProperties {

    /**
     * 授权ID
     */
    private String accessKeyId;

    /**
     * 授权密钥
     */
    private String accessKeySecret;

    /**
     * 端点，域
     */
    private String endpoint;

    /**
     * 存储桶
     */
    private String bucket;

}
