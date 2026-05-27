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
package com.lnsoft.device.api.asset.dto;

import com.lnsoft.device.api.asset.entity.DeviceOldFile;
import com.lnsoft.device.api.asset.entity.DeviceOldList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 老旧设备表	数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceOldListDTO extends DeviceOldList {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "开始年限")
	private String beginAge;
	@ApiModelProperty(value = "结束年限")
	private String endAge;
	@ApiModelProperty(value = "设备来源")
	private String deviceSourceCode;


	@ApiModelProperty(value = "附件")
	private List<DeviceOldFile> deviceOldFileList;

}
