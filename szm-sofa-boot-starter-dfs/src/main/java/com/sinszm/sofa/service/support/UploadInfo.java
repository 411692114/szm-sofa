package com.sinszm.sofa.service.support;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件上传信息
 * @author fh411
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(
        value = "文件信息",
        description = "文件信息"
)
public class UploadInfo {

    @ApiModelProperty(
            value = "bucket",
            dataType = "String",
            required = true,
            example = ""
    )
    String group;

    @ApiModelProperty(
            value = "bucket",
            dataType = "String",
            required = true,
            example = ""
    )
    String bucket;

    @ApiModelProperty(
            value = "存储路径",
            dataType = "String",
            required = true,
            example = ""
    )
    String path;

}