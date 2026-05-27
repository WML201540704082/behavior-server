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

import com.alibaba.excel.annotation.ExcelIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 空间资源管理数据传输类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@Data
public class ResourceTreeVO {
	/**
	 * 节点类型
	 */
	@ApiModelProperty(value = "节点类型")
	private String type;
	/**
	 * id
	 */
	@ApiModelProperty(value = "id")
	@ExcelIgnore
	private String id;
	/**
	 * name
	 */
	@ApiModelProperty(value = "name")
	@ExcelIgnore
	private String name;
	/**
	 * 区域编码
	 */
	@ApiModelProperty(value = "区域编码")
	private String regionCode;
	private String ciId;
	private String uuid;
	@ApiModelProperty(value = "配置项id",required = true)
	private Long ciEntityId;
	private Integer cabinetCapacity;
}
