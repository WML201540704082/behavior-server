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
package com.lnsoft.device.api.asset.vo;

import com.lnsoft.device.api.asset.entity.DeviceOldFile;
import com.lnsoft.device.api.asset.entity.DeviceOldList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * 老旧设备表	视图实体类
 *
 * @author Idevelop
 * @since 2024-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DeviceOldListVO对象", description = "老旧设备表	")
public class DeviceOldListVO extends DeviceOldList {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "起始得分")
	private Double startScore;

	@ApiModelProperty(value = "终止得分")
	private Double endScore;

	@ApiModelProperty(value = "起始年限")
	private Integer startAge;

	@ApiModelProperty(value = "终止年限")
	private Integer endAge;

	@ApiModelProperty(value = "只看TOP")
	private Integer top;

	@ApiModelProperty(value = "机房级别取值")
	private String roomTypeItem;

	@ApiModelProperty(value = "机房功能取值")
	private String roomFunctionItem;

	@ApiModelProperty(value = "超龄时间取值")
	private String overAgeItem;

	@ApiModelProperty(value = "维保情况取值")
	private String maintenanceItem;

	@ApiModelProperty(value = "设备归属部门取值")
	private String deptItem;

	@ApiModelProperty(value = "服务风险取值")
	private String serviceRiskItem;

	@ApiModelProperty(value = "运行情况自评取值")
	private String appriseOwnItem;

	@ApiModelProperty(value = "n-1情况取值")
	private String customizeItem;

	@ApiModelProperty(value = "附件")
	private List<DeviceOldFile> oldFileList;

}
