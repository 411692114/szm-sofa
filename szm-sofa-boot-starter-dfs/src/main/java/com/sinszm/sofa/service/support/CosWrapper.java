package com.sinszm.sofa.service.support;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import com.qcloud.cos.transfer.Transfer;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferProgress;
import com.qcloud.cos.transfer.Upload;
import com.sinszm.sofa.CosProperties;
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
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * 文件服务器操作
 *
 * @author fh411
 */
@Slf4j
@EnableDFS(DfsType.COS)
@Component
public class CosWrapper {

    @Resource
    private CosProperties cosProperties;

    private COSClient client() {
        return SpringHelper.instance().getBean(COSClient.class);
    }

    /**
     * 传输管理器
     *
     * @return 管理器实例
     */
    private TransferManager transferManager() {
        ExecutorService threadPool = newFixedThreadPool(5);
        return new TransferManager(client(), threadPool);
    }

    /**
     * 上传进度
     *
     * @param transfer 进度数据
     */
    private void showTransferProgress(Transfer transfer) {
        log.info(transfer.getDescription());
        do {
            ThreadUtil.sleep(1000);
            TransferProgress progress = transfer.getProgress();
            long soFar = progress.getBytesTransferred();
            long total = progress.getTotalBytesToTransfer();
            log.info("[{} / {}]\n", soFar, total);
        } while (!transfer.isDone());
        log.info(transfer.getState().name());
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
        //传输管理器
        TransferManager transferManager = transferManager();
        //设置对象属性
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileSize);
        objectMetadata.setContentType(BaseUtil.trim(contentType));
        //设置上传参数
        PutObjectRequest putObjectRequest = new PutObjectRequest(
                BaseUtil.trim(cosProperties.bucketName()),
                StrUtil.join("/", filePath, fileName),
                new ByteArrayInputStream(bytes),
                objectMetadata
        );
        putObjectRequest.setStorageClass(StorageClass.Standard);
        //执行多线程上传并开启监听
        long startTime = System.currentTimeMillis();
        Upload upload = transferManager.upload(putObjectRequest);
        this.showTransferProgress(upload);
        UploadResult result = upload.waitForUploadResult();
        //关闭传输器
        transferManager.shutdownNow(false);
        log.info("分组：{}, 存储路径：{}, 耗时：{}", result.getBucketName(), result.getKey(), (System.currentTimeMillis() - startTime) / 1000 + "s");
        return UploadInfo.builder()
                .group(result.getBucketName())
                .path(result.getKey())
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
        GetObjectRequest getObjectRequest = new GetObjectRequest(
                BaseUtil.trim(bucket),
                BaseUtil.trim(path)
        );
        COSObject cosObject = client().getObject(getObjectRequest);
        return cosObject.getObjectContent();
    }

}
