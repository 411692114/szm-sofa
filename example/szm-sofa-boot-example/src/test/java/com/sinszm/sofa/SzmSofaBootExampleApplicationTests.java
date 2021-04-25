package com.sinszm.sofa;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import com.alipay.sofa.test.runner.SofaBootRunner;
import com.sinszm.sofa.dao.DemoMapper;
import com.sinszm.sofa.dao.DemoTestMapper;
import com.sinszm.sofa.service.DemoService;
import com.sinszm.sofa.service.DfsService;
import com.sinszm.sofa.util.SqlUtil;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@RunWith(SofaBootRunner.class)
@SpringBootTest(classes = SzmSofaBootExampleApplication.class)
public class SzmSofaBootExampleApplicationTests {

    @Resource
    private DemoService demoService;

    @Resource
    private DemoMapper demoMapper;

    @Resource
    private DemoTestMapper demoTestMapper;

    @Resource
    private DfsService dfsService;

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

    @SneakyThrows
    @Test
    public void downTest() {

        InputStream in = dfsService.download("hello", "2021/04/25/74cd3eaa4ae84beb8182d150c01e2309.mp4");

        FileOutputStream os = new FileOutputStream(new File("D:/c.mp4"));

        IoUtil.write(os, true, IoUtil.readBytes(in));

    }

}
