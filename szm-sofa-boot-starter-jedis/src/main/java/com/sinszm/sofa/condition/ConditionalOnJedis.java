package com.sinszm.sofa.condition;

import cn.hutool.core.lang.Assert;
import com.sinszm.sofa.annotation.EnableJedis;
import com.sinszm.sofa.enums.JedisModel;
import com.sinszm.sofa.exception.ApiException;
import com.sinszm.sofa.response.StatusCode;
import com.sinszm.sofa.util.BaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Jedis模式条件判断器
 * @author fh411
 */
@Slf4j
public class ConditionalOnJedis extends SpringBootCondition {

    private static final String JEDIS_MODEL = "jedis.model";
    private static final String JEDIS_ADDRESS = "jedis.address";
    private static final String JEDIS_MASTER_NAME = "jedis.master-name";

    private <T> T propValue(ConditionContext context, String key, Class<T> cls) {
        return context.getEnvironment().getProperty(key, cls);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        JedisModel model = propValue(context,JEDIS_MODEL, JedisModel.class);
        Assert.notNull(model, () -> new ApiException("-1", JEDIS_MODEL + "不能为空"));
        Set<String> address = propValue(context, JEDIS_ADDRESS, Set.class);
        Assert.notEmpty(address, () -> new ApiException("-1", JEDIS_ADDRESS + "不能为空"));
        //jedis模型参数校验
        JedisModel[] expression = (JedisModel[]) Objects.requireNonNull(metadata.getAnnotationAttributes(EnableJedis.class.getName())).get("value");
        Assert.isFalse(expression == null || expression.length <= 0, () -> new ApiException(StatusCode.SYSTEM_ERROR));
        List<JedisModel> list = Arrays.asList(Objects.requireNonNull(expression));
        if (list.contains(model)) {
            switch (model) {
                case SENTINEL:
                    Assert.notEmpty(BaseUtil.trim(propValue(context, JEDIS_MASTER_NAME, String.class)), () -> new ApiException("-1", JEDIS_MASTER_NAME + "不能为空"));
                    break;
                case STANDALONE:
                case CLUSTER:
                    break;
                default:
                    throw new ApiException("-1", "未实现的Jedis操作模式");
            }
            return new ConditionOutcome(true, "@EnableJedis");
        } else {
            return new ConditionOutcome(false, "@EnableJedis");
        }
    }

}
