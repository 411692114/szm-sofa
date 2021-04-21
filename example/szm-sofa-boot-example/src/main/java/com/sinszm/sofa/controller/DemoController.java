package com.sinszm.sofa.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.sinszm.sofa.SpringHelper;
import com.sinszm.sofa.Swagger3Properties;
import com.sinszm.sofa.exception.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sinszm
 */
@Api(tags = "测试接口")
@RestController
public class DemoController {

    @ApiOperation(value = "Hello测试")
    @ApiImplicitParam(name = "name", value = "姓名", dataTypeClass = String.class)
    @GetMapping("/hello")
    public String hello(@RequestParam(required = false) String name) {
        System.out.println("---");
        System.out.println(JSONUtil.toJsonPrettyStr(SpringHelper.instance().getBean(Swagger3Properties.class)));
        System.out.println(SpringHelper.instance().environment().getProperty("spring.application.name"));
        System.out.println("---");
        return StrUtil.trimToEmpty(name);
    }

    @ApiOperation(value = "测试Exception")
    @GetMapping("/hello2")
    public String hello2() {
        throw new ApiException("100","1231231");
    }


}
