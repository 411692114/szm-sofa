package com.sinszm.sofa.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.sinszm.sofa.Swagger3Properties;
import com.sinszm.sofa.annotation.ResultBody;
import com.sinszm.sofa.response.Result;
import com.sinszm.sofa.response.ResultUtil;
import com.sinszm.sofa.util.BaseUtil;
import com.sinszm.sofa.util.SpringHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
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
    @GetMapping(value = "/hello")
    @ResultBody
    public String hello(@RequestParam(required = false) String name) {
        System.out.println("---");
        System.out.println(JSONUtil.toJsonPrettyStr(SpringHelper.instance().getBean(Swagger3Properties.class)));
        System.out.println(SpringHelper.instance().environment().getProperty("spring.application.name"));
        System.out.println("---");
        return StrUtil.trimToEmpty(name);
    }

    @ApiOperation(value = "测试Exception")
    @GetMapping(value = "/hello2", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResultBody
    public Result<String> hello2() {
        return ResultUtil.ok(BaseUtil.md5("我是2号"));
    }

    @ApiOperation(value = "测试Exception")
    @GetMapping(value = "/hello4", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResultBody
    public void hello3() {
        System.out.println("adfasfjasfalksfaskfjlasfasdf");
    }

    @ApiOperation(value = "测试Exception")
    @GetMapping(value = "/hello5", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResultBody
    public Integer hello5() {
        return Integer.MAX_VALUE;
    }

}
