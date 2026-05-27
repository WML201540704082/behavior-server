package com.lnsoft.device.api.asset.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.joda.time.DateTime;

import java.time.LocalDate;

/**
 * @author xyzadmin
 */

@Data
public class DeviceCmdbDTO {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "设备名称")
	private String deviceName;

	@ApiModelProperty(value = "设备来源")
	private String deviceSource;

	@ApiModelProperty(value = "设备来源编码")
	private String deviceSourceCode;

	@ApiModelProperty(value = "设备状态")
	private String deviceStatus;

	@ApiModelProperty(value = "设备状态编码")
	private String deviceStatusCode;

	@ApiModelProperty(value = "设备类型")
	private String deviceType;

	@ApiModelProperty(value = "设备类型编码")
	private String deviceTypeCode;

	@ApiModelProperty(value = "ciId")
	private String ciId;
	@ApiModelProperty(value = "uuid")
	private String uuid;
	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "设备分类")
	private String deviceCategory;

	@ApiModelProperty(value = "设备分类编码")
	private String deviceCategoryCode;

	@ApiModelProperty(value = "产权单位")
	private String ownerUnit;

	@ApiModelProperty(value = "产权单位编码")
	private String ownerUnitCode;

	@ApiModelProperty(value = "产权部门")
	private String propertyDept;

	@ApiModelProperty(value = "产权部门编码")
	private String propertyDeptCode;

	@ApiModelProperty(value = "运维单位")
	private String operationUnit;

	@ApiModelProperty(value = "运维单位编码")
	private String operationUnitCode;

	@ApiModelProperty(value = "运维部门")
	private String operationDept;

	@ApiModelProperty(value = "运维部门编码")
	private String operationDepCode;

	@ApiModelProperty(value = "首次投运日期")
	private LocalDate oprtDateFirst;

	@ApiModelProperty(value = "机房编号")
	private String computerRoomCode;

	@ApiModelProperty(value = "机房")
	private String computerRoom;

	@ApiModelProperty(value = "区域编码")
	private String area;

	@ApiModelProperty(value = "设备编码")
	private String deviceCode;

	@ApiModelProperty(value = "售后服务到期时间")
	private LocalDate afterSaleExpDate;

	/**
	 * 近三年故障次数
	 */
	@ApiModelProperty(value = "近三年故障次数")
	private Integer faultCount;
	/**
	 * 近三年故障情况
	 */
	@ApiModelProperty(value = "近三年故障情况")
	private String faultDetail;
	/**
	 * 近三年隐患个数
	 */
	@ApiModelProperty(value = "近三年隐患个数")
	private Integer hiddenCount;
	/**
	 * 近三年隐患情况
	 */
	@ApiModelProperty(value = "近三年隐患情况")
	private String hiddenDetail;
	/**
	 * 品牌
	 */
	@ApiModelProperty(value = "品牌")
	private String brand;
	/**
	 * 品牌编码
	 */
	@ApiModelProperty(value = "品牌编码")
	private String brandCode;
	/**
	 * 系列
	 */
	@ApiModelProperty(value = "系列")
	private String series;
	/**
	 * 系列编码
	 */
	@ApiModelProperty(value = "系列编码")
	private String seriesCode;
	/**
	 * 型号
	 */
	@ApiModelProperty(value = "型号")
	private String deviceModel;
	/**
	 * 型号编码
	 */
	@ApiModelProperty(value = "型号编码")
	private String deviceModelCode;
	/**
	 * ip
	 */
	@ApiModelProperty(value = "IP")
	private String IP;
	/**
	 * mac
	 */
	@ApiModelProperty(value = "MAC")
	private String MAC;
	/**
	 * 设备高度(查询起始)
	 */
	@ApiModelProperty(value = "设备高度(查询起始)")
	private String deviceHeightStart;
	/**
	 * 设备高度(查询结束)
	 */
	@ApiModelProperty(value = "设备高度(查询结束)")
	private String deviceHeightOver;
	/**
	 * 设备起始高度
	 */
	@ApiModelProperty(value = "设备起始高度")
	private String deviceHeightBegin;
	/**
	 * 设备终止高度
	 */
	@ApiModelProperty(value = "设备终止高度")
	private String deviceHeightEnd;
	/**
	 * 设备高度
	 */
	@ApiModelProperty(value = "设备高度")
	private String deviceHeight;
	/**
	 * 机柜
	 */
	@ApiModelProperty(value = "机柜")
	private String cabinet;
	/**
	 * 机柜编号
	 */
	@ApiModelProperty(value = "机柜编号")
	private String cabinetCode;
	/**
	 * 校验标识
	 */
	@ApiModelProperty(value = "校验标识")
	private Integer checkMark;

	/**
	 * 提示语
	 */
	@ApiModelProperty(value = "提示语")
	private String checkPrompt;

	/**
	 * 是否包含机柜
	 */
	@ApiModelProperty(value = "是否包含机柜  0-不包含  1-包含")
	private Integer isCabinetsMark;

	/**
	 * 过滤条件-机柜
	 */
	private String cabinets;
}
