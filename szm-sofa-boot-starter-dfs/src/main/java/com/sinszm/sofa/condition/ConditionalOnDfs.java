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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * DFS条件判断器
 * @author fh411
 */
@Slf4j
public class ConditionalOnDfs extends SpringBootCondition {

    private static final String DFS_TYPE = "dfs.type";

    private static final String FAST_DFS_URL = "fdfs.tracker-list";

    private static final String MIN_IO_ENDPOINT = "dfs.minio.endpoint";

    private static final String MIN_IO_ACCESS = "dfs.minio.access-key";

    private static final String MIN_IO_SECRET = "dfs.minio.secret-key";

    private static final String MIN_IO_BUCKET = "dfs.minio.bucket";

    private static final AtomicInteger INIT = new AtomicInteger(0);

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
                case FAST_DFS:
                    if (log.isDebugEnabled() && INIT.getAndIncrement() == 0) {
                        log.warn("\n~~~~~~~~~请检查分布式文件服务存储地址确保必须配置：【 {} 】~~~~~~~~~", FAST_DFS_URL);
                        log.warn("Check the distributed file service storage address to ensure that it must be configured: [ {} ]", FAST_DFS_URL);
                    }
                    break;
                case MINIO:
                    Assert.notEmpty(BaseUtil.trim(propValue(context, MIN_IO_ENDPOINT, String.class)), () -> new ApiException("-1", MIN_IO_ENDPOINT + "不能为空"));
                    Assert.notEmpty(BaseUtil.trim(propValue(context, MIN_IO_ACCESS, String.class)), () -> new ApiException("-1", MIN_IO_ACCESS + "不能为空"));
                    Assert.notEmpty(BaseUtil.trim(propValue(context, MIN_IO_SECRET, String.class)), () -> new ApiException("-1", MIN_IO_SECRET + "不能为空"));
                    Assert.notEmpty(BaseUtil.trim(propValue(context, MIN_IO_BUCKET, String.class)), () -> new ApiException("-1", MIN_IO_BUCKET + "不能为空"));
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
