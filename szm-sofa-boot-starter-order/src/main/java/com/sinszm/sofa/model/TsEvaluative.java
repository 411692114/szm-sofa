package com.sinszm.sofa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sinszm.sofa.enums.Evaluative;
import com.sinszm.sofa.enums.EvaluativeStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 评价表
 *
 * @author admin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ts_evaluative", indexes = {
        @Index(name = "ts_evaluative_order_id",columnList = "order_id"),
        @Index(name = "ts_evaluative_user_id",columnList = "user_id"),
        @Index(name = "ts_evaluative_eval_level",columnList = "eval_level"),
        @Index(name = "ts_evaluative_evaluative_status",columnList = "evaluative_status")
})
@ApiModel(value = "订单评价信息表")
public class TsEvaluative {

    /**
     * id
     */
    @ApiModelProperty(value = "主键")
    @Id
    @Column(name = "id", length = 32, nullable = false)
    private String id;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单ID")
    @Column(name = "order_id", length = 32, nullable = false)
    private String orderId;

    /**
     * 评价者用户ID
     */
    @ApiModelProperty(value = "评价用户ID")
    @Column(name = "user_id", length = 64, nullable = false)
    private String userId;

    /**
     * 评价级别
     */
    @ApiModelProperty(value = "评价级别")
    @Enumerated(EnumType.STRING)
    @Column(name = "eval_level", length = 64)
    private Evaluative evalLevel;

    /**
     * 描述
     */
    @ApiModelProperty(value = "评价描述")
    @Column(name = "describes", length = 200)
    private String describes;

    /**
     * 评价状态
     */
    @ApiModelProperty(value = "评价状态")
    @Enumerated(EnumType.STRING)
    @Column(name = "evaluative_status", nullable = false, length = 64)
    private EvaluativeStatus evaluativeStatus;

    /**
     * 创建用户id
     */
    @ApiModelProperty(value = "创建者ID")
    @Column(name = "create_user_id", length = 64, nullable = false)
    private String createUserId;

    /**
     * 创建日期时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_date_time", nullable = false)
    private Date createDateTime;

    /**
     * 更新用户id
     */
    @ApiModelProperty(value = "变更者ID")
    @Column(name = "update_user_id", length = 64, nullable = false)
    private String updateUserId;

    /**
     * 更新日期时间
     */
    @ApiModelProperty(value = "变更时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_date_time", nullable = false)
    private Date updateDateTime;

}
