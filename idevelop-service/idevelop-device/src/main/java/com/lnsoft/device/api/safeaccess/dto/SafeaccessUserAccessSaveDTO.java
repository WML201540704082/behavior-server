package com.lnsoft.device.api.safeaccess.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: SafeaccessUserAccessSaveDTO
 * @description:
 * @author: zhangs
 * @create: 2024-03-25 19:17
 **/
@Data
public class SafeaccessUserAccessSaveDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "地址")
	private String address;
	@ApiModelProperty(value = "联系电话")
	private String phone;
	@ApiModelProperty(value = "设备ID")
	private String deviceId;
	@ApiModelProperty(value = "设备类型")
	private String deviceType;
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;
	@ApiModelProperty(value = "所属子网ID")
	private String subnetId;
	@ApiModelProperty(value = "认证用户")
	private String approveuUser;
	@ApiModelProperty(value = "认证密码")
	private String approveuPassword;
	@ApiModelProperty(value = "mac地址")
	private String macAddress;
	@ApiModelProperty(value = "ip地址")
	private String ipAddress;
	@ApiModelProperty(value = "状态")
	private String code;
	@ApiModelProperty(value = "受理人")
	private String acceptMan;
	@ApiModelProperty(value = "受理时间")
	private String acceptDate;
	@ApiModelProperty(value = "入网申请工单ID")
	private String netInApplyId;
	@ApiModelProperty(value = "填报人")
	private String fillMan;
	@ApiModelProperty(value = "填报时间")
	private String fillTime;
	@ApiModelProperty(value = "部门编码")
	private String deptCode;
	@ApiModelProperty(value = "责任人")
	private String miUser;
	@ApiModelProperty(value = "使用人")
	private String miChargeUser;
	@ApiModelProperty(value = "入网开始时间")
	private String startTime;
	@ApiModelProperty(value = "允许入网时间")
	private String allowDays;
	@ApiModelProperty(value = "填报人姓名")
	private String fullUsername;
	@ApiModelProperty(value = "是否启用802.1x接入认证")
	private String is802;
	@ApiModelProperty(value = "是否认证")
	private String isAccess;
	@ApiModelProperty(value = "设备指纹")
	private String fingerprint;
	@ApiModelProperty(value = "入网类型,1-永久授权,2-临时授权")
	private String applyType;
	@ApiModelProperty(value = "临时禁用:1-临时禁用2-解除禁用")
	private Byte disableStatus;
	@ApiModelProperty(value = "备注")
	private String remark;

}
