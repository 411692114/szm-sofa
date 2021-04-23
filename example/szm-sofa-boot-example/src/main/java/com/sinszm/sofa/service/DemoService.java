package com.sinszm.sofa.service;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.sinszm.sofa.dao.DemoMapper;
import com.sinszm.sofa.dao.DemoTestMapper;
import com.sinszm.sofa.entity.Demo;
import com.sinszm.sofa.entity.DemoTest;
import com.sinszm.sofa.util.BaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author fh411
 */
@Slf4j
@Service
public class DemoService {

    @Resource
    private DemoMapper demoMapper;

    @Resource
    private DemoTestMapper demoTestMapper;

    @DSTransactional
    public void add() {
        Demo demo = Demo.builder()
                .id(BaseUtil.uuid())
                .remark(System.currentTimeMillis() + "")
                .build();
        demoMapper.insert(demo);
        demo.setId(BaseUtil.uuid());
        demoMapper.insert(demo);
        log.info("我是add1");
    }

    @DSTransactional
    public void add2() {
        DemoTest demo = DemoTest.builder()
                .id(System.currentTimeMillis())
                .body("我是GPF库")
                .build();
        demoTestMapper.insert(demo);
        demo.setId(System.currentTimeMillis()+1);
        demoTestMapper.insert(demo);
        log.debug("我是add2");
    }




}
