package com.sinszm.sofa.service.impl;

import com.sinszm.sofa.DfsProperties;
import com.sinszm.sofa.exception.ApiException;
import com.sinszm.sofa.service.DfsService;
import com.sinszm.sofa.service.support.FastDfsWrapper;
import com.sinszm.sofa.service.support.MinIoWrapper;
import com.sinszm.sofa.service.support.UploadInfo;
import com.sinszm.sofa.util.SpringHelper;
import org.springframework.stereotype.Service;

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

    @Resource
    private DfsProperties dfsProperties;

    @Override
    public UploadInfo upload(byte[] bytes, long fileSize, String contentType, String extension) {
        switch (dfsProperties.getType()) {
            case MINIO:
                return mimIo().upload(bytes, fileSize, contentType, extension);
            case FAST_DFS:
                return fastDfs().upload(bytes, fileSize, extension);
            default:
                throw new ApiException("-1", "暂不支持的上传类型");
        }
    }

    @Override
    public InputStream download(String group, String path) {
        switch (dfsProperties.getType()) {
            case MINIO:
                return mimIo().download(group, path);
            case FAST_DFS:
                return fastDfs().download(group, path);
            default:
                throw new ApiException("-1", "暂不支持的文件下载类型");
        }
    }

}
