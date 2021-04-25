package com.sinszm.sofa.service;

import com.sinszm.sofa.service.support.UploadInfo;

import java.io.InputStream;

/**
 * 文件服务
 * @author fh411
 */
public interface DfsService {

    /**
     * 上传文件
     * @param bytes             文件字节
     * @param fileSize          文件大小
     * @param contentType       文件类型MIME
     * @param extension         扩展名
     * @return                  文件信息
     */
    UploadInfo upload(byte[] bytes, long fileSize, String contentType, String extension);

    /**
     * 下载文件
     * @param group         文件组或bucket
     * @param path          存储路径
     * @return              文件下载
     */
    InputStream download(String group, String path);
}
