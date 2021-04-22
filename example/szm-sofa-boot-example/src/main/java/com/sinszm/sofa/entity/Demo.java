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
@TableName(value = "hi_example", autoResultMap = true)
public class Demo {

    @TableId
    private String id;

    @TableField(value = "remark")
    private String remark;

}
