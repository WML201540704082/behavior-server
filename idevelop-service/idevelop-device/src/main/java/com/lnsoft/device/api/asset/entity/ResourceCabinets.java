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

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * 空间资源管理机柜表实体类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ResourceCabinets对象", description = "空间资源管理机柜表")
public class ResourceCabinets extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * uuid
	 */
	@ApiModelProperty(value = "uuid")
	private String uuid;
	/**
	 * ciId
	 */
	@ApiModelProperty(value = "ciId")
	private String ciId;
	/**
	 * 标准全程
	 */
	@ApiModelProperty(value = "标准全程")
	private String fullName;
	/**
	 * 设备编码
	 */
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;
	/**
	 * 设备来源
	 */
	@ApiModelProperty(value = "设备来源")
	private String deviceSourceCode;
	/**
	 * 设备来源名称
	 */
	@ApiModelProperty(value = "设备来源名称")
	private String deviceSource;
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
	 * 出厂序列号
	 */
	@ApiModelProperty(value = "出厂序列号")
	private String sn;
	/**
	 * 投运日期
	 */
	@ApiModelProperty(value = "投运日期")
	private LocalDate oprtDate;
	/**
	 * 机柜容量
	 */
	@ApiModelProperty(value = "机柜容量")
	private Integer cabinetCapacity;
	/**
	 * 是否治理
	 */
	@ApiModelProperty(value = "是否治理")
	private String isGovern;
	/**
	 * 数据治理时间
	 */
	@ApiModelProperty(value = "数据治理时间")
	private LocalDate governTime;
	/**
	 * 机房名称
	 */
	@ApiModelProperty(value = "机房名称")
	private String computerRoom;
	/**
	 * 机房编码
	 */
	@ApiModelProperty(value = "机房编码")
	private String computerRoomCode;
	/**
	 * 责任人
	 */
	@ApiModelProperty(value = "责任人")
	private String receivingPerson;
	/**
	 * 责任人联系方式
	 */
	@ApiModelProperty(value = "责任人联系方式")
	private String receivingTel;
	/**
	 * 运维责任人
	 */
	@ApiModelProperty(value = "运维责任人")
	private String operationPerson;
	/**
	 * 运维责任人联系方式
	 */
	@ApiModelProperty(value = "运维责任人联系方式")
	private String operationPersonTel;
	/**
	 * ERP资产编码
	 */
	@ApiModelProperty(value = "ERP资产编码")
	private String assetCodeErp;
	/**
	 * ERP设备台账编码
	 */
	@ApiModelProperty(value = "ERP设备台账编码")
	private String deviceCodeErp;
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
	 * 设备类型编码
	 */
	@ApiModelProperty(value = "设备类型编码")
	private String deviceTypeCode;

	@ApiModelProperty(value = "设备高度")
	private Integer deviceHeight;

	private String deviceCategoryCode;

	private String area;
}
