package com.sinszm.sofa;


import com.github.tobato.fastdfs.FdfsClientConfig;
import com.sinszm.sofa.annotation.EnableDFS;
import com.sinszm.sofa.enums.DfsType;
import com.sinszm.sofa.util.BaseUtil;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
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
    public static class MinIoConfiguration implements CommandLineRunner {

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

        @Resource
        private MinioClient minioClient;

        /**
         * 判断及初始化存储桶
         * @param args  参数
         */
        @SneakyThrows
        @Override
        public void run(String... args) {
            boolean isExist = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(BaseUtil.trim(minIoProperties.getBucket()))
                            .build()
            );
            if(!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(BaseUtil.trim(minIoProperties.getBucket()))
                        .build());
            }
        }

    }

}
