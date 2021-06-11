package com.sinszm.sofa.service.support;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.sinszm.sofa.OssProperties;
import com.sinszm.sofa.annotation.EnableDFS;
import com.sinszm.sofa.enums.DfsType;
import com.sinszm.sofa.util.BaseUtil;
import com.sinszm.sofa.util.SpringHelper;
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
@EnableDFS(DfsType.OSS)
@Component
public class OSSWrapper {

    @Resource
    private OssProperties ossProperties;

    private OSS client() {
        return SpringHelper.instance().getBean(OSS.class);
    }

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
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(BaseUtil.trim(contentType));
        metadata.setContentLength(fileSize);
        client().putObject(
                BaseUtil.trim(ossProperties.getBucket()),
                StrUtil.join("/", filePath, fileName),
                new ByteArrayInputStream(bytes),
                metadata
        );
        log.info("分组：{}, 存储路径：{}",
                BaseUtil.trim(ossProperties.getBucket()),
                StrUtil.join("/", filePath, fileName)
        );
        return UploadInfo.builder()
                .group(BaseUtil.trim(ossProperties.getBucket()))
                .bucket(BaseUtil.trim(ossProperties.getBucket()))
                .path(StrUtil.join("/", filePath, fileName))
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
        OSSObject ossObject = client().getObject(
                BaseUtil.trim(bucket),
                BaseUtil.trim(path)
        );
        return ossObject.getObjectContent();
    }

}
