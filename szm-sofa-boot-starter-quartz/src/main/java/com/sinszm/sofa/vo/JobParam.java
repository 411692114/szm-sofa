package com.sinszm.sofa.vo;

import cn.hutool.core.lang.Assert;
import com.sinszm.sofa.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 工作参数
 *
 * @author sinszm
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobParam implements Serializable {

    private static final long serialVersionUID = -2827864989407728510L;

    /**
     * clazz
     */
    private Class<?> clazz;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数
     */
    private Object[] params;

    /**
     * 检查所有
     *
     */
    public void checkAll() {
        Assert.notNull(clazz, () -> new ApiException("202", "实例类不能为空"));
        Assert.notEmpty(methodName,() -> new ApiException("202", "请指定实例类中执行方法名称"));
        this.params = params == null ? new Object[0] : params;
    }

}
