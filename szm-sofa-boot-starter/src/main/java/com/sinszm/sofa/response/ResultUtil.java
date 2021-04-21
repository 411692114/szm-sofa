package com.sinszm.sofa.response;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 响应结果工具
 * @author sinszm
 */
@Slf4j
public class ResultUtil {

    /**
     * 默认成功
     * @return  请求结果
     */
    public static Result<String> ok() {
        return ResultUtil.ok("");
    }

    /**
     * 带数据返回
     * @param data  数据
     * @param <T>   泛型
     * @return      请求结果
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> ok(T data) {
        return (Result<T>) Result.builder()
                .code(StatusCode.OK.getCode())
                .message(StatusCode.OK.getMessage())
                .body(data)
                .throwable(null)
                .build();
    }

    /**
     * 系统级别默认错误代码失败
     * @param code          错误代码
     * @param throwable     异常信息
     * @return              响应结果
     */
    public static Result<Object> fail(StatusCode code, Throwable throwable) {
        StatusCode statusCode = Optional.ofNullable(code).orElse(StatusCode.SYSTEM_ERROR);
        if (throwable != null) {
            log.error("请求错误结果:", throwable);
        }
        return Result.builder()
                .code(statusCode.getCode())
                .message(statusCode.getMessage())
                .body("")
                .throwable(throwable)
                .build();
    }

    /**
     * 自定义任意结果错误信息响应
     * @param code          错误代码
     * @param message       错误描述
     * @param throwable     异常信息
     * @return              响应结果
     */
    public static Result<Object> fail(String code ,String message, Throwable throwable) {
        String statusCode = Optional.ofNullable(code).orElse(StatusCode.SYSTEM_ERROR.getCode());
        String statusMessage = Optional.ofNullable(message).orElse(StatusCode.SYSTEM_ERROR.getMessage());
        if (throwable != null) {
            log.error("请求错误结果:", throwable);
        }
        return Result.builder()
                .code(StrUtil.trimToEmpty(statusCode))
                .message(StrUtil.trimToEmpty(statusMessage))
                .body("")
                .throwable(throwable)
                .build();
    }

}
