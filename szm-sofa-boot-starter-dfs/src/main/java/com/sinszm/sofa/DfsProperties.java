package com.sinszm.sofa;

import com.sinszm.sofa.enums.DfsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 分布式文件服务配置
 * @author fh411
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "dfs")
public class DfsProperties {

    /**
     * 分布式文件服务福类型
     */
    private DfsType type = DfsType.MINIO;

}
