package com.sinszm.sofa.vo;

import com.sinszm.sofa.enums.Evaluative;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 评价信息
 *
 * @author admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluativeInfo implements Serializable {

    private static final long serialVersionUID = 6788035514356297418L;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 评价者用户ID
     */
    private String userId;

    /**
     * 评价级别
     */
    private Evaluative level;

    /**
     * 描述
     */
    private String describe;

}
