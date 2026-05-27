package com.lnsoft.device.api.warehouse.vo;

import com.lnsoft.device.api.warehouse.entity.CheckTaskDevice;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author cwb
 * @date 2024/4/24
 */
@Data
public class CheckDeviceHandleVo {

	@ApiModelProperty(value = "属性名称")
	private String property;

	@ApiModelProperty(value = "修改前")
	private String lastComment;

	@ApiModelProperty(value = "修改后")
	private String newComment;
}
