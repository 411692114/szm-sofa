package com.sinszm.sofa.service.support;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.sinszm.sofa.MinIoProperties;
import com.sinszm.sofa.annotation.EnableDFS;
import com.sinszm.sofa.enums.DfsType;
import com.sinszm.sofa.util.BaseUtil;
import com.sinszm.sofa.util.SpringHelper;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

/**
 * 文件服务器操作
 *
 * @author fh411
 */
@Slf4j
@EnableDFS(DfsType.MINIO)
@Component
public class MinIoWrapper {

    private MinioClient client() {
        return SpringHelper.instance().getBean(MinioClient.class);
    }

    @Resource
    private MinIoProperties minIoProperties;

    /**
     * 文件上传
     *
     * @param bytes         文件字节
     * @param fileSize      文件大小
     * @param contentType   文件MIME类型
     * @return              文件信息
     */
    @SneakyThrows
    public UploadInfo upload(byte[] bytes, long fileSize, String contentType, String extension) {
        String fileName = StrUtil.join(".", BaseUtil.uuid(), BaseUtil.trim(extension).replace(".", ""));
        String filePath = DateUtil.formatDate(new Date()).replace("-", "/");
        ObjectWriteResponse response = client().putObject(
                PutObjectArgs.builder()
                        .contentType(BaseUtil.trim(contentType))
                        .bucket(BaseUtil.trim(minIoProperties.getBucket()))
                        .object(StrUtil.join("/", filePath, fileName))
                        .stream(new ByteArrayInputStream(bytes),fileSize, 5L * 1024 * 1024 * 1024).build()
        );
        log.info("分组：{}, 存储路径：{}", response.bucket(), response.object());
        return UploadInfo.builder()
                .group(response.bucket())
                .path(response.object())
                .build();
    }

    /**
     * 下载文件
     * @param bucket        文件组或bucket
     * @param path          文件存储路径
     * @return              文件流
     */
    @SneakyThrows
    public InputStream download(String bucket, String path) {
        return client().getObject(
                GetObjectArgs.builder()
                        .bucket(BaseUtil.trim(bucket))
                        .object(BaseUtil.trim(path))
                        .build()
        );
    }

}
