package com.sinszm.sofa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单操作记录
 *
 * @author admin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ts_operation_record", indexes = {
        @Index(name = "ts_record_order_id",columnList = "order_id"),
        @Index(name = "ts_record_user_id",columnList = "user_id"),
        @Index(name = "ts_record_create_time",columnList = "create_time")
})
public class TsOperationRecord {

    /**
     * id
     */
    @Id
    @Column(name = "id", length = 32, nullable = false)
    private String id;

    /**
     * 订单id
     */
    @Column(name = "order_id", length = 32, nullable = false)
    private String orderId;

    /**
     * 评价者用户ID
     */
    @Column(name = "user_id", length = 64, nullable = false)
    private String userId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /**
     * 日志描述
     */
    @Column(name = "describe", length = 200)
    private String describe;

}
