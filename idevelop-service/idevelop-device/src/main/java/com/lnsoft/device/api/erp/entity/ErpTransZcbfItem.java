package com.lnsoft.device.api.erp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2024/3/30 14:50
 * @Description: ErpTransEqunrItem
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErpTransZcbfItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 信通设备ID == UUID 必填
	 */
	@NotBlank(message = "信通设备ID不能为空")
	private String xtbm;

	/**
	 * 序号
	 */
	@NotBlank(message = "序号不能为空")
	private String xh;

	/**
	 * 申请日期
	 */
	@NotBlank(message = "申请日期不能为空")
	private String sqsj;

	/**
	 * 报废单据申请人
	 */
	@NotBlank(message = "报废单据申请人不能为空")
	private String sqr;

	/**
	 * ERP设备编码
	 */
	@NotBlank(message = "ERP设备编码不能为空")
	private String equnr;

	/**
	 * 报废比例 默认100.00
	 */
	@NotBlank(message = "报废比例不能为空")
	private Double bfbl;

	/**
	 * 报废申请单描述信息
	 */
	private String sqdms;

	/**
	 * 报废原因(数据字典)
	 */
	@NotBlank(message = "报废原因不能为空")
	private String bfyy;

	/**
	 * 项目编号
	 */
	private String xmbm;

	/**
	 * 项目名称
	 */
	private String xmmc;

	/**
	 * 申请部门 == 保管部门
	 */
	@NotBlank(message = "申请部门不能为空")
	private String sqbm;

	/**
	 * 残值处理及资产更新方案
	 */
	private String clgx;

	/**
	 * 设备状态
	 */
	@NotBlank(message = "设备状态不能为空")
	private String zsbzt;
}
