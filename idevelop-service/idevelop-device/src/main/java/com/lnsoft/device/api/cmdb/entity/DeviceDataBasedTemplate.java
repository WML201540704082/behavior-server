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
package com.lnsoft.device.api.cmdb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lnsoft.core.mp.base.BaseEntity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 设备-数据治理模板表实体类
 *
 * @author Idevelop
 * @since 2024-06-19
 */
@Data
@TableName("idevelop_device_data_based_template")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceDataBasedTemplate对象", description = "设备-数据治理模板表")
public class DeviceDataBasedTemplate extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 文件类型
	 */
	@ApiModelProperty(value = "文件类型,SELF:自建,ITUMP:系统生成")
	private String fileType;
	/**
	 * 文件名称
	 */
	@ApiModelProperty(value = "文件名称")
	private String fileName;
	/**
	 * 文件地址
	 */
	@ApiModelProperty(value = "文件地址")
	private String filePath;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;


}
