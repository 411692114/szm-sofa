package com.sinszm.sofa.condition;

import cn.hutool.core.lang.Assert;
import com.sinszm.sofa.annotation.EnableDFS;
import com.sinszm.sofa.enums.DfsType;
import com.sinszm.sofa.exception.ApiException;
import com.sinszm.sofa.util.BaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

/**
 * DFS条件判断器
 * @author fh411
 */
@Slf4j
public class ConditionalOnDfs extends SpringBootCondition {

    private static final String DFS_TYPE = "dfs.type";

    private static final String MIN_IO_ENDPOINT = "dfs.minio.endpoint";

    private static final String MIN_IO_ACCESS = "dfs.minio.access-key";

    private static final String MIN_IO_SECRET = "dfs.minio.secret-key";

    private static final String MIN_IO_BUCKET = "dfs.minio.bucket";

    private static final String COS_SECRET_ID = "dfs.cos.secret-id";

    private static final String COS_SECRET_KEY = "dfs.cos.secret-key";

    private static final String COS_REGION = "dfs.cos.region";

    private static final String COS_BUCKET = "dfs.cos.bucket";

    private static final String COS_APPID = "dfs.cos.appid";

    private static final String OSS_ACCESS_ID = "dfs.oss.access-key-id";

    private static final String OSS_ACCESS_SECRET = "dfs.oss.access-key-secret";

    private static final String OSS_ACCESS_ENDPOINT = "dfs.oss.endpoint";

    private static final String OSS_ACCESS_BUCKET = "dfs.oss.bucket";

    private <T> T propValue(ConditionContext context, String key, Class<T> cls) {
        return context.getEnvironment().getProperty(key, cls);
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        DfsType dfsType = propValue(context,DFS_TYPE, DfsType.class);
        Assert.notNull(dfsType, () -> new ApiException("-1", DFS_TYPE + "不能为空"));
        DfsType expression = (DfsType) Objects.requireNonNull(metadata.getAnnotationAttributes(EnableDFS.class.getName())).get("value");
        if (expression == dfsType) {
            switch (dfsType) {
                case MINIO:
                    Assert.notEmpty(BaseUtil.trim(propValue(context, MIN_IO_ENDPOINT, String.class)), () -> new ApiException("-1", MIN_IO_ENDPOINT + "不能为空"));
                    Assert.notEmpty(BaseUtil.trim(propValue(context, MIN_IO_ACCESS, String.class)), () -> new ApiException("-1", MIN_IO_ACCESS + "不能为空"));
                    Assert.notEmpty(BaseUtil.trim(propValue(context, MIN_IO_SECRET, String.class)), () -> new ApiException("-1", MIN_IO_SECRET + "不能为空"));
                    Assert.notEmpty(BaseUtil.trim(propValue(context, MIN_IO_BUCKET, String.class)), () -> new ApiException("-1", MIN_IO_BUCKET + "不能为空"));
                    break;
                case COS:
                    Assert.notEmpty(BaseUtil.trim(propValue(context, COS_SECRET_ID, String.class)), () -> new ApiException("-1", COS_SECRET_ID + "不能为空"));
                    Assert.notEmpty(BaseUtil.trim(propValue(context, COS_SECRET_KEY, String.class)), () -> new ApiException("-1", COS_SECRET_KEY + "不能为空"));
                    Assert.notEmpty(BaseUtil.trim(propValue(context, COS_REGION, String.class)), () -> new ApiException("-1", COS_REGION + "不能为空"));
                    Assert.notEmpty(BaseUtil.trim(propValue(context, COS_BUCKET, String.class)), () -> new ApiException("-1", COS_BUCKET + "不能为空"));
                    Assert.notEmpty(BaseUtil.trim(propValue(context, COS_APPID, String.class)), () -> new ApiException("-1", COS_APPID + "不能为空"));
                    break;
                case OSS:
                    Assert.notEmpty(BaseUtil.trim(propValue(context, OSS_ACCESS_ID, String.class)), () -> new ApiException("-1", OSS_ACCESS_ID + "不能为空"));
                    Assert.notEmpty(BaseUtil.trim(propValue(context, OSS_ACCESS_SECRET, String.class)), () -> new ApiException("-1", OSS_ACCESS_SECRET + "不能为空"));
                    Assert.notEmpty(BaseUtil.trim(propValue(context, OSS_ACCESS_ENDPOINT, String.class)), () -> new ApiException("-1", OSS_ACCESS_ENDPOINT + "不能为空"));
                    Assert.notEmpty(BaseUtil.trim(propValue(context, OSS_ACCESS_BUCKET, String.class)), () -> new ApiException("-1", OSS_ACCESS_BUCKET + "不能为空"));
                    break;
                default:
                    throw new ApiException("-1", "未实现的分布式文件服务类型");
            }
            return new ConditionOutcome(true, "@EnableDFS");
        } else {
            return new ConditionOutcome(false, "@EnableDFS");
        }
    }

}
