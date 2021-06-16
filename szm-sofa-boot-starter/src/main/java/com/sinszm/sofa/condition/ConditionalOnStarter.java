package com.sinszm.sofa.condition;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.sinszm.sofa.exception.ApiException;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 验证必要参数
 * @author fh411
 */
public class ConditionalOnStarter extends SpringBootCondition  {

    private static final String APPLICATION_NAME = "spring.application.name";

    private static final String LOG_PATH = "logging.file.path";

    private String propValue(ConditionContext context, String key) {
        return StrUtil.trimToEmpty(
                context.getEnvironment().getProperty(key, String.class)
        );
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String applicationName = propValue(context, APPLICATION_NAME);
        Assert.notEmpty(applicationName, () -> new ApiException("-1", APPLICATION_NAME + "不能为空"));
        String logPath = propValue(context, LOG_PATH);
        Assert.notEmpty(logPath, () -> new ApiException("-1", LOG_PATH + "不能为空"));
        return new ConditionOutcome(true, "@EnableValidate");
    }

}
