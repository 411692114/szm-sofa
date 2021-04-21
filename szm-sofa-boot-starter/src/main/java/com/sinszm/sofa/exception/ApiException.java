package com.sinszm.sofa.exception;

import cn.hutool.core.util.StrUtil;
import com.sinszm.sofa.response.StatusCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 自定义业务异常
 * @author sinszm
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ApiException extends RuntimeException {

    private String code;

    private String message;

    public ApiException() {
        super(StrUtil.trimToEmpty(StatusCode.SYSTEM_ERROR.getCode()) + ":" + StrUtil.trimToEmpty(StatusCode.SYSTEM_ERROR.getMessage()));
    }

    public ApiException(String code, String message) {
        super(StrUtil.trimToEmpty(code) + ":" + StrUtil.trimToEmpty(message));
        this.code = code;
        this.message = message;
    }

    public ApiException(@NotNull StatusCode statusCode) {
        super(StrUtil.trimToEmpty(statusCode.getCode()) + ":" + StrUtil.trimToEmpty(statusCode.getMessage()));
    }

}
