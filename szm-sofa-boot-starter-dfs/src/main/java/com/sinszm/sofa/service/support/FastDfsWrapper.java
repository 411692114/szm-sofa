package com.sinszm.sofa.service.support;

import cn.hutool.core.io.IoUtil;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.sinszm.sofa.annotation.EnableDFS;
import com.sinszm.sofa.enums.DfsType;
import com.sinszm.sofa.util.SpringHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 文件服务器操作
 *
 * @author fh411
 */
@Slf4j
@EnableDFS(DfsType.FAST_DFS)
@Component
public class FastDfsWrapper {

    private FastFileStorageClient client() {
        return SpringHelper.instance().getBean(FastFileStorageClient.class);
    }

    /**
     * 文件上传
     *
     * @param bytes     文件字节
     * @param fileSize  文件大小
     * @param extension 文件扩展名
     * @return fastDfs上传的文件信息
     */
    public UploadInfo upload(byte[] bytes, long fileSize, String extension) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        StorePath storePath = client().uploadFile(byteArrayInputStream, fileSize, extension, null);
        log.info("分组：{}, 存储路径：{}", storePath.getGroup(), storePath.getPath());
        return UploadInfo.builder()
                .group(storePath.getGroup())
                .path(storePath.getPath())
                .build();
    }

    /**
     * 下载文件
     * @param group         文件组或bucket
     * @param path          文件存储路径
     * @return              文件流
     */
    @SneakyThrows
    public InputStream download(String group, String path) {
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        return IoUtil.toStream(
                client().downloadFile(group, path, downloadByteArray)
        );
    }

}
