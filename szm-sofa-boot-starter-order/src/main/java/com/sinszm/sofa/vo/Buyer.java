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

    /**
     * 用户名
     */
    private String userName;

    /**
     * 开放id
     */
    private String openId;

    /**
     * 聚合ID
     */
    private String unionId;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 地址
     */
    private String address;

    /**
     * 收货地址
     */
    private String receivingAddress;

}
