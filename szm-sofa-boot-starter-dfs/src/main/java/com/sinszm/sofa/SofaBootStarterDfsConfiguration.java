package com.sinszm.sofa;


import cn.hutool.core.util.StrUtil;
import com.github.tobato.fastdfs.FdfsClientConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.region.Region;
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
@EnableConfigurationProperties({DfsProperties.class,MinIoProperties.class,CosProperties.class})
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

    @EnableDFS(DfsType.COS)
    @Configuration
    public static class CosConfiguration implements CommandLineRunner {

        @Resource
        private CosProperties cosProperties;

        /**
         * COS客户端
         * <p>
         *     1.需要使用为私有访问；
         *     2.需要设置为标准存储模式；
         *     3.开启SSE-COS加密；
         * </p>
         * @return  客户端对象
         */
        @Bean
        public COSClient cosClient() {
            COSCredentials cred = new BasicCOSCredentials(
                    StrUtil.trimToEmpty(cosProperties.getSecretId()),
                    StrUtil.trimToEmpty(cosProperties.getSecretKey())
            );
            ClientConfig clientConfig = new ClientConfig(new Region(
                    StrUtil.trimToEmpty(cosProperties.getRegion())
            ));
            clientConfig.setHttpProtocol(HttpProtocol.https);
            return new COSClient(cred, clientConfig);
        }

        @Resource
        private COSClient cosClient;

        @Override
        public void run(String... args) {
            boolean state = cosClient.doesBucketExist(BaseUtil.trim(cosProperties.bucketName()));
            if (!state) {
                CreateBucketRequest request = new CreateBucketRequest(BaseUtil.trim(cosProperties.bucketName()));
                cosClient.createBucket(request);
            }
        }

    }

}
