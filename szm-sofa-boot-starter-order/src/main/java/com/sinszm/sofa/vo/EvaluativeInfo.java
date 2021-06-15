package com.sinszm.sofa.vo;

import cn.hutool.core.lang.Assert;
import com.sinszm.sofa.enums.Evaluative;
import com.sinszm.sofa.util.BaseUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.sinszm.sofa.support.Constant.error;

/**
 * 评价信息
 *
 * @author admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "评价信息")
public class EvaluativeInfo implements Serializable {

    private static final long serialVersionUID = 6788035514356297418L;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单ID")
    private String orderId;

    public EvaluativeInfo checkOrderId() {
        Assert.notNull(this.orderId, error("订单ID不能为空"));
        Assert.isFalse(BaseUtil.trim(this.orderId).length() > 32, error("订单ID不能超过32个字符"));
        this.orderId = BaseUtil.trim(this.orderId);
        return this;
    }

    /**
     * 评价者用户ID
     */
    @ApiModelProperty(value = "评价用户ID")
    private String userId;

    public EvaluativeInfo checkUserId() {
        Assert.notNull(this.userId, error("买家用户ID不能为空"));
        Assert.isFalse(BaseUtil.trim(this.userId).length() > 64, error("买家用户ID不能超过64个字符"));
        this.userId = BaseUtil.trim(this.userId);
        return this;
    }

    /**
     * 评价级别
     */
    @ApiModelProperty(value = "评价等级，可用于前端进行分数对照")
    private Evaluative level;

    public EvaluativeInfo checkLevel() {
        Assert.notNull(this.level, error("评价级别不能为空"));
        return this;
    }

    /**
     * 描述
     */
    @ApiModelProperty(value = "评价描述")
    private String describe;

    public EvaluativeInfo checkDescribe() {
        if (!BaseUtil.isEmpty(this.describe)) {
            Assert.isFalse(BaseUtil.trim(this.describe).length() > 200, error("评价描述不能超过200个字符"));
        }
        this.describe = BaseUtil.trim(this.describe);
        return this;
    }

    /**
     * 检查所有
     *
     * @return {EvaluativeInfo}
     */
    public EvaluativeInfo checkAll() {
        return this.checkOrderId()
                .checkUserId()
                .checkLevel()
                .checkDescribe();
    }

}
