package com.lnsoft.device.api.warehouse.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnsoft.core.tool.utils.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class CheckTaskExport {
	private String id;
	@ExcelProperty(value = "任务编号")
	private String filingNo;

	@ExcelProperty(value = "任务名称")
	private String taskName;

	@DateTimeFormat(pattern = DateUtil.PATTERN_DATE)
	@JsonFormat(pattern = DateUtil.PATTERN_DATE, timezone = "GMT+8")
	@ExcelProperty(value = "任务开始时间")
	private Date taskStartTime;

	@DateTimeFormat(pattern = DateUtil.PATTERN_DATE)
	@JsonFormat(pattern = DateUtil.PATTERN_DATE, timezone = "GMT+8")
	@ExcelProperty(value = "任务结束时间")
	private Date taskEndTime;

	@ExcelProperty(value = "发起单位名称")
	private String launchUnitName;

	@ExcelProperty(value = "盘点人名称")
	private String receiverName;


	@ExcelProperty(value = "盘点进度")
	private String checkProgress;


	@ExcelProperty(value = "已盘点")
	private Long isCheckNum;

	@ExcelProperty(value = "未盘点")
	private Long noCheckNum;

	@ExcelProperty(value = "盘盈总数")
	private Long py;

	@ExcelProperty(value = "盘亏总数")
	private Long pk;

	@ExcelProperty(value = "退运数量")
	private Long returnedDevice;

	@ExcelProperty(value = "临时退网数量")
	private Long lstwDevice;
}
