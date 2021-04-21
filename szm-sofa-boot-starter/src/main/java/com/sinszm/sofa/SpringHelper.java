package com.sinszm.sofa;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * Spring上下文工具
 *
 * @author sinszm
 */
public class SpringHelper {

    private ApplicationContext context = null;

    private static class Spring {
        private static final SpringHelper HOLDER = new SpringHelper();
    }

    private SpringHelper() {
    }

    public static SpringHelper instance() {
        return Spring.HOLDER;
    }

    SpringHelper create() {
        if (this.context == null) {
            this.context = SpringUtil.getApplicationContext();
        }
        return this;
    }

    /**
     * Spring上下文
     * @return  实例
     */
    public ApplicationContext context() {
        return create().context;
    }

    /**
     * 获取类实例
     * @param clazz 类
     * @param <T>   泛型
     * @return      实例
     */
    public <T> T getBean(Class<T> clazz){
        return context().getBean(clazz);
    }

    /**
     * 获取名称实例
     * @param name  实例名
     * @return      实例
     */
    public Object getBean(String name){
        return context().getBean(name);
    }

    /**
     * 获取名称实例并转换为泛型类
     * @param name      实例名
     * @param clazz     转换目标类
     * @param <T>       泛型
     * @return          实例
     */
    public <T> T getBean(String name,Class<T> clazz){
        return context().getBean(name, clazz);
    }

    /**
     * 获取Spring环境信息
     * @return  环境信息
     */
    public Environment activeProfile() {
        return context().getEnvironment();
    }

}
