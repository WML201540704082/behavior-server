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
package com.lnsoft.device.api.operation.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnsoft.common.config.ObjectSerializer;
import com.lnsoft.core.mp.base.BaseEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 设备变更实体类
 *
 * @author Idevelop
 * @since 2024-03-07
 */
@Data
@TableName("idevelop_device_change")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceChange对象", description = "设备变更")
public class DeviceChange extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@TableId
    private String id;
    /**
     * 变更编号
     */
    @ApiModelProperty(value = "变更编号")
    private String filingNo;
    /**
     * 变更类型
     */
    @ApiModelProperty(value = "变更类型")
    private String changeType;
    /**
     * 申请单位
     */
    @ApiModelProperty(value = "申请单位")
    private String applyUnit;
	/**
	 * 申请单位
	 */
	@ApiModelProperty(value = "申请单位名称")
	private String applyUnitName;
    /**
     * 申请部门
     */
    @ApiModelProperty(value = "申请部门")
    private String applyDept;
	/**
	 * 申请部门
	 */
	@ApiModelProperty(value = "申请部门名称")
	private String applyDeptName;
    /**
     * 申请人
     */
    @ApiModelProperty(value = "申请人")
    private String applyUser;
	/**
	 * 申请人
	 */
	@ApiModelProperty(value = "申请人名称")
	private String applyUserName;
    /**
     * 工单状态   1未上报[暂存]，2审批，3已变更
     */
    @ApiModelProperty(value = "工单状态   1未上报[暂存]，2审批，3已变更")
	@JsonSerialize(nullsUsing = ObjectSerializer.class)
    private Integer ticketStatus;
    /**
     * 工单生成方式
     */
    @ApiModelProperty(value = "工单生成方式")
	@JsonSerialize(nullsUsing = ObjectSerializer.class)
    private Integer ticketCreatType;
    /**
     * 受理时间
     */
    @ApiModelProperty(value = "受理时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime receiverTime;
    /**
     * 流程实例ID
     */
    @ApiModelProperty(value = "流程实例ID")
    private String processInsId;
    /**
     * 流程状态
     */
    @ApiModelProperty(value = "流程状态")
    private String processStatus;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 变更设备数
     */
    @ApiModelProperty(value = "变更设备数")
    private String deviceCount;
	/**
	 * 变更设备数
	 */
	@ApiModelProperty(value = "区域编码")
	private String regionCode;



}
