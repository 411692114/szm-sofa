package com.sinszm.sofa.response;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.sinszm.sofa.annotation.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;

/**
 * 正确结果简化响应处理器
 * @author sinszm
 */
@Slf4j
@ControllerAdvice(annotations = {
        RestController.class,
        Controller.class
})
public class SuccessResultAdvice implements ResponseBodyAdvice<Object> {

    private static final Class<? extends Annotation> ANNOTATION_TYPE = ResultBody.class;

    @Override
    public boolean supports(MethodParameter parameter, Class<? extends HttpMessageConverter<?>> clz) {
        return AnnotatedElementUtils.hasAnnotation(parameter.getContainingClass(), ANNOTATION_TYPE)
                || parameter.hasMethodAnnotation(ANNOTATION_TYPE);
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter parameter,
                                  MediaType type,
                                  Class<? extends HttpMessageConverter<?>> clz,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body == null) {
            return ResultUtil.ok("");
        }
        if (body instanceof Result) {
            return body;
        }
        if (body instanceof ResponseEntity) {
            return body;
        }
        if (body instanceof String) {
            if (type.isCompatibleWith(MediaType.APPLICATION_JSON)
                    || type.isCompatibleWith(MediaType.APPLICATION_JSON_UTF8)) {
                Result<Object> result = ResultUtil.ok(body);
                JSONObject jsonObject = JSONUtil.parseObj(result);
                jsonObject.set("throwable", "");
                return JSONUtil.toJsonStr(jsonObject, 4);
            }
            return body;
        }
        return ResultUtil.ok(body);
    }

}