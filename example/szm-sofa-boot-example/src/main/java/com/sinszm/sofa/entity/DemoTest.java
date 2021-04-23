package com.sinszm.sofa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fh411
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "pf_test", autoResultMap = true)
public class DemoTest {

    @TableId
    private Long id;

    @TableField(value = "body")
    private String body;

}
