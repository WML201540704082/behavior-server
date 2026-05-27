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
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目下的ERP资产编码实体类
 *
 * @author Idevelop
 * @since 2024-04-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectManagerDetailExport {

	private static final long serialVersionUID = 1L;

	/**
	 * 设备UUID
	 */
	@ApiModelProperty(value = "设备UUID")
	@TableId("uuid")
	@ExcelIgnore
	private String uuid;
	/**
	 * 设备编码
	 */
	@ApiModelProperty(value = "设备编码")
	@ExcelProperty("设备编码")
	private String deviceCode;
	/**
	 * 设备名称
	 */
	@ApiModelProperty(value = "设备名称")
	@ExcelProperty("设备名称")
	private String deviceName;
	/**
	 * ERP资产编码
	 */
	@ApiModelProperty(value = "ERP资产编码")
	@ExcelProperty("ERP资产编码")
	private String erpAssetCode;
	/**
	 * ERP台账编号
	 */
	@ApiModelProperty(value = "ERP台账编号")
	@ExcelProperty("ERP台账编码")
	private String erpAccountCode;
	/**
	 * ERP资产编码使用状态
	 */
	@ApiModelProperty(value = "ERP资产编码使用状态 0未使用, 1已使用")
	@ExcelIgnore
	private Integer erpAssetStatus;
	/**
	 * ERP转资状态
	 */
	@ApiModelProperty(value = "ERP转资状态")
	@ExcelIgnore
	private String erpTransferStatus;
	/**
	 * ERP同步状态
	 */
	@ApiModelProperty(value = "ERP同步状态 0: 未同步, 1 同步中 ,2 已同步,3 同步失败, 99 部分成功")
	@ExcelIgnore
	private Integer erpStatus;
	/**
	 * I6000同步状态
	 */
	@ApiModelProperty(value = "I6000同步状态 0: 未同步, 1 同步中 ,2 已同步,3 同步失败")
	@ExcelIgnore
	private Integer i6000Status;
	/**
	 * 设备类型
	 */
	@ApiModelProperty(value = "设备类型")
	@ExcelProperty("设备类型")
	private String deviceType;
	/**
	 * WBS项目编码
	 */
	@ApiModelProperty(value = "WBS项目编码")
	@ExcelIgnore
	private String wbsCode;
	/**
	 * WBS项目
	 */
	@ApiModelProperty(value = "WBS项目")
	@ExcelIgnore
	private String wbsName;
	/**
	 * 所在阶段
	 */
	@ApiModelProperty(value = "所在阶段")
	@ExcelIgnore
	private String stage;
	/**
	 * I6000同步结果
	 */
	@ApiModelProperty(value = "I6000同步结果")
	@ExcelIgnore
	private String i6000Remake;

}
