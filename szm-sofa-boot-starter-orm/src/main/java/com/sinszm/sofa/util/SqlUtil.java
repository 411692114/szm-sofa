package com.sinszm.sofa.util;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 数据操作工具
 * @author fh411
 */
public class SqlUtil {

    /**
     * 基本分页方法
     * @param page          分页
     * @param mapper        调用器
     * @param queryWrapper  查询条件
     * @param <T>           泛型
     * @return              分页数据
     */
    public static <T> Page<T> select(Page<T> page, BaseMapper<T> mapper, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        Page<T> result = page == null ? new Page<>(1, 10) : page;
        mapper.selectPage(result, queryWrapper);
        return result;
    }

    /**
     * 基本分页方法
     * @param current       当前页
     * @param size          每页数量
     * @param mapper        调用器
     * @param queryWrapper  查询条件
     * @param <T>           泛型
     * @return              分页数据
     */
    public static <T> Page<T> select(long current, long size, BaseMapper<T> mapper, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        return select(new Page<>(current <= 0 ? 1 : current, size <= 0 ? 10 : size), mapper, queryWrapper);
    }

    /**
     * 基本分页方法
     * @param page          分页
     * @param mapper        调用器
     * @param queryWrapper  查询条件
     * @param <T>           泛型
     * @return              分页数据
     */
    public static <T> Page<Map<String, Object>> selectMaps(Page<Map<String, Object>> page, BaseMapper<T> mapper, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        Page<Map<String, Object>> result = page == null ? new Page<>(1, 10) : page;
        return mapper.selectMapsPage(result, queryWrapper);
    }

    /**
     * 基本分页方法
     * @param current       当前页
     * @param size          每页数量
     * @param mapper        调用器
     * @param queryWrapper  查询条件
     * @param <T>           泛型
     * @return              分页数据
     */
    public static <T> Page<Map<String, Object>> selectMaps(long current, long size, BaseMapper<T> mapper, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        return selectMaps(new Page<>(current <= 0 ? 1 : current, size <= 0 ? 10 : size), mapper, queryWrapper);
    }

}
