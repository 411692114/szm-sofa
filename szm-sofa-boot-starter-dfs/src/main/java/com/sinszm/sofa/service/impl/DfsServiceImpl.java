package com.sinszm.sofa.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.URLUtil;
import com.sinszm.sofa.DfsProperties;
import com.sinszm.sofa.exception.ApiException;
import com.sinszm.sofa.service.DfsService;
import com.sinszm.sofa.service.support.*;
import com.sinszm.sofa.util.SpringHelper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * 文件服务实现
 * @author fh411
 */
@Service
public class DfsServiceImpl implements DfsService {

    private FastDfsWrapper fastDfs() {
        return SpringHelper.instance().getBean(FastDfsWrapper.class);
    }

    private MinIoWrapper mimIo() {
        return SpringHelper.instance().getBean(MinIoWrapper.class);
    }

    private CosWrapper cos() {
        return SpringHelper.instance().getBean(CosWrapper.class);
    }

    private OSSWrapper oss() {
        return SpringHelper.instance().getBean(OSSWrapper.class);
    }

    @Resource
    private DfsProperties dfsProperties;

    @Override
    public UploadInfo upload(byte[] bytes, long fileSize, String contentType, String extension) {
        Assert.notNull(bytes, () -> new ApiException("-1", "文件字节不能为空"));
        Assert.isFalse(bytes.length == 0, () -> new ApiException("-1", "文件字节不能为空"));
        Assert.notEmpty(contentType, () -> new ApiException("-1", "文件类型不能为空"));
        Assert.notEmpty(extension, () -> new ApiException("-1", "文件扩展名不能为空"));
        switch (dfsProperties.getType()) {
            case MINIO:
                return mimIo().upload(bytes, fileSize, contentType, extension);
            case FAST_DFS:
                return fastDfs().upload(bytes, fileSize, extension);
            case COS:
                return cos().upload(bytes, fileSize, contentType, extension);
            case OSS:
                return oss().upload(bytes, fileSize, contentType, extension);
            default:
                throw new ApiException("-1", "暂不支持的上传类型");
        }
    }

    @Override
    public InputStream download(String group, String path) {
        Assert.notEmpty(group, () -> new ApiException("-1", "文件组或bucket不能为空"));
        Assert.notEmpty(path, () -> new ApiException("-1", "文件存储路径不能为空"));
        switch (dfsProperties.getType()) {
            case MINIO:
                return mimIo().download(group, path);
            case FAST_DFS:
                return fastDfs().download(group, path);
            case COS:
                return cos().download(group, path);
            case OSS:
                return oss().download(group, path);
            default:
                throw new ApiException("-1", "暂不支持的文件下载类型");
        }
    }

    @Override
    public ResponseEntity<InputStreamResource> download(String fileName, String group, String path) {
        InputStream in = download(group, path);
        byte[] bytes = IoUtil.readBytes(in);
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("Cache-Control", "no-cache, no-store, must-revalidate");
        multiValueMap.add("Pragma", "no-cache");
        multiValueMap.add("Expires", "0L");
        multiValueMap.add("Content-disposition", "attachment; filename=" + URLUtil.encode(fileName, "UTF-8"));
        multiValueMap.add("access-control-allow-origin", "*");
        HttpHeaders headers = new HttpHeaders(multiValueMap);
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.status(200);
        responseEntity.contentType(MediaType.APPLICATION_OCTET_STREAM);
        responseEntity.headers(headers);
        responseEntity.contentLength(bytes.length);
        return responseEntity.body(new InputStreamResource(IoUtil.toStream(bytes)));
    }

}
