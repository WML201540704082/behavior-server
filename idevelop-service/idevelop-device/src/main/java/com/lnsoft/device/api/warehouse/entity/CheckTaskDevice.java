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
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 盘点任务设备详情实体类
 *
 * @author Idevelop
 * @since 2024-04-19
 */
@Data
@TableName("idevelop_check_task_device")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CheckTaskDevice对象", description = "盘点任务设备详情")
public class CheckTaskDevice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 盘点任务主键
     */
    @ApiModelProperty(value = "盘点任务主键")
    private String taskId;
    /**
     * 设备ID
     */
    @ApiModelProperty(value = "设备ID")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long deviceId;
    /**
     * 设备cid
     */
    @ApiModelProperty(value = "设备cid")
	@JsonSerialize(using = ToStringSerializer.class)
    private Long ciId;
    /**
     * 设备uuid
     */
    @ApiModelProperty(value = "设备uuid")
    private String uuid;
    /**
     * 设备编号
     */
    @ApiModelProperty(value = "设备编号")
    private String deviceCode;
    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称")
    private String deviceName;
    /**
     * ERP资产编码
     */
    @ApiModelProperty(value = "ERP资产编码")
    private String assetCodeErp;
    /**
     * ERP台账编码
     */
    @ApiModelProperty(value = "ERP台账编码")
    private String accountCodeErp;
    /**
     * 出厂序列号
     */
    @ApiModelProperty(value = "出厂序列号")
    private String factorySerial;
    /**
     * 设备分类
     */
    @ApiModelProperty(value = "设备分类")
    private String deviceCategory;
	@ApiModelProperty(value = "设备分类编码")
	private String deviceCategoryCode;
    /**
     * 设备类型
     */
    @ApiModelProperty(value = "设备类型")
    private String deviceType;
	@ApiModelProperty(value = "设备类型编码")
	private String deviceTypeCode;
    /**
     * 设备状态
     */
    @ApiModelProperty(value = "设备状态")
    private String deviceStatus;
	@ApiModelProperty(value = "设备状态编码")
	private String deviceStatusCode;
	/**
	 * 领用部门
	 */
	@ApiModelProperty(value = "运行单位")
	private String oprtDept;
	/**
	 * 领用部门
	 */
	@ApiModelProperty(value = "运行单位编码")
	private String oprtDeptCode;
	/**
	 * 领用部门
	 */
	@ApiModelProperty(value = "领用单位")
	private String receiveUnit;
	/**
	 * 领用部门
	 */
	@ApiModelProperty(value = "领用单位编码")
	private String receiveUnitCode;
    /**
     * 领用部门
     */
    @ApiModelProperty(value = "领用部门")
    private String receiveDept;
    /**
     * 领用部门编码
     */
    @ApiModelProperty(value = "领用部门编码")
    private String receiveDeptCode;
    /**
     * 领用责任人
     */
    @ApiModelProperty(value = "领用责任人")
    private String receivingPerson;
    /**
     * 领用责任人联系方式
     */
    @ApiModelProperty(value = "领用责任人联系方式")
    private String receivingTel;
	/**
	 * 领用责任人联系方式
	 */
	@ApiModelProperty(value = "领用责任人联系方式")
	private String receivingIDCard;
	/**
	 * 责任人统一权限账号
	 */
	@ApiModelProperty(value = "责任人统一权限账号")
	@TableField(exist = false)
	private String receivePersonUnifiedAcc;

    /**
     * 使用人
     */
    @ApiModelProperty(value = "使用人")
    private String user;
	@ApiModelProperty(value = "使用人类型")
	private String userType;
	/**
	 * 使用人
	 */
	@ApiModelProperty(value = "使用人联系方式")
	private String userTel;
	/**
	 * 使用人
	 */
	@ApiModelProperty(value = "使用人身份证号")
	private String deviceUserIDCard;
    /**
     * 安装位置
     */
    @ApiModelProperty(value = "安装位置")
    private String installationSite;
    /**
     * IP地址
     */
    @ApiModelProperty(value = "IP地址")
    private String ip;
    /**
     * MAC地址
     */
    @ApiModelProperty(value = "MAC地址")
    private String mac;
    @ApiModelProperty(value = "是否临时")
	private String isInterim;
    @ApiModelProperty(value = "所属网络")
	private String netWorkCode;
	@ApiModelProperty(value = "所属子网ID")
	private String subnetId;
	@ApiModelProperty(value = "所属子网名称")
	private String subnetName;
	@ApiModelProperty(value = "认证方式")
	private String authentication;
	@ApiModelProperty(value = "投运日期")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATE)
	@JsonFormat(pattern = DateUtil.PATTERN_DATE, timezone = "GMT+8")
	private Date oprtDate;
    /**
     * 盘点状态(0:已盘点，1:待盘点，2:注册/盘盈设备, 3:盘亏设备)
     */
    @ApiModelProperty(value = "盘点状态(0:已盘点，1:待盘点，2:注册/盘盈设备, 3:盘亏设备)")
    private String checkStatus;
    /**
     * 处置结果(0:待处置, 1:同意, 2:驳回, 3:退运, 4:恢复入网, 5:临时退网)
     */
    @ApiModelProperty(value = "处置结果(0:待处置, 1:同意, 2:驳回, 3:退运, 4:恢复入网, 5:临时退网) 处置传参")
    private String disposeResult;
    /**
     * 处置状态(0:待处置, 1:已处置)
     */
    @ApiModelProperty(value = "处置状态(0:待处置, 1:已处置)")
    private String disposeStatus;
    /**
     * 是否信创设备(0:是，1:否)
     */
    @ApiModelProperty(value = "是否信创设备")
	@TableField(exist = false)
    private String isITAICode;
    /**
     * 设备信息json
     */
    @ApiModelProperty(value = "设备信息json")
    private String cmdbDevice;
    /**
     * 提交人
     */
    @ApiModelProperty(value = "提交人")
    private String subPerson;
    /**
     * 提交人联系方式
     */
    @ApiModelProperty(value = "提交人联系方式")
    private String subTel;
    /**
     * 提交人所在部门
     */
    @ApiModelProperty(value = "提交人所在部门")
    private String subDept;
    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date subTime;
    /**
     * 变更内容json
     */
    @ApiModelProperty(value = "变更内容json")
    private String changeContent;
    /**
     * 处置人
     */
    @ApiModelProperty(value = "处置人")
    private String disPerson;
    /**
     * 处置人联系方式
     */
    @ApiModelProperty(value = "处置人联系方式")
    private String disTel;
    /**
     * 处置人所在部门
     */
    @ApiModelProperty(value = "处置人所在部门")
    private String disDept;
    /**
     * 处置时间
     */
    @ApiModelProperty(value = "处置时间")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date disTime;
    /**
     * 处置意见
     */
    @ApiModelProperty(value = "处置意见 处置传参")
    private String disComment;
    /**
     * 关联工单编号
     */
    @ApiModelProperty(value = "关联工单编号")
    private String filingNo;
    /**
     * 工单生成时间
     */
    @ApiModelProperty(value = "工单生成时间")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date filingTime;
    /**
     * 创建人所在区域
     */
    @ApiModelProperty(value = "创建人所在区域")
    private String regionCode;

    private Long createDept;

    @ApiModelProperty(value = "品牌编码")
    private String brandCode;
	@ApiModelProperty(value = "系列编码")
	private String seriesCode;
	@ApiModelProperty(value = "型号编码")
	private String deviceModelCode;
	@ApiModelProperty(value = "品牌")
	private String brand;
	@ApiModelProperty(value = "系列")
	private String series;
	@ApiModelProperty(value = "型号")
	private String deviceModel;
	@ApiModelProperty(value = "标准全称")
	private String fullName;
	@ApiModelProperty(value = "断网开始时间")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
	private Date offlineStartTime;
	@ApiModelProperty(value = "断网恢复时间")
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
	private Date offlineEndTime;
	/**
	 * 机房名称
	 */
	@ApiModelProperty(value = "机房名称")
	@TableField(exist = false)
	private String computerRoom;
	/**
	 * 机房uuid(主键)
	 */
	@ApiModelProperty(value = "机房uuid(主键)")
	@TableField(exist = false)
	private String computerRoomCode;
	/**
	 * 机柜名称
	 */
	@ApiModelProperty(value = "机柜名称")
	@TableField(exist = false)
	private String cabinet;
	/**
	 * 机柜主键id
	 */
	@ApiModelProperty(value = "机柜id(主键)")
	@TableField(exist = false)
	private String cabinetCode;
	/**
	 * 设备起始高度
	 */
	@ApiModelProperty(value = "设备起始高度(U)")
	@TableField(exist = false)
	private Double deviceHeightBegin;
	/**
	 * 设备终止高度
	 */
	@ApiModelProperty(value = "设备终止高度(U)")
	@TableField(exist = false)
	private Double deviceHeightEnd;
	/**
	 * 设备高度
	 */
	@ApiModelProperty(value = "设备高度(U)")
	@TableField(exist = false)
	private Double deviceHeight;
	/**
	 * 网络设备用途类型
	 */
	@ApiModelProperty(value = "网络设备用途类型")
	@TableField(exist = false)
	private String networkDeviceType;
	/**
	 * 所属安全边际
	 */
	@ApiModelProperty(value = "所属安全边际")
	@TableField(exist = false)
	private String securityBoundary;
	/**
	 * 用途
	 */
	@ApiModelProperty(value = "用途")
	@TableField(exist = false)
	private String useTo;
	/**
	 * 设备来源名称
	 */
	@ApiModelProperty(value = "设备来源名称")
	@TableField(exist = false)
	private String deviceSource;
	/**
	 * 设备来源编码
	 */
	@ApiModelProperty(value = "设备来源编码")
	@TableField(exist = false)
	private String deviceSourceCode;
}
