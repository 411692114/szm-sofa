package com.sinszm.sofa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 主订单表
 *
 * @author admin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ts_order")
public class SzmOrder {

    /**
     * id
     */
    @Id
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 名字
     */
    @Column(name = "name", length = 100)
    private String name;

}
