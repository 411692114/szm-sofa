package com.sinszm.sofa.condition;

import com.sinszm.sofa.util.BaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 数据源条件判断器
 * @author fh411
 */
@Slf4j
public class ConditionalOnOrderDataSource extends SpringBootCondition {

    private static final String DEFAULT_DATASOURCE = "order.datasource";
    private static final String DEFAULT_DIALECT = "order.hibernate-dialect";

    /**
     * 工具
     *
     * @param context 上下文
     * @param key     关键
     * @param cls     cls
     * @return {@link T}
     */
    private <T> T propValue(ConditionContext context, String key, Class<T> cls) {
        return context.getEnvironment().getProperty(key, cls);
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String dataSource = propValue(context, DEFAULT_DATASOURCE, String.class);
        String dialect = propValue(context, DEFAULT_DIALECT, String.class);
        if (BaseUtil.isEmpty(dataSource) || BaseUtil.isEmpty(dialect)) {
            return new ConditionOutcome(true, "@EnableOrderDataSource");
        }
        return new ConditionOutcome(false, "@EnableOrderDataSource");
    }

}
