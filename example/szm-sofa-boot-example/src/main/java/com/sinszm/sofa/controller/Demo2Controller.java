package com.sinszm.sofa.controller;

import com.sinszm.sofa.exception.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author sinszm
 */
@Api(tags = "测试接口2")
@Controller
public class Demo2Controller {

    @ApiOperation(value = "测试Exception")
    @GetMapping(value = "/hello3")
    public String hello2() {
        throw new ApiException("100","1231231");
    }


}
