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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnsoft.device.api.asset.entity.ProjectManager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 项目管理数据传输对象实体类
 *
 * @author xuel
 * @since 2024-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectManagerDTO extends ProjectManager {
	private static final long serialVersionUID = 1L;


	@JsonFormat(pattern = "yyyyMMdd")
	@DateTimeFormat(pattern = "yyyyMMdd")
	@ApiModelProperty(value = "查询项目创建时间Start")
	private LocalDate projectCreateStartTime;

	@JsonFormat(pattern = "yyyyMMdd")
	@DateTimeFormat(pattern = "yyyyMMdd")
	@ApiModelProperty(value = "查询项目创建时间End")
	private LocalDate projectCreateEndTime;

}
