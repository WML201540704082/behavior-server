/**
 .
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lnsoft.device.api.warehouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnsoft.core.mp.base.BaseEntity;
import com.lnsoft.core.tool.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 盘点任务实体类
 *
 * @author Idevelop
 * @since 2024-04-19
 */
@Data
@TableName("idevelop_check_task")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CheckTask对象", description = "盘点任务")
public class CheckTask extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
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
     * 是否过期
     */
    @ApiModelProperty(value = "是否过期(0：否，1：是)")
    private String isExpire;
    /**
     * 盘点总设备数
     */
    @ApiModelProperty(value = "盘点总设备数")
    private String checkNum;
    /**
     * 任务开始时间
     */
    @ApiModelProperty(value = "任务开始时间")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATE)
	@JsonFormat(pattern = DateUtil.PATTERN_DATE, timezone = "GMT+8")
    private Date taskStartTime;
    /**
     * 任务结束时间
     */
    @ApiModelProperty(value = "任务结束时间")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATE)
	@JsonFormat(pattern = DateUtil.PATTERN_DATE, timezone = "GMT+8")
    private Date taskEndTime;
    /**
     * 发起单位
     */
    @ApiModelProperty(value = "发起单位")
    private String launchUnit;
    /**
     * 发起单位名称
     */
    @ApiModelProperty(value = "发起单位名称")
    private String launchUnitName;
	/**
	 * 发起单位名称
	 */
	@ApiModelProperty(value = "根据历史盘点任务创建(id)")
	private String historyTask;
	/**
	 * 发起单位名称
	 */
	@ApiModelProperty(value = "历史盘点类型JSON(不传)")
	private String historyCheckType;
	/**
	 * 发起单位名称
	 */
	@ApiModelProperty(value = "历史盘点类型名称")
	private String historyCheckTypes;
    /**
     * 部门范围
     */
    @ApiModelProperty(value = "部门范围(不传)")
    private String checkDept;
	/**
	 * 部门范围
	 */
	@ApiModelProperty(value = "部门范围名称")
	private String checkDepts;
    /**
     * 盘点设备分类
     */
    @ApiModelProperty(value = "盘点设备分类(不传)")
    private String deviceCategory;
    /**
     * 盘点设备类型
     */
    @ApiModelProperty(value = "盘点设备类型(不传)")
    private String deviceType;
	/**
	 * 盘点设备类型
	 */
	@ApiModelProperty(value = "盘点设备类型名称")
	private String deviceTypes;
    /**
     * 工单发起人
     */
    @ApiModelProperty(value = "盘点人ID(不传)")
    private String receiver;
	@ApiModelProperty(value = "盘点人名称")
	private String receiverName;
    /**
     * 工单发起时间
     */
    @ApiModelProperty(value = "工单发起时间(自动生成)")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATE)
	@JsonFormat(pattern = DateUtil.PATTERN_DATE, timezone = "GMT+8")
    private Date receiverTime;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 创建人所在区域
     */
    @ApiModelProperty(value = "创建人所在区域")
    private String regionCode;
    /**
     * 流程实例ID
     */
    @ApiModelProperty(value = "流程实例ID")
    private String processInsId;
    /**
     * 流程实例状态
     */
    @ApiModelProperty(value = "流程实例状态")
    private String processStatus;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long createDept;


}
