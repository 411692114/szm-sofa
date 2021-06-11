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

    public EvaluativeInfo checkOrderId() {
        return this;
    }

    /**
     * 评价者用户ID
     */
    private String userId;

    public EvaluativeInfo checkUserId() {
        return this;
    }

    /**
     * 评价级别
     */
    private Evaluative level;

    public EvaluativeInfo checkLevel() {
        return this;
    }

    /**
     * 描述
     */
    private String describe;

    public EvaluativeInfo checkDescribe() {
        return this;
    }

}
