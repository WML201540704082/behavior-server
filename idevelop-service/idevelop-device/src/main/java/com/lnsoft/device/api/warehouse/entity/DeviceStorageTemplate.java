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

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 设备入库导入模板表实体类
 *
 * @author Idevelop
 * @since 2024-02-22
 */
@Data
@TableName("idevelop_device_storage_template")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceStorageTemplate对象", description = "设备入库导入模板表")
public class DeviceStorageTemplate extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;
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
	 * 文件类型，用于指定文件下载流格式mime
	 */
	@ApiModelProperty(value = "文件类型，用于指定文件下载流格式mime")
	private String fileType;
	/**
	 * 附件名称，原始文件名称
	 */
	@ApiModelProperty(value = "附件名称，原始文件名称")
	private String fileName;
	/**
	 * 附件地址
	 */
	@ApiModelProperty(value = "附件地址")
	private String filePath;
	/**
	 * 附件手机端访问地址
	 */
	@ApiModelProperty(value = "附件手机端访问地址")
	private String filePathMobile;
	/**
	 * 文件大小(kb)
	 */
	@ApiModelProperty(value = "文件大小(kb)")
	private Long fileSize;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "创建部门")
	private String createDept;
}
