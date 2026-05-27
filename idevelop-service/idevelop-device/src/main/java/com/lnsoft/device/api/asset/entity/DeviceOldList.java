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
package com.lnsoft.device.api.asset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 老旧设备表	实体类
 *
 * @author Idevelop
 * @since 2024-06-19
 */
@Data
@TableName("idevelop_device_old_list")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceOldList对象", description = "老旧设备表	")
public class DeviceOldList extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 设备编码
     */
    @ApiModelProperty(value = "设备编码")
    private String deviceCode;
    /**
     * 分数
     */
    @ApiModelProperty(value = "分数")
    private Double score;
    /**
     * 机房功能
     */
    @ApiModelProperty(value = "机房功能")
    private String roomFunction;
    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称")
    private String deviceName;
    /**
     * 设备状态
     */
    @ApiModelProperty(value = "设备状态")
    private String deviceStatus;
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
     * 设备分类名称
     */
    @ApiModelProperty(value = "设备分类名称")
    private String deviceCategoryName;
    /**
     * 设备类型名称
     */
    @ApiModelProperty(value = "设备类型名称")
    private String deviceTypeName;
    /**
     * 产权单位
     */
    @ApiModelProperty(value = "产权单位")
    private String ownerUnit;
    /**
     * 产权单位编码
     */
    @ApiModelProperty(value = "产权单位编码")
    private String ownerUnitCode;
    /**
     * 产权部门
     */
    @ApiModelProperty(value = "产权部门")
    private String propertyDept;
    /**
     * 产权部门编码
     */
    @ApiModelProperty(value = "产权部门编码")
    private String propertyDeptCode;
    /**
     * 运维单位
     */
    @ApiModelProperty(value = "运维单位")
    private String operationUnit;
    /**
     * 运维单位编码
     */
    @ApiModelProperty(value = "运维单位编码")
    private String operationUnitCode;
    /**
     * 运维部门
     */
    @ApiModelProperty(value = "运维部门")
    private String operationDept;
    /**
     * 运维部门编码
     */
    @ApiModelProperty(value = "运维部门编码")
    private String operationDeptCode;
    /**
     * 运维情况
     */
    @ApiModelProperty(value = "运维情况")
    private Integer operationCondition;
    /**
     * 首次投运日期
     */
    @ApiModelProperty(value = "首次投运日期")
    private LocalDate oprtDateFirst;
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
     * 自评是否更换
     */
    @ApiModelProperty(value = "自评是否更换")
    private String isChange;
    /**
     * 自评修改原因
     */
    @ApiModelProperty(value = "自评修改原因")
    private String changeReason;
    /**
     * 是否完成立项
     */
    @ApiModelProperty(value = "是否完成立项")
    private Integer isProject;
    /**
     * 立项更换时间
     */
    @ApiModelProperty(value = "立项更换时间")
    private LocalDateTime projectTime;
    /**
     * 机房编码
     */
    @ApiModelProperty(value = "机房编码")
    private String roomCode;
    /**
     * 机房类型
     */
    @ApiModelProperty(value = "机房类型")
    private String roomType;
    /**
     * 区域编码
     */
    @ApiModelProperty(value = "区域编码")
    private String regionCode;
    /**
     * 机房级别得分
     */
    @ApiModelProperty(value = "机房级别得分")
    private Double roomLevelScore;
    /**
     * 机房功能得分
     */
    @ApiModelProperty(value = "机房功能得分")
    private Double roomFunctionScore;
    /**
     * 超龄时间得分
     */
    @ApiModelProperty(value = "超龄时间得分")
    private Double overAgeScore;
    /**
     * 维保情况得分
     */
    @ApiModelProperty(value = "维保情况得分")
    private Double maintenanceScore;
    /**
     * 设备归属部门得分
     */
    @ApiModelProperty(value = "设备归属部门得分")
    private Double deptScore;
    /**
     * 服务风险得分
     */
    @ApiModelProperty(value = "服务风险得分")
    private Double serviceRiskScore;
    /**
     * 运行情况自评得分
     */
    @ApiModelProperty(value = "运行情况自评得分")
    private Double appriseOwnScore;
    /**
     * n-1满足情况得分
     */
    @ApiModelProperty(value = "n-1满足情况得分")
    private Double customizeScore;
    /**
     * 设备状态得分
     */
    @ApiModelProperty(value = "设备状态得分")
    private Double deviceStatusScore;

	/**
	 * 超龄时间
	 */
	@ApiModelProperty(value = "超龄时间")
	private Double overAge;

	/**
	 * 设备总数
	 */
	@ApiModelProperty(value = "设备总数")
	private Integer deviceCount;

	/**
	 * 评审库标识 1-评审库
	 */
	@ApiModelProperty(value = "评审库标识  1-评审库")
	private Integer reviewLibraryMark;

	/**
	 * 评审意见
	 */
	@ApiModelProperty(value = "评审意见")
	private String approvalOpinion;

	/**
	 * 自评修改人名称
	 */
	@ApiModelProperty(value = "自评修改人名称")
	private String changeUser;
	/**
	 * 打分周期
	 */
	@ApiModelProperty(value = "打分周期")
	private String scoreCycle;

}
