package com.sinszm.sofa.job;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.sinszm.sofa.exception.ApiException;
import com.sinszm.sofa.vo.JobParam;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * 默认的简单工作处理器
 *
 * @author sinszm
 */
public class DefaultSimpleJob implements Job {

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) {
        Map<String, Object> map = context.getMergedJobDataMap();
        JobParam param = BeanUtil.toBeanIgnoreCase(map, JobParam.class, true);
        param.checkAll();
        //获取执行实例
        Object object = SpringUtil.getBean(param.getClazz());
        Assert.notNull(object, () -> new ApiException("202", "实例未找到"));
        //参数数组获取对应类型数组
        Class<?>[] cla = Arrays.stream(param.getParams()).map(Object::getClass).toArray(Class[]::new);
        //方法验证
        Method method = ReflectUtil.getPublicMethod(param.getClazz(), param.getMethodName(), cla);
        Assert.notNull(method, () -> new ApiException("202", "执行方法未找到"));
        //执行调用
        ReflectUtil.invoke(object, method, param.getParams());
    }
}
