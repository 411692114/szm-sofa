package com.sinszm.sofa.vo;

import cn.hutool.core.lang.Assert;
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
 * 买家信息
 *
 * @author admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "买家信息")
public class Buyer implements Serializable {

    private static final long serialVersionUID = -4717329736737394134L;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "买家用户ID")
    private String userId;

    public Buyer checkUserId() {
        Assert.notNull(this.userId, error("买家用户ID不能为空"));
        Assert.isFalse(BaseUtil.trim(this.userId).length() > 64, error("买家用户ID不能超过64个字符"));
        this.userId = BaseUtil.trim(this.userId);
        return this;
    }

    /**
     * 用户名
     */
    @ApiModelProperty(value = "买家用户姓名或昵称")
    private String userName;

    public Buyer checkUserName() {
        if (!BaseUtil.isEmpty(this.userName)) {
            Assert.isFalse(BaseUtil.trim(this.userName).length() > 200, error("买家用户姓名不能超过200个字符"));
        }
        this.userName = BaseUtil.trim(this.userName);
        return this;
    }

    /**
     * 开放id
     */
    @ApiModelProperty(value = "买家微信openid")
    private String openId;

    public Buyer checkOpenId() {
        if (!BaseUtil.isEmpty(this.openId)) {
            Assert.isFalse(BaseUtil.trim(this.openId).length() > 64, error("开放ID不能超过64个字符"));
        }
        this.openId = BaseUtil.trim(this.openId);
        return this;
    }

    /**
     * 聚合ID
     */
    @ApiModelProperty(value = "买家微信unionId")
    private String unionId;

    public Buyer checkUnionId() {
        if (!BaseUtil.isEmpty(this.unionId)) {
            Assert.isFalse(BaseUtil.trim(this.unionId).length() > 64, error("聚合ID不能超过64个字符"));
        }
        this.unionId = BaseUtil.trim(this.unionId);
        return this;
    }

    /**
     * 电话
     */
    @ApiModelProperty(value = "买家联系电话")
    private String mobile;

    public Buyer checkMobile() {
        if (!BaseUtil.isEmpty(this.mobile)) {
            Assert.isFalse(BaseUtil.trim(this.mobile).length() > 13, error("联系电话不能超过13个字符"));
        }
        this.mobile = BaseUtil.trim(this.mobile);
        return this;
    }

    /**
     * 地址
     */
    @ApiModelProperty(value = "买家联系地址")
    private String address;

    public Buyer checkAddress() {
        if (!BaseUtil.isEmpty(this.address)) {
            Assert.isFalse(BaseUtil.trim(this.address).length() > 200, error("地址不能超过200个字符"));
        }
        this.address = BaseUtil.trim(this.address);
        return this;
    }

    /**
     * 收货地址
     */
    @ApiModelProperty(value = "买家收获地址")
    private String receivingAddress;

    public Buyer checkReceivingAddress() {
        if (!BaseUtil.isEmpty(this.receivingAddress)) {
            Assert.isFalse(BaseUtil.trim(this.receivingAddress).length() > 200, error("收货地址不能超过200个字符"));
        }
        this.receivingAddress = BaseUtil.trim(this.receivingAddress);
        return this;
    }

    /**
     * 检查所有
     *
     * @return {Buyer}
     */
    public Buyer checkAll() {
        return this.checkUserId()
                .checkUserName()
                .checkOpenId()
                .checkUnionId()
                .checkMobile()
                .checkAddress()
                .checkReceivingAddress();
    }

}
