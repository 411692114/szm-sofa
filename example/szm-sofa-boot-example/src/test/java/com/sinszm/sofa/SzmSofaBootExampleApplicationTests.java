package com.sinszm.sofa;

import cn.hutool.json.JSONUtil;
import com.alipay.sofa.test.runner.SofaBootRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sinszm.sofa.dao.DemoMapper;
import com.sinszm.sofa.entity.Demo;
import com.sinszm.sofa.service.DemoService;
import com.sinszm.sofa.util.SqlUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SofaBootRunner.class)
@SpringBootTest(classes = SzmSofaBootExampleApplication.class)
public class SzmSofaBootExampleApplicationTests {

    @Resource
    private DemoService demoService;

    @Resource
    private DemoMapper demoMapper;

    @Test
    public void testInsert() {
        demoService.add();
        List<Demo> list = demoMapper.selectList(new QueryWrapper<>());
        for (Demo demo : list) {
            System.out.println(JSONUtil.toJsonStr(demo));
        }

        System.out.println(
                JSONUtil.toJsonPrettyStr(SqlUtil.select(new Page<>(1,5), demoMapper, null))
        );

        System.out.println(
                JSONUtil.toJsonPrettyStr(SqlUtil.selectMaps(1, 5, demoMapper, null))
        );

    }

}
