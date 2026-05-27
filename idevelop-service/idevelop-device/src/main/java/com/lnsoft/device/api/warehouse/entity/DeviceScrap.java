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
import com.lnsoft.core.mp.base.BaseEntity;

import java.util.Date;

import com.lnsoft.device.dto.DeviceOrderFileDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 设备报废实体类
 *
 * @author Idevelop
 * @since 2024-03-18
 */
@Data
@TableName("idevelop_device_scrap")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceScrap对象", description = "设备报废")
public class DeviceScrap extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 报废编号
     */
    @ApiModelProperty(value = "报废编号")
    private String filingNo;
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
     * 申请人
     */
    @ApiModelProperty(value = "申请人")
    private Long applyUser;
	/**
	 * 申请人
	 */
	@ApiModelProperty(value = "申请人名称")
	private String applyUserName;
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
     * 申请人联系方式
     */
    @ApiModelProperty(value = "申请人联系方式")
    private String applyPhone;
    /**
     * 设备铭牌号
     */
    @ApiModelProperty(value = "设备铭牌号")
    private String deviceNameplate;
    /**
     * 设备数量
     */
    @ApiModelProperty(value = "设备数量")
    private Integer deviceNum;
    /**
     * 设备状态，默认待报废
     */
    @ApiModelProperty(value = "设备状态，默认待报废")
    private String deviceStatus;
    /**
     * 报废比例
     */
    @ApiModelProperty(value = "报废比例")
    private String scrapScale;
    /**
     * 资产原值
     */
    @ApiModelProperty(value = "资产原值")
    private Long originalValue;
    /**
     * 已提折旧
     */
    @ApiModelProperty(value = "已提折旧")
    private Long depreciation;
    /**
     * 资产净值
     */
    @ApiModelProperty(value = "资产净值")
    private Long netWorth;
    /**
     * 设备报废时间
     */
    @ApiModelProperty(value = "设备报废时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date scrapTime;

    @TableField(exist = false)
    private DeviceOrderFileDTO orderFile;
    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private String accessory;
    /**
     * 残值处理及资产更新方案
     */
    @ApiModelProperty(value = "残值处理及资产更新方案")
    private String salvageAssets;
    /**
     * 报废原因
     */
    @ApiModelProperty(value = "报废原因")
    private String scrapReason;
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
    /**
     * 是否报废 0是 1否
     */
    @ApiModelProperty(value = "是否报废 0是 1否")
    private String isScrap;
    /**
     * ERP同步状态 0 未同步 1 同步中 2 同步成功 3 同步失败
     */
    @ApiModelProperty(value = "ERP同步状态 0 未同步 1 同步中 2 同步成功 3 同步失败")
    private String erpStatus;
    /**
     * ERP报废单信息
     */
    @ApiModelProperty(value = "ERP报废单信息")
    private String erpScrapCode;
    /**
     * 是否同步i6000 0 不同步 1 同步
     */
    @ApiModelProperty(value = "是否同步i6000 0 不同步 1 同步")
    private String i6000SyncStatus;
    /**
     * 同步i6000状态 0 未同步 1 同步中 2 同步成功 3 同步失败
     */
    @ApiModelProperty(value = "同步i6000状态 0 未同步 1 同步中 2 同步成功 3 同步失败")
    private String i6000Status;
    /**
     * 创建人所在区域
     */
    @ApiModelProperty(value = "创建人所在区域")
    private String regionCode;

	/**
	 * 使用保管部门
	 */
	@ApiModelProperty(value = "使用保管部门")
	private String useKeepDept;
	/**
	 * 实物保管部门
	 */
	@ApiModelProperty(value = "实物保管部门")
	private String entityKeepDept;

	@ApiModelProperty(value = "报废单据申请人账号")
	private String sqr;

	@ApiModelProperty(value = "报废单据审批人账号")
	private String spr;
}
