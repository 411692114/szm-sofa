package com.sinszm.sofa.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.sinszm.sofa.Swagger3Properties;
import com.sinszm.sofa.annotation.ResultBody;
import com.sinszm.sofa.response.Result;
import com.sinszm.sofa.response.ResultUtil;
import com.sinszm.sofa.service.DfsService;
import com.sinszm.sofa.service.support.UploadInfo;
import com.sinszm.sofa.util.BaseUtil;
import com.sinszm.sofa.util.SpringHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

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

    @Resource
    private DfsService dfsService;

    @SneakyThrows
    @ApiOperation(value = "upload")
    @PostMapping(value = "/upload")
    @ResultBody
    public UploadInfo upload(@RequestParam("file") MultipartFile file) {
        long fileSize = file.getSize();
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        String fileExt = FileNameUtil.extName(fileName);
        byte[] fileBytes = IoUtil.readBytes(file.getInputStream());
        System.out.println("------------------");
        System.out.println(fileName);
        System.out.println(contentType);
        System.out.println(fileSize);
        System.out.println("------------------");
        return dfsService.upload(fileBytes, fileSize, contentType, fileExt);
    }

    @ApiOperation(value = "下载文档")
    @GetMapping(value = "/download")
    public ResponseEntity<InputStreamResource> download() {
        return dfsService.download("测试1.mp4", "sinszm", "2021/04/26/a44a5e607df64b23aa90128c2cbc5fe6.mp4");
    }

}
