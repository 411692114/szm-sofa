package com.sinszm.sofa.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
        return this;
    }

    /**
     * 用户名
     */
    private String userName;

    public Seller checkUserName() {
        return this;
    }

    /**
     * 店铺ID
     */
    private String shopId;

    public Seller checkShopId() {
        return this;
    }

    /**
     * 店铺名称
     */
    private String shopName;

    public Seller checkShopName() {
        return this;
    }

    /**
     * 店铺电话
     */
    private String shopMobile;

    public Seller checkShopMobile() {
        return this;
    }

    /**
     * 店铺地址
     */
    private String shopAddress;

    public Seller checkShopAddress() {
        return this;
    }

}
