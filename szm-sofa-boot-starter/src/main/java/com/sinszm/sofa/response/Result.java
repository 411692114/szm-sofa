package com.sinszm.sofa.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 接口响应
 * @author sinszm
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "请求响应数据")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -8352224458209541789L;

    /**
     * 响应代码
     */
    @ApiModelProperty(value = "响应代码，成功：0，其他为失败", required = true)
    private String code;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息", required = true)
    private String message;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据")
    private T body;

    /**
     * 异常栈
     */
    @ApiModelProperty(value = "异常栈信息")
    private Throwable throwable;

}
