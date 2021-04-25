package com.sinszm.sofa;


import com.github.tobato.fastdfs.FdfsClientConfig;
import com.sinszm.sofa.annotation.EnableDFS;
import com.sinszm.sofa.enums.DfsType;
import com.sinszm.sofa.util.BaseUtil;
import io.minio.MinioClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.jmx.support.RegistrationPolicy;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 配置加载中心
 * @author sinszm
 */
@PropertySource("classpath:settings.properties")
@EnableConfigurationProperties({DfsProperties.class,MinIoProperties.class})
public class SofaBootStarterDfsConfiguration {

    @EnableDFS(DfsType.FAST_DFS)
    @Configuration
    @Import(FdfsClientConfig.class)
    @EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
    public static class FastDfsConfiguration {
    }

    @EnableDFS(DfsType.MINIO)
    @Configuration
    public static class MinIoConfiguration {

        @Resource
        private MinIoProperties minIoProperties;

        @Bean
        public MinioClient minioClient() {
            return MinioClient.builder()
                    .endpoint(
                            BaseUtil.trim(minIoProperties.getEndpoint()),
                            Optional.ofNullable(minIoProperties.getPort()).orElse(80),
                            minIoProperties.isSecure()
                    )
                    .credentials(
                            BaseUtil.trim(minIoProperties.getAccessKey()),
                            BaseUtil.trim(minIoProperties.getSecretKey())
                    )
                    .build();
        }

    }

}
