package com.sinszm.sofa.condition;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.sinszm.sofa.exception.ApiException;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;
import java.util.List;

/**
 * ORM条件判断器
 * @author fh411
 */
public class ConditionalOnOrm extends SpringBootCondition {

    private static final String ORM_PACKAGE = "orm.base-package";
    private static final String ORM_PLATFORM = "spring.datasource.platform";
    private static final String ORM_DRIVER_CLASS = "spring.datasource.driver-class-name";
    private static final String ORM_URL = "spring.datasource.url";
    private static final String ORM_USERNAME = "spring.datasource.username";
    private static final String ORM_PASSWORD = "spring.datasource.password";
    private static final List<String> PLATFORMS = Arrays.asList(
            "oracle",
            "mysql",
            "mariadb",
            "sqlserver"
    );

    private String propValue(ConditionContext context, String key) {
        return StrUtil.trimToEmpty(
                context.getEnvironment().getProperty(key, String.class)
        );
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String basePackage = propValue(context,ORM_PACKAGE);
        Assert.notEmpty(basePackage, () -> new ApiException("-1", ORM_PACKAGE + "不能为空"));
        String platform = propValue(context,ORM_PLATFORM);
        Assert.notEmpty(platform, () -> new ApiException("-1", ORM_PLATFORM + "不能为空"));
        String driverClass = propValue(context,ORM_DRIVER_CLASS);
        Assert.notEmpty(driverClass, () -> new ApiException("-1", ORM_DRIVER_CLASS + "不能为空"));
        String url = propValue(context,ORM_URL);
        Assert.notEmpty(url, () -> new ApiException("-1", ORM_URL + "不能为空"));
        if (PLATFORMS.contains(platform.toLowerCase())) {
            String username = propValue(context,ORM_USERNAME);
            Assert.notEmpty(username, () -> new ApiException("-1", ORM_USERNAME + "不能为空"));
            String password = propValue(context,ORM_PASSWORD);
            Assert.notEmpty(password, () -> new ApiException("-1", ORM_PASSWORD + "不能为空"));
        }
        return new ConditionOutcome(true, "@EnableORM");
    }

}
