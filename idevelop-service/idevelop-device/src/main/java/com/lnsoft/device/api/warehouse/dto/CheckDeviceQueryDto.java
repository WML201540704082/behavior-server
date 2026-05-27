package com.lnsoft.device.api.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author cwb
 * @date 2024/4/19
 */
@Data
public class CheckDeviceQueryDto {

	/**
	 * 任务ID
	 */
	@ApiModelProperty(value = "任务ID")
	private String taskId;

	/**
	 * 所在部门
	 */
	@ApiModelProperty(value = "所在部门")
	private String receiveDeptCode;

	/**
	 * 设备分类
	 */
	@ApiModelProperty(value = "设备分类")
	private String deviceCategory;
	/**
	 * 设备类型
	 */
	@ApiModelProperty(value = "设备类型")
	private String deviceType;
	/**
	 * 盘点状态(0:已盘点，1:待盘点，2:注册/盘盈设备, 3:盘亏设备)
	 */
	@ApiModelProperty(value = "盘点状态(0:已盘点，1:待盘点，2:注册/盘盈设备, 3:盘亏设备)")
	private String checkStatus;

	@ApiModelProperty(value = "是否异常设备(0:是, 1:否)")
	private Long isException;
	/**
	 * 设备状态
	 */
	@ApiModelProperty(value = "设备状态")
	private String deviceStatus;
	/**
	 * 处置状态(0:待处置, 1:已处置)
	 */
	@ApiModelProperty(value = "处置状态(0:待处置, 1:已处置)")
	private String disposeStatus;
	/**
	 * 领用责任人
	 */
	@ApiModelProperty(value = "领用责任人")
	private String receivingPerson;
	/**
	 * 是否信创设备(0:是，1:否)
	 */
	@ApiModelProperty(value = "是否信创设备(cmdb字典值)")
	private String isITAICode;
	/**
	 * 提交人
	 */
	@ApiModelProperty(value = "申报人")
	private String subPerson;
	/**
	 * 提交人联系方式
	 */
	@ApiModelProperty(value = "申报人联系方式")
	private String subTel;

	@ApiModelProperty(value = "上期设备(2:盘盈设备, 3:盘亏设备, 4:退运, 5:临时退网)")
	private List<String> lastDevice;

	private String lastDeviceStr;

	private String ip;

	private String mac;

	private String deviceName;

	private String isOwner;

	private String account;
}
