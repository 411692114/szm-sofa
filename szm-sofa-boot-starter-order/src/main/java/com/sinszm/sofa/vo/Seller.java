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

    /**
     * 用户名
     */
    private String userName;

    /**
     * 店铺ID
     */
    private String shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺电话
     */
    private String shopMobile;

    /**
     * 店铺地址
     */
    private String shopAddress;

}
