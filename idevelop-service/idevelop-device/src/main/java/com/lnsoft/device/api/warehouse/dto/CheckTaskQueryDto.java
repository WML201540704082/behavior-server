package com.lnsoft.device.api.warehouse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnsoft.core.tool.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author cwb
 * @date 2024/4/19
 */
@Data
public class CheckTaskQueryDto {
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private String id;
	/**
	 * 盘点任务编号
	 */
	@ApiModelProperty(value = "盘点任务编号")
	private String filingNo;
	/**
	 * 任务名称
	 */
	@ApiModelProperty(value = "任务名称")
	private String taskName;
	/**
	 * 任务开始时间
	 */
	@ApiModelProperty(value = "任务开始时间")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATE)
	@JsonFormat(pattern = DateUtil.PATTERN_DATE, timezone = "GMT+8")
	private Date taskStartTimeS;
	/**
	 * 任务开始时间
	 */
	@ApiModelProperty(value = "任务开始时间")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATE)
	@JsonFormat(pattern = DateUtil.PATTERN_DATE, timezone = "GMT+8")
	private Date taskStartTimeE;
	/**
	 * 任务结束时间
	 */
	@ApiModelProperty(value = "任务结束时间")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATE)
	@JsonFormat(pattern = DateUtil.PATTERN_DATE, timezone = "GMT+8")
	private Date taskEndTimeS;
	/**
	 * 任务结束时间
	 */
	@ApiModelProperty(value = "任务结束时间")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATE)
	@JsonFormat(pattern = DateUtil.PATTERN_DATE, timezone = "GMT+8")
	private Date taskEndTimeE;
	/**
	 * 任务状态
	 */
	@ApiModelProperty(value = "任务状态：1未开始，2进行中，3已完成，4已超时")
	private String status;
	/**
	 * 盘盈状态
	 */
	@ApiModelProperty(value = "盘盈状态：0待处理，1已处理，2全部")
	private String pyStatus;
	/**
	 * 盘亏状态
	 */
	@ApiModelProperty(value = "盘亏状态：0待处理，1已处理，2全部")
	private String pkStatus;

	private String regionCode;
}
