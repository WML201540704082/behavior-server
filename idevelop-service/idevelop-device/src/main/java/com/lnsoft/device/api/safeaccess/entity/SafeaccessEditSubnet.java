package com.lnsoft.device.api.safeaccess.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SafeaccessEditSubnet
 * @description:
 * @author: zhangs
 * @create: 2024-03-09 15:20
 **/
@Data
public class SafeaccessEditSubnet implements Serializable {

	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "id")
	private String id;

	@ApiModelProperty(value = "原网段")
	private String subnetOld;

	@ApiModelProperty(value = "现网段")
	private String subnetNew;

	@ApiModelProperty(value = "原网段已用地址")
	private String ipUsedCount;

	@ApiModelProperty(value = "现网段空闲地址")
	private String ipAvailableCount;

	@ApiModelProperty(value = "原ip")
	private String oldIp;

	@ApiModelProperty(value = "现ip")
	private String newIp;

	@ApiModelProperty(value = "createTime")
	private Date createTime;

}
