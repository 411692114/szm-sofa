package com.sinszm.sofa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文件存储服务器配置
 * <p>
 *     腾讯云COS
 * </p>
 * @author sinszm
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("dfs.cos")
public class CosProperties {

    /**
     * 授权ID
     */
    private String secretId;

    /**
     * 授权密钥
     */
    private String secretKey;

    /**
     * 所属地区
     */
    private String region;

    /**
     * 存储桶
     */
    private String bucket;

    /**
     * 应用ID
     */
    private String appid;

    /**
     * 存储桶名称
     * @return  名称
     */
    public String bucketName() {
        return this.bucket + "-" + this.appid;
    }

}
