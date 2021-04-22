package com.sinszm.sofa.service;

import com.sinszm.sofa.annotation.OrmTransactional;
import com.sinszm.sofa.dao.DemoMapper;
import com.sinszm.sofa.entity.Demo;
import com.sinszm.sofa.util.BaseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fh411
 */
@Service
public class DemoService {

    @Resource
    private DemoMapper demoMapper;

    @OrmTransactional
    public void add() {

        Demo demo = Demo.builder()
                .id(BaseUtil.uuid())
                .remark("测试一下2")
                .build();
        demoMapper.insert(demo);
        demo.setId(BaseUtil.uuid());
        demoMapper.insert(demo);
    }






}
