package com.sinszm.sofa.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 买家信息
 *
 * @author admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Buyer implements Serializable {

    private static final long serialVersionUID = -4717329736737394134L;

    /**
     * 用户id
     */
    private String userId;

    public Buyer checkUserId() {
        return this;
    }

    /**
     * 用户名
     */
    private String userName;

    public Buyer checkUserName() {
        return this;
    }

    /**
     * 开放id
     */
    private String openId;

    public Buyer checkOpenId() {
        return this;
    }

    /**
     * 聚合ID
     */
    private String unionId;

    public Buyer checkUnionId() {
        return this;
    }

    /**
     * 电话
     */
    private String mobile;

    public Buyer checkMobile() {
        return this;
    }

    /**
     * 地址
     */
    private String address;

    public Buyer checkAddress() {
        return this;
    }

    /**
     * 收货地址
     */
    private String receivingAddress;

    public Buyer checkReceivingAddress() {
        return this;
    }

}
