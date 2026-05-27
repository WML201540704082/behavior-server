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

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 设备报废列表实体类
 *
 * @author Idevelop
 * @since 2024-03-18
 */
@Data
@TableName("idevelop_device_scrap_list")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceScrapList对象", description = "设备报废列表")
public class DeviceScrapList extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 报废工单主键
     */
    @ApiModelProperty(value = "报废工单主键")
    private String scrapId;

	/**
	 * 设备ID
	 */
	@ApiModelProperty(value = "设备编号")
	private Long deviceId;

    /**
     * 设备编号
     */
	@ExcelProperty(value = "设备编号")
    @ApiModelProperty(value = "设备编号")
    private String deviceCode;
    /**
     * 设备名称
     */
	@ExcelProperty(value = "设备名称")
    @ApiModelProperty(value = "设备名称")
    private String deviceName;
    /**
     * ERP资产编码
     */
	@ExcelProperty(value = "ERP资产编码")
    @ApiModelProperty(value = "ERP资产编码")
    private String assetCodeErp;
    /**
     * ERP台账编码
     */
	@ExcelProperty(value = "ERP台账编码")
    @ApiModelProperty(value = "ERP台账编码")
    private String deviceCodeErp;
    /**
     * 出厂序列号
     */
	@ExcelProperty(value = "出厂序列号")
    @ApiModelProperty(value = "出厂序列号")
    private String factorySerial;
    /**
     * 首次投运日期
     */
	@ExcelProperty(value = "首次投运日期")
    @ApiModelProperty(value = "首次投运日期")
    private LocalDateTime operatedTime;
    /**
     * 预定使用年限
     */
	@ExcelProperty(value = "预定使用年限")
    @ApiModelProperty(value = "预定使用年限")
    private String serviceLife;
    /**
     * 品牌
     */
	@ExcelProperty(value = "品牌")
    @ApiModelProperty(value = "品牌")
    private String brand;
    /**
     * 系列
     */
	@ExcelProperty(value = "系列")
    @ApiModelProperty(value = "系列")
    private String series;
    /**
     * 型号
     */
	@ExcelProperty(value = "型号")
    @ApiModelProperty(value = "型号")
    private String model;
    /**
     * 设备分类
     */
	@ExcelProperty(value = "设备分类")
    @ApiModelProperty(value = "设备分类")
    private String deviceCategory;
    /**
     * 设备类型
     */
	@ExcelProperty(value = "设备类型")
    @ApiModelProperty(value = "设备类型")
    private String deviceType;
    /**
     * 报废比例
     */
	@ExcelProperty(value = "报废比例")
    @ApiModelProperty(value = "报废比例")
    private String scrapScale;
    /**
     * 资产原值
     */
	@ExcelProperty(value = "资产原值")
    @ApiModelProperty(value = "资产原值")
    private Double originalValue;
    /**
     * 已提折旧
     */
	@ExcelProperty(value = "已提折旧")
    @ApiModelProperty(value = "已提折旧")
    private Long depreciation;
    /**
     * 资产净值
     */
	@ExcelProperty(value = "资产净值")
    @ApiModelProperty(value = "资产净值")
    private Double netWorth;

	/**
	 * 创建部门
	 */
	@ApiModelProperty(value = "创建部门")
	private String createDept;

	private Long ciId;

	private String uuid;

	private String cmdbDevice;

	@ApiModelProperty(value = "序号")
	private String sort;

	@ApiModelProperty(value = "报废原因")
	private String bfyy;

}
