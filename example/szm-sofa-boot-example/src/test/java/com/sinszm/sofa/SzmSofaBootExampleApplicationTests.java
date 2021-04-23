package com.sinszm.sofa;

import cn.hutool.json.JSONUtil;
import com.alipay.sofa.test.runner.SofaBootRunner;
import com.sinszm.sofa.dao.DemoMapper;
import com.sinszm.sofa.dao.DemoTestMapper;
import com.sinszm.sofa.service.DemoService;
import com.sinszm.sofa.util.SqlUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@RunWith(SofaBootRunner.class)
@SpringBootTest(classes = SzmSofaBootExampleApplication.class)
public class SzmSofaBootExampleApplicationTests {

    @Resource
    private DemoService demoService;

    @Resource
    private DemoMapper demoMapper;

    @Resource
    private DemoTestMapper demoTestMapper;

    @Test
    public void testInsert() {
        demoService.add();
        demoService.add2();

        System.out.println(
                JSONUtil.toJsonPrettyStr(SqlUtil.selectMaps(1, 5, demoMapper, null))
        );

        System.out.println(
                JSONUtil.toJsonPrettyStr(SqlUtil.selectMaps(1, 5, demoTestMapper, null))
        );

    }

}
