package com.sinszm.sofa.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinszm.sofa.entity.Demo;

/**
 * @author fh411
 */
@DS("mysql")
public interface DemoMapper extends BaseMapper<Demo> {
}
