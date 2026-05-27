package com.lnsoft.device.api.cmdb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2026/2/28 16:53
 * @Description: CmdbCardDTO
 */
@Data
public class CmdbCardDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性ID")
    private Long id;

    @ApiModelProperty(value = "模型ID")
    private Long ciId;


}
