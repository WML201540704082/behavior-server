package com.lnsoft.device.api.desk.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DictValueVO implements Serializable {

	private static final long serialVersionUID = -2487925816423195756L;
	/**
	 * 工作流节点
	 */
	@ApiModelProperty(value = "工作流节点")
	private String node;

	/**
	 * 工作流节点名称
	 */
	@ApiModelProperty(value = "工作流节点名称")
	private String nodeName;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 工单节点
	 */
	@ApiModelProperty(value = "工单节点 0 开始 1 流程中 2 结束")
	private String type;
}
