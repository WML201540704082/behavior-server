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
public class ErpTransEqunrItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 信通设备ID == UUID 必填
	 */
	@NotBlank(message = "信通设备ID不能为空")
	private String xtbm;

	/**
	 * 信通设备编码
	 */
	private String xtbmNo;

	/**
	 * 实物ID
	 */
	private String swid;

	/**
	 * 设备名称
	 */
	@NotBlank(message = "设备名称不能为空")
	private String eqktx;

	/**
	 * 使用保管部门 == 编码
	 */
	@NotBlank(message = "使用保管部门不能为空")
	private String zsb001;

	/**
	 * 实物管理部门 == 编码
	 */
	@NotBlank(message = "实物管理部门不能为空")
	private String zsb002;

	/**
	 * 使用保管人
	 */
	@NotBlank(message = "使用保管人不能为空")
	private String zsb010;

	/**
	 * 电压等级
	 */
	@NotBlank(message = "电压等级不能为空")
	private String zsb004;

	/**
	 * 设备状态
	 */
	@NotBlank(message = "设备状态不能为空")
	private String stat;

	/**
	 * 设备增加方式
	 */
	@NotBlank(message = "设备增加方式不能为空")
	private String zsb005;

	/**
	 * 设备变动方式
	 */
	private String stort;

	/**
	 * 技术对象类型
	 */
	private String eqart;

	/**
	 * 设备类型
	 */
	@NotBlank(message = "设备类型不能为空")
	private String sbfl;

	/**
	 * 制造商
	 */
	@NotBlank(message = "制造商不能为空")
	private String herst;

	/**
	 * 制造国家
	 */
	private String herld;

	/**
	 * WBS元素
	 */
	private String posid;

	/**
	 * 设备存放地点 = 功能位置名称
	 */
	@NotBlank(message = "设备存放地点不能为空")
	private String zsb006;

	/**
	 * 功能位置 = 功能位置编码
	 */
	@NotBlank(message = "功能位置不能为空")
	private String tplnr;

	/**
	 * 数量
	 */
	@NotBlank(message = "数量不能为空")
	private Integer zcabn_ztpm1005;

	/**
	 * 计量单位 = 名称
	 */
	private String zcabn_ztpm1006;

	/**
	 * 工厂区域 = 编码 市公司传003，县公司传004
	 */
	@NotBlank(message = "工厂区域不能为空")
	private String beber;

	/**
	 * 投运日期 格式 = YYYYMMDD
	 */
	private String inbdt;

	/**
	 * 制造商设备型号
	 */
	@NotBlank(message = "制造商设备型号不能为空")
	private String typbz;

	/**
	 * 制造商设备铭牌号
	 */
	@NotBlank(message = "设备铭牌号不能为空")
	private String serge;

	/**
	 * 制造（出厂、竣工）年份
	 */
	@NotBlank(message = "制造（出厂、竣工）年份不能为空")
	private String baujj;

	/**
	 * 制造（出厂、竣工）月份
	 */
	@NotBlank(message = "制造（出厂、竣工）月份不能为空")
	private String baumm;

	/**
	 * 维护工厂 = 维护工厂编码
	 */
	@NotBlank(message = "维护工厂不能为空")
	private String swerk;

	/**
	 * ERP资产编码
	 */
	private String anlnr;

	/**
	 * ERP设备台账编码
	 */
	private String equnr;

	/**
	 * 同步标志位
	 */
	private String tbbs;

	/**
	 * 线站标识
	 */
	private String zsb011 = "00000000000000000";

}
