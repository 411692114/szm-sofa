package com.sinszm.sofa.vo;

import cn.hutool.core.lang.Assert;
import com.sinszm.sofa.util.BaseUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.sinszm.sofa.support.Constant.error;

/**
 * 卖方信息
 *
 * @author admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seller implements Serializable {

    private static final long serialVersionUID = 8898794839529332792L;

    /**
     * 用户id
     */
    private String userId;

    public Seller checkUserId() {
        Assert.notNull(this.userId, error("卖家用户ID不能为空"));
        Assert.isFalse(BaseUtil.trim(this.userId).length() > 64, error("卖家用户ID不能超过64个字符"));
        this.userId = BaseUtil.trim(this.userId);
        return this;
    }

    /**
     * 用户名
     */
    private String userName;

    public Seller checkUserName() {
        if (!BaseUtil.isEmpty(this.userName)) {
            Assert.isFalse(BaseUtil.trim(this.userName).length() > 200, error("卖家用户姓名不能超过200个字符"));
        }
        this.userName = BaseUtil.trim(this.userName);
        return this;
    }

    /**
     * 店铺ID
     */
    private String shopId;

    public Seller checkShopId() {
        if (!BaseUtil.isEmpty(this.shopId)) {
            Assert.isFalse(BaseUtil.trim(this.shopId).length() > 64, error("店铺ID不能超过64个字符"));
        }
        this.shopId = BaseUtil.trim(this.shopId);
        return this;
    }

    /**
     * 店铺名称
     */
    private String shopName;

    public Seller checkShopName() {
        if (!BaseUtil.isEmpty(this.shopName)) {
            Assert.isFalse(BaseUtil.trim(this.shopName).length() > 200, error("店铺名称不能超过200个字符"));
        }
        this.shopName = BaseUtil.trim(this.shopName);
        return this;
    }

    /**
     * 店铺电话
     */
    private String shopMobile;

    public Seller checkShopMobile() {
        if (!BaseUtil.isEmpty(this.shopMobile)) {
            Assert.isFalse(BaseUtil.trim(this.shopMobile).length() > 13, error("店铺联系电话不能超过13个字符"));
        }
        this.shopMobile = BaseUtil.trim(this.shopMobile);
        return this;
    }

    /**
     * 店铺地址
     */
    private String shopAddress;

    public Seller checkShopAddress() {
        if (!BaseUtil.isEmpty(this.shopAddress)) {
            Assert.isFalse(BaseUtil.trim(this.shopAddress).length() > 200, error("店铺地址不能超过200个字符"));
        }
        this.shopAddress = BaseUtil.trim(this.shopAddress);
        return this;
    }

    /**
     * 检查所有
     *
     * @return {@link Seller}
     */
    public Seller checkAll() {
        return this.checkUserId()
                .checkUserName()
                .checkShopId()
                .checkShopName()
                .checkShopMobile()
                .checkShopAddress();
    }

}
