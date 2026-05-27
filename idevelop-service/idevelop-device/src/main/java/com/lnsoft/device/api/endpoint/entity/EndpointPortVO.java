package com.lnsoft.device.api.endpoint.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EndpointPortVO {

	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID")
	@TableId(value = "id", type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 接口名称
	 */
	@ApiModelProperty(value = "接口名称")
	private String port;
	/**
	 * 接口地址
	 */
	@ApiModelProperty(value = "接口地址")
	private String portAddress;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remake;

	/**
	 * 接口类型
	 */
	@ApiModelProperty(value = "接口类型")
	private String portType;

	@ApiModelProperty("数据频率")
	private String cycle;
	@ApiModelProperty("接口方式")
	private String method;
	@ApiModelProperty("技术路线")
	private String road;
	@ApiModelProperty("输入参数")
	private String inParam;
	@ApiModelProperty("返回参数")
	private String outParam;
}
