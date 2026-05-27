package com.lnsoft.device.api.i6000.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: xuel
 * @CreateTime: 2026/3/1 16:18
 * @Description: I6000EntityIdDTO
 */

@Data
public class I6000EntityIdDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 区域编码
     */
    @ApiModelProperty(value = "区域编码")
    private String area;

    /**
     * 设备编码集合
     */
    @ApiModelProperty(value = "设备编码集合")
    private List<String> deviceList;

}
