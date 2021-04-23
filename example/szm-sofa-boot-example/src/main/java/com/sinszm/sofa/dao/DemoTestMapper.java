package com.sinszm.sofa.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinszm.sofa.entity.DemoTest;

/**
 * @author fh411
 */
@DS("mysql2")
public interface DemoTestMapper extends BaseMapper<DemoTest> {
}
