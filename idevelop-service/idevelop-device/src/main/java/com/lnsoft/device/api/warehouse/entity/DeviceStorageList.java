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
import com.lnsoft.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 设备入库明细表实体类
 *
 * @author Idevelop
 * @since 2024-02-22
 */
@Data
@TableName("idevelop_device_storage_list")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceStorageList对象", description = "设备入库明细表")
public class DeviceStorageList extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 入库ID
	 */
	@ApiModelProperty(value = "入库ID")
	@NotNull(message = "入库ID不能为空")
	private String storageId;
	/**
	 * 设备编码
	 */
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;
	/**
	 * erp资产编码
	 */
	@ApiModelProperty(value = "erp资产编码")
	private String erpAssetCode;

	@ApiModelProperty(value = "erp台账编码")
	private String erpAccountCode;
	/**
	 * 设备名称
	 */
	@ApiModelProperty(value = "设备名称")
	private String deviceName;

	@ApiModelProperty(value = "设备全称")
	private String fullName;
	/**
	 * 设备状态
	 */
	@ApiModelProperty(value = "设备状态")
	private String deviceStatus;
	/**
	 * 硬件配置信息
	 */
	@ApiModelProperty(value = "硬件配置信息")
	private String deviceHardwareInfo;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "创建部门")
	private String createDept;

	@ApiModelProperty(value = "配置项id(新增cmdb台账后回写)")
	private String ciEntityId;

	@ApiModelProperty(value = "uuid(新增cmdb台账后回写)")
	private String uuid;

	@ApiModelProperty(value = "新增cmdb台账是否成功(新增cmdb台账后回写)")
	private String isToCmdb;

	@ApiModelProperty(value = "ERP同步状态 2成功3失败")
	private String syncErpStatus;

	@ApiModelProperty(value = "i6000同步状态 1成功2失败")
	private String syncI6000Status;


}
